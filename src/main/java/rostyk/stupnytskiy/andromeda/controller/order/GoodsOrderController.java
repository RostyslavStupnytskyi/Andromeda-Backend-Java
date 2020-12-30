package rostyk.stupnytskiy.andromeda.controller.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rostyk.stupnytskiy.andromeda.dto.request.order.ConfirmGoodsOrderDeliveryRequest;
import rostyk.stupnytskiy.andromeda.dto.request.order.GoodsOrderDeliveryDetailsForShipmentRequest;
import rostyk.stupnytskiy.andromeda.dto.request.order.GoodsOrderRequest;
import rostyk.stupnytskiy.andromeda.service.order.GoodsOrderService;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/goods-order")
public class GoodsOrderController {

    @Autowired
    private GoodsOrderService goodsOrderService;


    @PostMapping
    private void createOrder(@Valid @RequestBody GoodsOrderRequest request){
        goodsOrderService.createGoodsOrder(request);
    }

    @PutMapping("/shipment")
    private void updateGoodsOrderToShipment(@Valid @RequestBody GoodsOrderDeliveryDetailsForShipmentRequest request){
        goodsOrderService.updateGoodsOrderToDelivery(request);
    }

    @PutMapping()
    private void confirmGoodsOrderDelivery(@Valid @RequestBody ConfirmGoodsOrderDeliveryRequest request){
        goodsOrderService.confirmGoodsOrderDelivery(request);
    }

}
