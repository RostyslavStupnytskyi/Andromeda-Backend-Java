package rostyk.stupnytskiy.andromeda.controller.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rostyk.stupnytskiy.andromeda.dto.request.PaginationRequest;
import rostyk.stupnytskiy.andromeda.dto.request.order.ConfirmGoodsOrderDeliveryRequest;
import rostyk.stupnytskiy.andromeda.dto.request.order.GoodsOrderDeliveryDetailsForShipmentRequest;
import rostyk.stupnytskiy.andromeda.dto.request.order.GoodsOrderRequest;
import rostyk.stupnytskiy.andromeda.dto.request.order.confirm.GoodsOrderSellerConfirmRequest;
import rostyk.stupnytskiy.andromeda.dto.response.PageResponse;
import rostyk.stupnytskiy.andromeda.dto.response.order.GoodsOrderResponse;
import rostyk.stupnytskiy.andromeda.dto.response.order.UserGoodsOrdersDataResponse;
import rostyk.stupnytskiy.andromeda.entity.order.GoodsOrderStatus;
import rostyk.stupnytskiy.andromeda.service.order.GoodsOrderItemService;
import rostyk.stupnytskiy.andromeda.service.order.GoodsOrderService;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/goods-order")
public class GoodsOrderController {

    @Autowired
    private GoodsOrderService goodsOrderService;
    @Autowired
    private GoodsOrderItemService goodsOrderItemService;

    @PostMapping
    private void createOrder(@Valid @RequestBody GoodsOrderRequest request) {
        goodsOrderService.createGoodsOrder(request);
    }

    @PutMapping("/shipment")
    private void confirmGoodsOrderSending(Long id, @Valid @RequestBody GoodsOrderDeliveryDetailsForShipmentRequest request) {
        goodsOrderService.confirmGoodsOrderSending(id, request);
    }

    @PutMapping("/view")
    private void confirmGoodsOrderView(Long id) {
        goodsOrderService.confirmGoodsOrderView(id);
    }


    @PutMapping("/confirm")
    private void confirmGoodsOrderFromSeller(Long id, @RequestBody GoodsOrderSellerConfirmRequest request) {
        goodsOrderService.confirmGoodsOrderFromSeller(id, request);
    }

    @PutMapping("/confirm-payment")
    private void confirmGoodsOrderPayment(Long id) {
        goodsOrderService.confirmGoodsOrderPayment(id);
    }


    @PutMapping("/confirm-payment-seller")
    private void confirmGoodsOrderPaymentFromSeller(Long id) {
        goodsOrderService.confirmGoodsOrderPaymentFromSeller(id);
    }


    @PutMapping("/confirm-delivery")
    private void confirmGoodsOrderDelivery( Long id) {
        goodsOrderService.confirmGoodsOrderDelivery(id);
    }

    @GetMapping("/seller")
    private PageResponse<GoodsOrderResponse> getSellerOrdersByStatus(PaginationRequest request, GoodsOrderStatus[] status) {
        return goodsOrderService.getOrdersPageForSellerByOrderStatus(request, status);
    }

    @GetMapping("/user")
    private PageResponse<GoodsOrderResponse> getUserOrdersByStatus(PaginationRequest request, GoodsOrderStatus[] status) {
        return goodsOrderService.getOrdersPageForUserByOrderStatus(request, status);
    }

    @GetMapping("/user/order")
    private GoodsOrderResponse getUserOrder(Long id) {
        return goodsOrderService.getUserGoodsOrderById(id);
    }


    @GetMapping("/seller/order")
    private GoodsOrderResponse getSellerOrder(Long id) {
        return goodsOrderService.getSellerGoodsOrderById(id);
    }

    @GetMapping("/user/data")
    private UserGoodsOrdersDataResponse getUserGoodsOrderDataResponse(){
        return goodsOrderService.getUserGoodsOrderData();
    }

    @GetMapping("/exchange")
    private Double exchangeItemPrice(Long id, String currency){
        return goodsOrderItemService.exchangeItemPrice(id, currency);
    }



}
