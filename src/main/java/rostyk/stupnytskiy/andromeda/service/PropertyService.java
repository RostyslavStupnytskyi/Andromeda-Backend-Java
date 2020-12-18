package rostyk.stupnytskiy.andromeda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.andromeda.dto.request.PaginationRequest;
import rostyk.stupnytskiy.andromeda.dto.request.advertisement.*;
import rostyk.stupnytskiy.andromeda.dto.response.PageResponse;
import rostyk.stupnytskiy.andromeda.dto.response.PropertyResponse;
import rostyk.stupnytskiy.andromeda.entity.advertisement.Property;
import rostyk.stupnytskiy.andromeda.repository.PropertyRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PropertyService {

    @Autowired
    private AdvertisementService advertisementService;

    @Autowired
    private PropertyRepository propertyRepository;

    public void delete(Long id) {
        propertyRepository.delete(findById(id));
    }

    public List<PropertyResponse> findAllByAdvertisementId(Long id) {
        return propertyRepository.findAllByAdvertisementId(id)
                .stream()
                .map(PropertyResponse::new)
                .collect(Collectors.toList());
    }

    public PageResponse<PropertyResponse> findPageByAdvertisementId(Long id, PaginationRequest request){
        final Page<Property> page = propertyRepository.findAllByAdvertisementId(id,request.mapToPageable());
        return new PageResponse<>(page.getContent().stream().map(PropertyResponse::new).collect(Collectors.toList()),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }

    public Property findById(Long id) {
        return propertyRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Property with id " + id + " not exist"));
    }

    public Property propertyRequestToProperty(PropertyRequest request) {
        Property property = new Property();
        property.setName(request.getName());
        property.setValue(request.getValue());
        return property;
    }
}
