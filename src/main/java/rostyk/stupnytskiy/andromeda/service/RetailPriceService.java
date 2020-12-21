package rostyk.stupnytskiy.andromeda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.andromeda.dto.request.advertisement.RetailPriceRequest;
import rostyk.stupnytskiy.andromeda.entity.advertisement.Advertisement;
import rostyk.stupnytskiy.andromeda.entity.advertisement.RetailPrice;
import rostyk.stupnytskiy.andromeda.repository.RetailPriceRepository;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class RetailPriceService {

    @Autowired
    private RetailPriceRepository retailPriceRepository;

    public void save(RetailPriceRequest request, Advertisement advertisement){
        retailPriceRepository.save(retailPriceRequestToRetailPrice(request, advertisement));
    }

    public RetailPrice retailPriceRequestToRetailPrice(RetailPriceRequest request){
        RetailPrice retailPrice = new RetailPrice();
        retailPrice.setDateTime(LocalDateTime.now());
        retailPrice.setPrice(request.getPrice());
        return retailPrice;
    }

    public RetailPrice retailPriceRequestToRetailPrice(RetailPriceRequest request, Advertisement advertisement){
        RetailPrice retailPrice = new RetailPrice();
        retailPrice.setAdvertisement(advertisement);
        retailPrice.setDateTime(LocalDateTime.now());
        retailPrice.setPrice(request.getPrice());
        return retailPrice;
    }
}
