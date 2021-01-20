package rostyk.stupnytskiy.andromeda.controller.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rostyk.stupnytskiy.andromeda.dto.request.PaginationRequest;
import rostyk.stupnytskiy.andromeda.dto.request.order.ConfirmGoodsOrderDeliveryRequest;
import rostyk.stupnytskiy.andromeda.dto.request.order.GoodsOrderDeliveryDetailsForShipmentRequest;
import rostyk.stupnytskiy.andromeda.dto.request.order.GoodsOrderRequest;
import rostyk.stupnytskiy.andromeda.dto.response.PageResponse;
import rostyk.stupnytskiy.andromeda.dto.response.order.GoodsOrderResponse;
import rostyk.stupnytskiy.andromeda.dto.response.order.SellerGoodsOrdersDataResponse;
import rostyk.stupnytskiy.andromeda.dto.response.order.UserGoodsOrdersDataResponse;
import rostyk.stupnytskiy.andromeda.service.order.GoodsOrderService;

import javax.validation.Valid;

import static rostyk.stupnytskiy.andromeda.entity.order.GoodsOrderStatus.WAITING_FOR_DELIVERY;
import static rostyk.stupnytskiy.andromeda.entity.order.GoodsOrderStatus.WAITING_FOR_SENDING;

@CrossOrigin
@RestController
@RequestMapping("/goods-order")
public class GoodsOrderController {

    @Autowired
    private GoodsOrderService goodsOrderService;


    @PostMapping
    private void createOrder(@Valid @RequestBody GoodsOrderRequest request) {
        goodsOrderService.createGoodsOrder(request);
    }

    @PutMapping("/shipment")
    private void confirmGoodsOrderSending(@Valid @RequestBody GoodsOrderDeliveryDetailsForShipmentRequest request) {
        goodsOrderService.confirmGoodsOrderSending(request);
    }

    @PutMapping("/view")
    private void confirmGoodsOrderView(Long id) {
        goodsOrderService.confirmGoodsOrderView(id);
    }

    @PutMapping("/confirm")
    private void confirmGoodsOrderDelivery( @RequestBody ConfirmGoodsOrderDeliveryRequest request) {
        goodsOrderService.confirmGoodsOrderDelivery(request);
    }

    @GetMapping("/seller/all")
    private PageResponse<GoodsOrderResponse> getSellerOrders(PaginationRequest request) {
       return goodsOrderService.getAllOrdersPageForSeller(request);
    }

    @GetMapping("/seller/shipment")
    private PageResponse<GoodsOrderResponse> getShipmentSellerOrders(PaginationRequest request) {
        return goodsOrderService.getAllOrdersPageForSellerByOrderStatus(request, WAITING_FOR_SENDING);
    }

    @GetMapping("/seller/order")
    private GoodsOrderResponse getSellerGoodsOrderById(Long id){
        return goodsOrderService.getSellerGoodsOrderById(id);
    }

    @GetMapping("/seller/delivery")
    private PageResponse<GoodsOrderResponse> getDeliverySellerOrders(PaginationRequest request) {
        return goodsOrderService.getAllOrdersPageForUserByOrderStatus(request, WAITING_FOR_DELIVERY);
    }

    @GetMapping("/seller/closed")
    private PageResponse<GoodsOrderResponse> getClosedSellerOrders(PaginationRequest request) {
        return goodsOrderService.getAllClosedOrdersPageForSeller(request);
    }

    @GetMapping("/seller/new")
    private PageResponse<GoodsOrderResponse> getNewSellerOrders(PaginationRequest request) {
        return goodsOrderService.getAllNewOrdersPageForSeller(request);
    }

    @GetMapping("/seller/data")
    private SellerGoodsOrdersDataResponse getSellerGoodsOrderDataResponse(){
        return goodsOrderService.getSellerGoodsOrderData();
    }

    @GetMapping("/user/all")
    private PageResponse<GoodsOrderResponse> getUserOrders(PaginationRequest request) {
        return goodsOrderService.getAllOrdersPageForUser(request);
    }


    @GetMapping("/user/shipment")
    private PageResponse<GoodsOrderResponse> getShipmentUserOrders(PaginationRequest request) {
        return goodsOrderService.getAllOrdersPageForUserByOrderStatus(request, WAITING_FOR_SENDING);
    }

    @GetMapping("/user/delivery")
    private PageResponse<GoodsOrderResponse> getDeliveryUserOrders(PaginationRequest request) {
        return goodsOrderService.getAllOrdersPageForUserByOrderStatus(request, WAITING_FOR_DELIVERY);
    }

    @GetMapping("/user/closed")
    private PageResponse<GoodsOrderResponse> getClosedUserOrders(PaginationRequest request) {
        return goodsOrderService.getAllClosedOrdersPageForUser(request);
    }

    @GetMapping("/user/order")
    private GoodsOrderResponse getUserGoodsOrderById(Long id){
        return goodsOrderService.getUserGoodsOrderById(id);
    }

    @GetMapping("/user/data")
    private UserGoodsOrdersDataResponse getUserGoodsOrderDataResponse(){
        return goodsOrderService.getUserGoodsOrderData();
    }


}
