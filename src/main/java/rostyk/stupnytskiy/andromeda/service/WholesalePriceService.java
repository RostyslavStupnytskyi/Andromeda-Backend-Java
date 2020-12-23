package rostyk.stupnytskiy.andromeda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.andromeda.dto.request.advertisement.goods_advertisement.wholesale.WholesalePriceRequest;
import rostyk.stupnytskiy.andromeda.dto.request.advertisement.goods_advertisement.wholesale.WholesalePriceUnitRequest;
import rostyk.stupnytskiy.andromeda.entity.advertisement.Advertisement;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.wholesale.WholesalePrice;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.wholesale.WholesalePriceUnit;
import rostyk.stupnytskiy.andromeda.repository.WholesalePriceRepository;
import rostyk.stupnytskiy.andromeda.repository.WholesalePriceUnitRepository;

import java.time.LocalDateTime;

@Service
public class WholesalePriceService {

    @Autowired
    private WholesalePriceRepository wholesalePriceRepository;

    @Autowired
    private WholesalePriceUnitRepository wholesalePriceUnitRepository;


    public void save(WholesalePriceRequest request, Advertisement advertisement){
        WholesalePrice wholesalePrice = wholesalePriceRepository.save(wholesalePriceRequestToWholesalePrice(request, advertisement));
        request.getPriceUnits().forEach(
                (priceUnit) ->  wholesalePriceUnitRepository.save(wholesalePriceUnitRequestToWholesalePriceUnit(priceUnit,wholesalePrice))
        );
    }



    public WholesalePrice wholesalePriceRequestToWholesalePrice(WholesalePriceRequest request, Advertisement advertisement){
        WholesalePrice wholesalePrice = new WholesalePrice();
        wholesalePrice.setDateTime(LocalDateTime.now());
//        wholesalePrice.setAdvertisement(advertisement);
        return wholesalePrice;
    }

    public WholesalePrice wholesalePriceRequestToWholesalePrice(WholesalePriceRequest request){
        WholesalePrice wholesalePrice = new WholesalePrice();
        wholesalePrice.setDateTime(LocalDateTime.now());
        return wholesalePrice;
    }

    public WholesalePriceUnit wholesalePriceUnitRequestToWholesalePriceUnit(WholesalePriceUnitRequest request, WholesalePrice wholesalePrice){
        WholesalePriceUnit wholesalePriceUnit = new WholesalePriceUnit();
        wholesalePriceUnit.setMax(request.getMax());
        wholesalePriceUnit.setMin(request.getMin());
        wholesalePriceUnit.setPrice(request.getPrice());
        wholesalePriceUnit.setWholesalePrice(wholesalePrice);
        return wholesalePriceUnit;
    }



}
