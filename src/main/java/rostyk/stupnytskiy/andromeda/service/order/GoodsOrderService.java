package rostyk.stupnytskiy.andromeda.service.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.andromeda.dto.request.PaginationRequest;
import rostyk.stupnytskiy.andromeda.dto.request.order.*;
import rostyk.stupnytskiy.andromeda.dto.response.PageResponse;
import rostyk.stupnytskiy.andromeda.dto.response.order.GoodsOrderResponse;
import rostyk.stupnytskiy.andromeda.dto.response.order.SellerGoodsOrdersDataResponse;
import rostyk.stupnytskiy.andromeda.dto.response.order.UserGoodsOrdersDataResponse;
import rostyk.stupnytskiy.andromeda.entity.account.goods_seller.GoodsSellerAccount;
import rostyk.stupnytskiy.andromeda.entity.account.user_account.UserAccount;
import rostyk.stupnytskiy.andromeda.entity.order.GoodsOrder;
import rostyk.stupnytskiy.andromeda.entity.order.GoodsOrderPaymentDetails;
import rostyk.stupnytskiy.andromeda.entity.order.GoodsOrderStatus;
import rostyk.stupnytskiy.andromeda.repository.order.goods_order.GoodsOrderPaymentDetailsRepository;
import rostyk.stupnytskiy.andromeda.repository.order.goods_order.GoodsOrderRepository;
import rostyk.stupnytskiy.andromeda.service.CurrencyService;
import rostyk.stupnytskiy.andromeda.service.DeliveryTypeService;
import rostyk.stupnytskiy.andromeda.service.UserDeliveryAddressService;
import rostyk.stupnytskiy.andromeda.service.account.UserAccountService;
import rostyk.stupnytskiy.andromeda.service.account.seller.GoodsSellerAccountService;
import rostyk.stupnytskiy.andromeda.service.advertisement.goods_advertisement.GoodsAdvertisementService;
import rostyk.stupnytskiy.andromeda.service.cart.CartService;
import rostyk.stupnytskiy.andromeda.service.notification.NotificationService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static rostyk.stupnytskiy.andromeda.entity.order.GoodsOrderStatus.*;

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

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private GoodsOrderPaymentDetailsRepository goodsOrderPaymentDetailsRepository;

    @Autowired
    private CurrencyService currencyService;




    public void createGoodsOrder(GoodsOrderRequest request) {
        validateGoodsOrderRequest(request);
        GoodsOrder goodsOrder = goodsOrderRequestToGoodsOrderAndSave(request);
        request.getItems().forEach((i) -> goodsOrderItemService.save(i, goodsOrder));
        notificationService.createNewOrderNotification(goodsOrder);
    }

    private GoodsOrder goodsOrderRequestToGoodsOrderAndSave(GoodsOrderRequest request) {
        GoodsOrder goodsOrder = new GoodsOrder();
        goodsOrder.setCreationDate(LocalDateTime.now());
        goodsOrder.setOrderStatus(WAITING_FOR_SELLER_CONFIRMATION);
        goodsOrder.setUser(userAccountService.findBySecurityContextHolderOrReturnNull());
        goodsOrder.setSeller(getSellerAccountFromGoodsItemRequest(request.getItems().get(0)));
        goodsOrder.setIsViewed(false);
        goodsOrder.setSum(Math.round(request.getSum() * 100.0) / 100.0);
        goodsOrder.setPaymentDetails(requestToGoodsOrderPaymentRequest(request.getPayment()));
        goodsOrder.setDeliveryDetails(goodsOrderDeliveryDetailsService.userDeliveryAddressToDeliveryDetails(userDeliveryAddressService.findById(request.getAddressId()), deliveryTypeService.findById(request.getDeliveryTypeId())));
        return goodsOrderRepository.save(goodsOrder);
    }

    public void confirmGoodsOrderSending(GoodsOrderDeliveryDetailsForShipmentRequest request) {
        GoodsOrder goodsOrder = findOneByIdAndSeller(request.getOrderId(), goodsSellerAccountService.findBySecurityContextHolder());
        goodsOrder.setOrderStatus(GoodsOrderStatus.WAITING_FOR_DELIVERY);
        goodsOrder.getOrderItems().forEach((i) -> goodsOrderItemService.confirmGoodsOrderItemSending(i));
        goodsOrderDeliveryDetailsService.updateGoodsOrderDeliveryDetailsToShipment(request, goodsOrder.getDeliveryDetails());
        goodsOrderRepository.save(goodsOrder);
        notificationService.createOrderSendNotification(goodsOrder);
    }

    private GoodsSellerAccount getSellerAccountFromGoodsItemRequest(GoodsOrderItemRequest request) {
        return goodsAdvertisementService.findById(request.getGoodsAdvertisementId()).getSeller();
    }

    private void validateGoodsOrderRequest(GoodsOrderRequest request) {
        GoodsSellerAccount sellerAccount = getSellerAccountFromGoodsItemRequest(request.getItems().get(0));
        request.getItems().forEach((i) -> {
            if (sellerAccount != getSellerAccountFromGoodsItemRequest(i))
                throw new IllegalArgumentException("Non one seller in order request");
//            if (i.getCount() > goodsAdvertisementService.findById(i.getGoodsAdvertisementId()).getCount())
//                throw new IllegalArgumentException("you want to by more than seller have");
        });
    }


    private GoodsOrder findOneByIdAndSeller(Long id, GoodsSellerAccount seller) {
        return goodsOrderRepository.findOneByIdAndSeller(id, seller).orElseThrow(IllegalArgumentException::new);
    }

    public GoodsOrder findOneByIdAndUser(Long id, UserAccount user) {
        return goodsOrderRepository.findOneByIdAndUser(id, user).orElseThrow(IllegalArgumentException::new);
    }

    public void confirmGoodsOrderDelivery(ConfirmGoodsOrderDeliveryRequest request) {
        GoodsOrder goodsOrder = findOneByIdAndUser(request.getOrderId(), userAccountService.findBySecurityContextHolderOrReturnNull());
        goodsOrder.setClosingDate(LocalDateTime.now());
        request.getOrderItems().forEach(
                (i) -> goodsOrderItemService.confirmGoodsOrderItemDelivery(goodsOrderItemService.findById(i))
        );
        if (goodsOrder.isAllGoodsOrderItemsDelivered()) {
            goodsOrder.setOrderStatus(WAITING_FOR_FEEDBACK);
            goodsOrderRepository.save(goodsOrder);
        }
        notificationService.createOrderReceivedNotification(goodsOrder);

    }

    public void makeGoodsOrderClosed(GoodsOrder order) {
        order.setOrderStatus(CLOSED);
        goodsOrderRepository.save(order);
    }


    public GoodsOrderResponse getUserGoodsOrderById(Long id) {
        return new GoodsOrderResponse(findOneByIdAndUser(id, userAccountService.findBySecurityContextHolderOrReturnNull()));
    }

    public GoodsOrderResponse getSellerGoodsOrderById(Long id) {
        return new GoodsOrderResponse(findOneByIdAndSeller(id, goodsSellerAccountService.findBySecurityContextHolder()));
    }

    public void confirmGoodsOrderView(Long id) {
        GoodsOrder order = findOneByIdAndSeller(id, goodsSellerAccountService.findBySecurityContextHolder());
        order.setIsViewed(true);
        goodsOrderRepository.save(order);
    }

    public UserGoodsOrdersDataResponse getUserGoodsOrderData() {
        UserAccount user = userAccountService.findBySecurityContextHolderOrReturnNull();
        return UserGoodsOrdersDataResponse
                .builder()
                .closedOrders((long) (goodsOrderRepository.findAllByUserAndOrderStatus(user, CLOSED).size() + goodsOrderRepository.findAllByUserAndOrderStatus(user, WAITING_FOR_FEEDBACK).size()))
                .waitingForSending((long) goodsOrderRepository.findAllByUserAndOrderStatus(user, WAITING_FOR_SENDING).size())
                .waitingForDelivery((long) goodsOrderRepository.findAllByUserAndOrderStatus(user, WAITING_FOR_DELIVERY).size())
                .allItems((long) goodsOrderRepository.findAllByUser(user).size())
                .build();
    }


    public SellerGoodsOrdersDataResponse getSellerGoodsOrderData() {
        GoodsSellerAccount seller = goodsSellerAccountService.findBySecurityContextHolder();
        return SellerGoodsOrdersDataResponse
                .builder()
                .closedOrders((long) (goodsOrderRepository.findAllBySellerAndOrderStatus(seller, CLOSED).size() + goodsOrderRepository.findAllBySellerAndOrderStatus(seller, WAITING_FOR_FEEDBACK).size()))
                .waitingForSending((long) goodsOrderRepository.findAllBySellerAndOrderStatus(seller, WAITING_FOR_SENDING).size())
                .waitingForDelivery((long) goodsOrderRepository.findAllBySellerAndOrderStatus(seller, WAITING_FOR_DELIVERY).size())
                .allItems((long) goodsOrderRepository.findAllBySeller(seller).size())
                .newItems((long) goodsOrderRepository.findAllBySellerAndIsViewedIsFalse(seller).size())
                .build();
    }

    public PageResponse<GoodsOrderResponse> getOrdersPageForSellerByOrderStatus(PaginationRequest request, GoodsOrderStatus[] statuses) {
        GoodsSellerAccount seller = goodsSellerAccountService.findBySecurityContextHolder();
        Page<GoodsOrder> page;
        if (statuses == null || statuses.length == 0) page = goodsOrderRepository.findPageBySeller(seller, request.mapToPageable());
        else page = goodsOrderRepository.findPageBySellerAndOrderStatusIn(seller, statuses, request.mapToPageable());
        return new PageResponse<>(page.get().map(GoodsOrderResponse::new).collect(Collectors.toList()), page.getTotalElements(), page.getTotalPages());
    }

    public GoodsOrderPaymentDetails requestToGoodsOrderPaymentRequest(GoodsOrderPaymentRequest request) {
        GoodsOrderPaymentDetails paymentDetails = new GoodsOrderPaymentDetails();
        paymentDetails.setPaymentMethod(request.getMethod());
        paymentDetails.setCurrency(currencyService.findCurrencyByCurrencyCode(request.getCurrency()));
        return goodsOrderPaymentDetailsRepository.save(paymentDetails);
    }
}
