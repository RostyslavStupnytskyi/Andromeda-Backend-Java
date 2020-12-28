package rostyk.stupnytskiy.andromeda.service.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.andromeda.repository.GoodsOrderItemRepository;

@Service
public class GoodsOrderItemService {

    @Autowired
    private GoodsOrderItemRepository goodsOrderItemRepository;



}
