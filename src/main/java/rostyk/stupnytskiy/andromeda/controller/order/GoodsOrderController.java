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
import rostyk.stupnytskiy.andromeda.entity.order.GoodsOrderStatus;
import rostyk.stupnytskiy.andromeda.service.order.GoodsOrderService;

import javax.validation.Valid;

import java.util.Arrays;

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

    @GetMapping("/seller")
    private PageResponse<GoodsOrderResponse> getSellerOrdersByStatus(PaginationRequest request, GoodsOrderStatus[] status) {
        return goodsOrderService.getOrdersPageForSellerByOrderStatus(request, status);
    }

    @GetMapping("/user/data")
    private UserGoodsOrdersDataResponse getUserGoodsOrderDataResponse(){
        return goodsOrderService.getUserGoodsOrderData();
    }


}
