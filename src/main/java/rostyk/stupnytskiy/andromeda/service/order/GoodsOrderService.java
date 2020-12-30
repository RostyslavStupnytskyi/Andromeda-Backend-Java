package rostyk.stupnytskiy.andromeda.service.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.andromeda.dto.request.order.ConfirmGoodsOrderDeliveryRequest;
import rostyk.stupnytskiy.andromeda.dto.request.order.GoodsOrderDeliveryDetailsForShipmentRequest;
import rostyk.stupnytskiy.andromeda.dto.request.order.GoodsOrderItemRequest;
import rostyk.stupnytskiy.andromeda.dto.request.order.GoodsOrderRequest;
import rostyk.stupnytskiy.andromeda.entity.UserDeliveryAddress;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.SellerAccount;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.GoodsSellerAccount;
import rostyk.stupnytskiy.andromeda.entity.account.user_account.UserAccount;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.GoodsAdvertisement;
import rostyk.stupnytskiy.andromeda.entity.order.GoodsOrder;
import rostyk.stupnytskiy.andromeda.entity.order.GoodsOrderStatus;
import rostyk.stupnytskiy.andromeda.repository.GoodsOrderRepository;
import rostyk.stupnytskiy.andromeda.service.DeliveryTypeService;
import rostyk.stupnytskiy.andromeda.service.UserDeliveryAddressService;
import rostyk.stupnytskiy.andromeda.service.account.UserAccountService;
import rostyk.stupnytskiy.andromeda.service.account.seller.GoodsSellerAccountService;
import rostyk.stupnytskiy.andromeda.service.advertisement.goods_advertisement.GoodsAdvertisementService;

import java.time.LocalDateTime;
import java.util.List;

import static rostyk.stupnytskiy.andromeda.entity.order.GoodsOrderStatus.WARRANTY_PERIOD;

@Service
public class GoodsOrderService {

    @Autowired
    private GoodsOrderRepository goodsOrderRepository;

    @Autowired
    private GoodsAdvertisementService goodsAdvertisementService;

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private UserDeliveryAddressService userDeliveryAddressService;

    @Autowired
    private GoodsOrderDeliveryDetailsService goodsOrderDeliveryDetailsService;

    @Autowired
    private DeliveryTypeService deliveryTypeService;

    @Autowired
    private GoodsOrderItemService goodsOrderItemService;

    @Autowired
    private GoodsSellerAccountService goodsSellerAccountService;

    public void createGoodsOrder(GoodsOrderRequest request) {
        validateGoodsOrderRequest(request);
        GoodsOrder goodsOrder = goodsOrderRequestToGoodsOrder(request);
        request.getItems().forEach((i) -> goodsOrderItemService.save(i, goodsOrder));
    }

    private GoodsOrder goodsOrderRequestToGoodsOrder(GoodsOrderRequest request) {
        GoodsOrder goodsOrder = new GoodsOrder();
        goodsOrder.setCreationDate(LocalDateTime.now());
        goodsOrder.setOrderStatus(GoodsOrderStatus.WAITING_FOR_SENDING);
        goodsOrder.setDeliveryType(deliveryTypeService.findById(request.getAddressId()));
        goodsOrder.setUser(userAccountService.findBySecurityContextHolder());
        goodsOrder.setSeller(getSellerAccountFromGoodsItemRequest(request.getItems().get(0)));
        goodsOrder.setDeliveryDetails(goodsOrderDeliveryDetailsService.userDeliveryAddressToDeliveryDetails(userDeliveryAddressService.findById(request.getAddressId())));
        return goodsOrderRepository.save(goodsOrder);
    }

    public void updateGoodsOrderToDelivery(GoodsOrderDeliveryDetailsForShipmentRequest request){
        GoodsOrder goodsOrder = findOneByIdAndSeller(request.getOrderId(), goodsSellerAccountService.findBySecurityContextHolder());
        goodsOrder.setOrderStatus(GoodsOrderStatus.WAITING_FOR_DELIVERY);
        goodsOrder.getOrderItems().forEach((i) -> goodsOrderItemService.updateGoodsOrderItemStatusToDelivery(i));
        goodsOrderDeliveryDetailsService.updateGoodsOrderDeliveryDetailsToShipment(request, goodsOrder.getDeliveryDetails());
        goodsOrderRepository.save(goodsOrder);
    }

    private GoodsSellerAccount getSellerAccountFromGoodsItemRequest(GoodsOrderItemRequest request) {
        return goodsAdvertisementService.findById(request.getGoodsAdvertisementId()).getSeller();
    }

    private void validateGoodsOrderRequest(GoodsOrderRequest request){
        SellerAccount sellerAccount = getSellerAccountFromGoodsItemRequest(request.getItems().get(0));
        request.getItems().forEach((i) -> {
            if (sellerAccount != getSellerAccountFromGoodsItemRequest(i)) throw new IllegalArgumentException("Non one seller in order request");
            if (i.getCount() > goodsAdvertisementService.findById(i.getGoodsAdvertisementId()).getCount()) throw new IllegalArgumentException("you want to by more than seller have");
        });
    }


    private GoodsOrder findOneByIdAndSeller(Long id, GoodsSellerAccount seller){
        return goodsOrderRepository.findOneByIdAndSeller(id, seller).orElseThrow(IllegalArgumentException::new);
    }

    private GoodsOrder findOneByIdAndUser(Long id, UserAccount user){
        return goodsOrderRepository.findOneByIdAndUser(id, user).orElseThrow(IllegalArgumentException::new);
    }

    public void confirmGoodsOrderDelivery(ConfirmGoodsOrderDeliveryRequest request) {
        GoodsOrder goodsOrder = findOneByIdAndUser(request.getOrderId(), userAccountService.findBySecurityContextHolder());
        request.getOrderItems().forEach(
                (i) -> goodsOrderItemService.confirmGoodsOrderItemDelivery(goodsOrderItemService.findById(i))
        );
        if (goodsOrder.didAllGoodsOrderItemsDelivered()) {
            goodsOrder.setOrderStatus(WARRANTY_PERIOD);
            goodsOrderRepository.save(goodsOrder);
        }

    }
}
