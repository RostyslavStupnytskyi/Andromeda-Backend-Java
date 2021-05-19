package rostyk.stupnytskiy.andromeda.service.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.andromeda.dto.request.PaginationRequest;
import rostyk.stupnytskiy.andromeda.dto.request.order.*;
import rostyk.stupnytskiy.andromeda.dto.request.order.confirm.GoodsOrderSellerConfirmRequest;
import rostyk.stupnytskiy.andromeda.dto.response.PageResponse;
import rostyk.stupnytskiy.andromeda.dto.response.order.GoodsOrderResponse;
import rostyk.stupnytskiy.andromeda.dto.response.order.SellerGoodsOrdersDataResponse;
import rostyk.stupnytskiy.andromeda.dto.response.order.UserGoodsOrdersDataResponse;
import rostyk.stupnytskiy.andromeda.entity.account.goods_seller.GoodsSellerAccount;
import rostyk.stupnytskiy.andromeda.entity.account.user_account.UserAccount;
import rostyk.stupnytskiy.andromeda.entity.message.Chat;
import rostyk.stupnytskiy.andromeda.entity.order.GoodsOrder;
import rostyk.stupnytskiy.andromeda.entity.order.GoodsOrderPaymentDetails;
import rostyk.stupnytskiy.andromeda.entity.order.GoodsOrderPaymentMethod;
import rostyk.stupnytskiy.andromeda.entity.order.GoodsOrderStatus;
import rostyk.stupnytskiy.andromeda.entity.order.changes.GoodsOrderSellerChange;
import rostyk.stupnytskiy.andromeda.entity.order.changes.GoodsOrderSellerChangeType;
import rostyk.stupnytskiy.andromeda.repository.order.goods_order.GoodsOrderPaymentDetailsRepository;
import rostyk.stupnytskiy.andromeda.repository.order.goods_order.GoodsOrderRepository;
import rostyk.stupnytskiy.andromeda.repository.order.goods_order.GoodsOrderSellerChangeRepository;
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

    @Autowired
    private GoodsOrderSellerChangeRepository goodsOrderSellerChangeRepository;


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
        goodsOrder.setChat(new Chat(Arrays.asList(goodsOrder.getUser(), goodsOrder.getSeller())));
        goodsOrder.setSum(Math.round(request.getSum() * 100.0) / 100.0);
        goodsOrder.setPaymentDetails(requestToGoodsOrderPaymentRequest(request.getPayment()));
        goodsOrder.setDeliveryDetails(goodsOrderDeliveryDetailsService.userDeliveryAddressToDeliveryDetails(userDeliveryAddressService.findById(request.getAddressId()), deliveryTypeService.findById(request.getDeliveryTypeId())));
        return goodsOrderRepository.save(goodsOrder);
    }

    public void confirmGoodsOrderSending(Long id, GoodsOrderDeliveryDetailsForShipmentRequest request) {
        GoodsOrder goodsOrder = findOneByIdAndSeller(id, goodsSellerAccountService.findBySecurityContextHolder());
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

    public void confirmGoodsOrderDelivery(Long id) {
        GoodsOrder goodsOrder = findOneByIdAndUser(id, userAccountService.findBySecurityContextHolderOrReturnNull());
        goodsOrder.setOrderStatus(WAITING_FOR_FEEDBACK);
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
        if (statuses == null || statuses.length == 0)
            page = goodsOrderRepository.findPageBySeller(seller, request.mapToPageable());
        else page = goodsOrderRepository.findPageBySellerAndOrderStatusIn(seller, statuses, request.mapToPageable());
        return new PageResponse<>(page.get().map(GoodsOrderResponse::new).collect(Collectors.toList()), page.getTotalElements(), page.getTotalPages());
    }

    public PageResponse<GoodsOrderResponse> getOrdersPageForUserByOrderStatus(PaginationRequest request, GoodsOrderStatus[] statuses) {
        UserAccount user = userAccountService.findBySecurityContextHolderOrReturnNull();
        Page<GoodsOrder> page;
        if (statuses == null || statuses.length == 0)
            page = goodsOrderRepository.findPageByUser(user, request.mapToPageable());
        else page = goodsOrderRepository.findPageByUserAndOrderStatusIn(user, statuses, request.mapToPageable());
        return new PageResponse<>(page.get().map(GoodsOrderResponse::new).collect(Collectors.toList()), page.getTotalElements(), page.getTotalPages());

    }

    public GoodsOrderPaymentDetails requestToGoodsOrderPaymentRequest(GoodsOrderPaymentRequest request) {
        GoodsOrderPaymentDetails paymentDetails = new GoodsOrderPaymentDetails();
        paymentDetails.setPaymentMethod(request.getMethod());
        paymentDetails.setCurrency(currencyService.findCurrencyByCurrencyCode(request.getCurrency()));
        return goodsOrderPaymentDetailsRepository.save(paymentDetails);
    }

    public void confirmGoodsOrderFromSeller(Long id, GoodsOrderSellerConfirmRequest request) {
        GoodsOrder order = findOneByIdAndSeller(id, goodsSellerAccountService.findBySecurityContextHolder());
        if ((Math.round(request.getSum() * 100.0) / 100.0) != order.getSum()) {
            GoodsOrderSellerChange change = new GoodsOrderSellerChange();
            change.setOrder(order);
            change.setType(GoodsOrderSellerChangeType.CHANGE_SUM);
            change.setValueFrom(order.getSum().toString());
            change.setValueTo((Math.round(request.getSum() * 100.0) / 100.0) + "");
            goodsOrderSellerChangeRepository.save(change);
            order.setSum((Math.round(request.getSum() * 100.0) / 100.0));
        }
        if (!request.getPayment().getCurrency().equals(order.getPaymentDetails().getCurrency().getCode())) {
            GoodsOrderSellerChange change = new GoodsOrderSellerChange();
            change.setOrder(order);
            change.setType(GoodsOrderSellerChangeType.CHANGE_CURRENCY);
            change.setValueFrom(order.getPaymentDetails().getCurrency().getCode());
            change.setValueTo(request.getPayment().getCurrency());
            goodsOrderSellerChangeRepository.save(change);
            order.getOrderItems().forEach((i) -> {
                goodsOrderItemService.exchangeItemPriceAndSave(i, request.getPayment().getCurrency());
            });
            order.getPaymentDetails().setCurrency(currencyService.findCurrencyByCurrencyCode(request.getPayment().getCurrency()));
        }
        if (request.getPayment().getMethod() != order.getPaymentDetails().getPaymentMethod()) {
            GoodsOrderSellerChange change = new GoodsOrderSellerChange();
            change.setOrder(order);
            change.setType(GoodsOrderSellerChangeType.CHANGE_PAYMENT);
            change.setValueFrom(order.getPaymentDetails().getPaymentMethod().toString());
            change.setValueTo(request.getPayment().getMethod().toString());
            goodsOrderSellerChangeRepository.save(change);
            order.getPaymentDetails().setPaymentMethod(request.getPayment().getMethod());
        }
        if (!request.getDeliveryTypeId().equals(order.getDeliveryDetails().getDeliveryType().getId())) {
            GoodsOrderSellerChange change = new GoodsOrderSellerChange();
            change.setOrder(order);
            change.setType(GoodsOrderSellerChangeType.CHANGE_DELIVERY);
            change.setValueFrom(order.getDeliveryDetails().getDeliveryType().getTitle());
            change.setValueTo(deliveryTypeService.findById(request.getDeliveryTypeId()).getTitle());
            goodsOrderSellerChangeRepository.save(change);
            order.getDeliveryDetails().setDeliveryType(deliveryTypeService.findById(request.getDeliveryTypeId()));
        }
        if (order.getPaymentDetails().getPaymentMethod() == GoodsOrderPaymentMethod.CASH_ON_DELIVERY) {
            order.setOrderStatus(WAITING_FOR_SENDING);
        } else {
            order.setOrderStatus(WAITING_FOR_USER_PAYMENT);
        }

        goodsOrderRepository.save(order);
    }


    public void confirmGoodsOrderPayment(Long id) {
        GoodsOrder order = findOneByIdAndUser(id, userAccountService.findBySecurityContextHolderOrReturnNull());
        order.setOrderStatus(WAITING_FOR_PAYMENT_VERIFY);
        goodsOrderRepository.save(order);
    }

    public void confirmGoodsOrderPaymentFromSeller(Long id) {
        GoodsOrder order = findOneByIdAndSeller(id, goodsSellerAccountService.findBySecurityContextHolder());
        order.setOrderStatus(WAITING_FOR_SENDING);
        goodsOrderRepository.save(order);
    }
}
