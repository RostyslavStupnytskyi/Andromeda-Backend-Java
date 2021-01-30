package rostyk.stupnytskiy.andromeda.service.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.andromeda.dto.request.order.GoodsOrderItemRequest;
import rostyk.stupnytskiy.andromeda.entity.account.user_account.UserAccount;
import rostyk.stupnytskiy.andromeda.entity.order.GoodsOrder;
import rostyk.stupnytskiy.andromeda.entity.order.order_item.GoodsOrderItem;
import rostyk.stupnytskiy.andromeda.entity.order.order_item.GoodsOrderItemStatus;
import rostyk.stupnytskiy.andromeda.repository.GoodsOrderItemRepository;
import rostyk.stupnytskiy.andromeda.service.DeliveryTypeService;
import rostyk.stupnytskiy.andromeda.service.advertisement.goods_advertisement.GoodsAdvertisementService;
import rostyk.stupnytskiy.andromeda.service.statistics.advertisement.GoodsAdvertisementStatisticsService;

@Service
public class GoodsOrderItemService {

    @Autowired
    private GoodsOrderItemRepository goodsOrderItemRepository;

    @Autowired
    private GoodsAdvertisementService goodsAdvertisementService;

    @Autowired
    private DeliveryTypeService deliveryTypeService;

    @Autowired
    private GoodsAdvertisementStatisticsService goodsAdvertisementStatisticsService;


    public void save(GoodsOrderItemRequest request, GoodsOrder goodsOrder) {
//        GoodsOrderItem orderItem = ;
        goodsOrderItemRepository.save(goodsOrderItemRequestToGoodsOrderItem(request, goodsOrder));
        goodsAdvertisementService.minusAdvertisementCount(request.getGoodsAdvertisementId(), request.getCount());
        goodsAdvertisementStatisticsService.incrementGoodsAdvertisementSoldNumber(request.getGoodsAdvertisementId(), request.getCount());
        goodsAdvertisementStatisticsService.incrementGoodsAdvertisementOrdersNumber(request.getGoodsAdvertisementId());
    }

    public void confirmGoodsOrderItemSending(GoodsOrderItem orderItem) {
        orderItem.setStatus(GoodsOrderItemStatus.WAITING_FOR_DELIVERY);
        goodsOrderItemRepository.save(orderItem);
    }

    public void confirmGoodsOrderItemDelivery(GoodsOrderItem orderItem) {
        orderItem.setStatus(GoodsOrderItemStatus.WAITING_FOR_FEEDBACK);
        goodsOrderItemRepository.save(orderItem);
    }

    public GoodsOrderItem goodsOrderItemRequestToGoodsOrderItem(GoodsOrderItemRequest request, GoodsOrder goodsOrder) {
        GoodsOrderItem goodsOrderItem = new GoodsOrderItem();
        goodsOrderItem.setCount(request.getCount());
        goodsOrderItem.setDescriptionFromUser(request.getDescription());
        goodsOrderItem.setDeliveryType(deliveryTypeService.findById(request.getDeliveryTypeId()));
        goodsOrderItem.setStatus(GoodsOrderItemStatus.WAITING_FOR_SENDING);
        goodsOrderItem.setGoodsAdvertisement(goodsAdvertisementService.findById(request.getGoodsAdvertisementId()));
        goodsOrderItem.setGoodsOrder(goodsOrder);
        return goodsOrderItem;
    }

    public GoodsOrderItem findById(Long id) {
        return goodsOrderItemRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    public GoodsOrderItem findByIdAndUser(Long id, UserAccount user) {
        return goodsOrderItemRepository.findByIdAndGoodsOrderUser(id, user).orElseThrow(IllegalArgumentException::new);
    }

}
