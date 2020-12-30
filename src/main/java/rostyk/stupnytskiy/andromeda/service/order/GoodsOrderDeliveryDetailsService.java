package rostyk.stupnytskiy.andromeda.service.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.andromeda.dto.request.order.GoodsOrderDeliveryDetailsForShipmentRequest;
import rostyk.stupnytskiy.andromeda.entity.UserDeliveryAddress;
import rostyk.stupnytskiy.andromeda.entity.order.GoodsOrderDeliveryDetails;
import rostyk.stupnytskiy.andromeda.repository.GoodsOrderDeliveryDetailsRepository;
import rostyk.stupnytskiy.andromeda.service.DeliveryTypeService;

@Service
public class GoodsOrderDeliveryDetailsService {

    @Autowired
    private GoodsOrderDeliveryDetailsRepository goodsOrderDeliveryDetailsRepository;

    @Autowired
    private DeliveryTypeService deliveryTypeService;

    public GoodsOrderDeliveryDetails userDeliveryAddressToDeliveryDetails(UserDeliveryAddress address) {
        GoodsOrderDeliveryDetails goodsOrderDeliveryDetails = new GoodsOrderDeliveryDetails();
        goodsOrderDeliveryDetails.setCity(address.getCity());
        goodsOrderDeliveryDetails.setRecipient(address.getRecipient());
        goodsOrderDeliveryDetails.setRegion(address.getRegion());
        goodsOrderDeliveryDetails.setPhoneNumber(address.getPhoneNumber());
        goodsOrderDeliveryDetails.setStreet(address.getStreet());
        return save(goodsOrderDeliveryDetails);
    }

    public GoodsOrderDeliveryDetails save(GoodsOrderDeliveryDetails orderDeliveryDetails){
        return goodsOrderDeliveryDetailsRepository.save(orderDeliveryDetails);
    }

    public void updateGoodsOrderDeliveryDetailsToShipment(GoodsOrderDeliveryDetailsForShipmentRequest request, GoodsOrderDeliveryDetails details){
        details.setShipment(deliveryTypeService.findById(request.getDeliveryTypeId()).getTitle());
        details.setTrackingNumber(request.getTrackingNumber());
        goodsOrderDeliveryDetailsRepository.save(details);
    }
}
