package rostyk.stupnytskiy.andromeda.service.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.andromeda.dto.request.order.GoodsOrderItemRequest;
import rostyk.stupnytskiy.andromeda.dto.request.order.GoodsOrderRequest;
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
import rostyk.stupnytskiy.andromeda.service.advertisement.goods_advertisement.GoodsAdvertisementService;

import java.time.LocalDateTime;
import java.util.List;

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
    private DeliveryTypeService deliveryTypeService;

    @Autowired
    private GoodsOrderItemService goodsOrderItemService;

    public void createGoodsOrder(GoodsOrderRequest request) {
        GoodsOrder goodsOrder = goodsOrderRequestToGoodsOrder(request);
        request.getItems().forEach((i) -> goodsOrderItemService.save(i, goodsOrder));
        //testing
    }

    private GoodsOrder goodsOrderRequestToGoodsOrder(GoodsOrderRequest request) {
        GoodsOrder goodsOrder = new GoodsOrder();
        goodsOrder.setCreationDate(LocalDateTime.now());
        goodsOrder.setOrderStatus(GoodsOrderStatus.WAITING_FOR_SENDING);
        goodsOrder.setDeliveryType(deliveryTypeService.findById(request.getAddressId()));
        goodsOrder.setUser(userAccountService.findBySecurityContextHolder());
        goodsOrder.setAddress(userDeliveryAddressService.cloneAddressForOrder(userDeliveryAddressService.findById(request.getAddressId())));
        goodsOrder.setSeller(getSellerAccountFromGoodsItemRequest(request.getItems().get(0)));
        return goodsOrderRepository.save(goodsOrder);
    }

    private GoodsSellerAccount getSellerAccountFromGoodsItemRequest(GoodsOrderItemRequest request) {
        return goodsAdvertisementService.findById(request.getGoodsAdvertisementId()).getSeller();
    }

    private void validateGoodsOrderRequest(GoodsOrderRequest request){
        SellerAccount sellerAccount = getSellerAccountFromGoodsItemRequest(request.getItems().get(0));
        request.getItems().forEach((i) -> {
            if (sellerAccount != getSellerAccountFromGoodsItemRequest(i)) throw new IllegalArgumentException("Non one seller");
        });

    }

}
