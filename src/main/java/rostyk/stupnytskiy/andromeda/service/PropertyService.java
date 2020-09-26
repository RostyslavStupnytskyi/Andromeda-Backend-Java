package rostyk.stupnytskiy.andromeda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.andromeda.dto.request.PropertyRequest;
import rostyk.stupnytskiy.andromeda.dto.response.PropertyResponse;
import rostyk.stupnytskiy.andromeda.entity.Property;
import rostyk.stupnytskiy.andromeda.repository.PropertyRepository;

import java.util.List;

@Service
public class PropertyService {

    @Autowired
    private AdvertisementService advertisementService;

    @Autowired
    private PropertyRepository propertyRepository;

    public void save(PropertyRequest propertyRequest){
        propertyRepository.save(propertyRequestToProperty(propertyRequest,null));
    }

    public List<PropertyResponse> findAllByAdvertisementId(Long id){

    }

    private Property propertyRequestToProperty(PropertyRequest request, Property property) {
        if (property == null) {
            property = new Property();
        }
        property.setName(request.getName());
        property.setValue(request.getValue());
        property.setAdvertisement(advertisementService.findById(request.getAdvertisementId()));
        return property;
    }
}
