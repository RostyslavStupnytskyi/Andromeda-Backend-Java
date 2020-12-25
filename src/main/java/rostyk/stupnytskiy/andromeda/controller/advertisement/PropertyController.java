package rostyk.stupnytskiy.andromeda.controller.advertisement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rostyk.stupnytskiy.andromeda.dto.request.PaginationRequest;
import rostyk.stupnytskiy.andromeda.dto.request.advertisement.goods_advertisement.PropertyRequest;
import rostyk.stupnytskiy.andromeda.dto.response.PageResponse;
import rostyk.stupnytskiy.andromeda.dto.response.advertisement.goods_advertisement.PropertyResponse;
import rostyk.stupnytskiy.andromeda.service.advertisement.goods_advertisement.PropertyService;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/property")
public class PropertyController {

    @Autowired
    private PropertyService propertyService;

//    @PostMapping
//    public void save(@RequestBody PropertiesRequest request) {
//        propertyService.save(request);
//    }

    @PutMapping
    public void update(Long id, @RequestBody PropertyRequest request) {
//        propertyService.update(request,id);
    }

    @GetMapping
    public List<PropertyResponse> findAllByAdvertisementId(Long id){
        return propertyService.findAllByAdvertisementId(id);
    }

    @GetMapping("/page")
    public PageResponse<PropertyResponse> findPageByAdvertisementId(Long id, PaginationRequest request){
        return propertyService.findPageByAdvertisementId(id,request);
    }

    @DeleteMapping
    public void delete(Long id){
        propertyService.delete(id);
    }
}
