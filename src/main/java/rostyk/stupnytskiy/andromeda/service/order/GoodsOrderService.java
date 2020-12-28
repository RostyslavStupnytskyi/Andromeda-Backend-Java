package rostyk.stupnytskiy.andromeda.service.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.andromeda.dto.request.order.GoodsOrderRequest;
import rostyk.stupnytskiy.andromeda.entity.order.GoodsOrder;
import rostyk.stupnytskiy.andromeda.repository.GoodsOrderRepository;

import java.time.LocalDateTime;

@Service
public class GoodsOrderService {

    @Autowired
    private GoodsOrderRepository goodsOrderRepository;

    @Autowired
    private AddressService addressService;

    public void createGoodsOrder(GoodsOrderRequest request) {

    }

    private GoodsOrder goodsOrderRequestToGoodsOrder(GoodsOrderRequest request) {
        GoodsOrder goodsOrder = new GoodsOrder();
        goodsOrder.setCreationDate(LocalDateTime.now());

//        goodsOrder.ser
    }

}
