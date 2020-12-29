package rostyk.stupnytskiy.andromeda.controller.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
}
