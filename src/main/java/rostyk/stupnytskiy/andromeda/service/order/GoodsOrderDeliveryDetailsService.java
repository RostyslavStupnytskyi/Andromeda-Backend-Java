package rostyk.stupnytskiy.andromeda.service.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.andromeda.dto.request.order.GoodsOrderDeliveryDetailsForShipmentRequest;
import rostyk.stupnytskiy.andromeda.entity.UserDeliveryAddress;
import rostyk.stupnytskiy.andromeda.entity.country.DeliveryType;
import rostyk.stupnytskiy.andromeda.entity.order.GoodsOrderDeliveryDetails;
import rostyk.stupnytskiy.andromeda.repository.order.goods_order.GoodsOrderDeliveryDetailsRepository;
import rostyk.stupnytskiy.andromeda.service.DeliveryTypeService;

@Service
public class GoodsOrderDeliveryDetailsService {

    @Autowired
    private GoodsOrderDeliveryDetailsRepository goodsOrderDeliveryDetailsRepository;

    @Autowired
    private DeliveryTypeService deliveryTypeService;

    public GoodsOrderDeliveryDetails userDeliveryAddressToDeliveryDetails(UserDeliveryAddress address, DeliveryType deliveryType) {
        GoodsOrderDeliveryDetails goodsOrderDeliveryDetails = new GoodsOrderDeliveryDetails();
        goodsOrderDeliveryDetails.setCity(address.getCity());
        goodsOrderDeliveryDetails.setRecipient(address.getRecipient());
        goodsOrderDeliveryDetails.setRegion(address.getRegion());
        goodsOrderDeliveryDetails.setPhoneNumber(address.getPhoneNumber());
        goodsOrderDeliveryDetails.setStreet(address.getStreet());
        goodsOrderDeliveryDetails.setCountry(address.getCountry());
        goodsOrderDeliveryDetails.setHouse(address.getHouse());
        goodsOrderDeliveryDetails.setDeliveryType(deliveryType);
        return save(goodsOrderDeliveryDetails);
    }

    public GoodsOrderDeliveryDetails save(GoodsOrderDeliveryDetails orderDeliveryDetails){
        return goodsOrderDeliveryDetailsRepository.save(orderDeliveryDetails);
    }

    public void updateGoodsOrderDeliveryDetailsToShipment(GoodsOrderDeliveryDetailsForShipmentRequest request, GoodsOrderDeliveryDetails details){
        details.setShipment(deliveryTypeService.findById(request.getDeliveryTypeId()).getTitle());
        details.setTrackingNumber(request.getTrackingNumber());
        if (!request.getSellerMessage().equals(""))
        details.setSellerMessage(request.getSellerMessage());
        goodsOrderDeliveryDetailsRepository.save(details);
    }
}
