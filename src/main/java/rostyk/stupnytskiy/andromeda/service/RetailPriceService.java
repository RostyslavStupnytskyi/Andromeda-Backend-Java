package rostyk.stupnytskiy.andromeda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.andromeda.dto.request.advertisement.RetailPriceRequest;
import rostyk.stupnytskiy.andromeda.entity.advertisement.RetailPrice;
import rostyk.stupnytskiy.andromeda.repository.RetailPriceRepository;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class RetailPriceService {

    @Autowired
    private RetailPriceRepository retailPriceRepository;

    public RetailPrice retailPriceRequestToRetailPrice(RetailPriceRequest request){
        RetailPrice retailPrice = new RetailPrice();

        retailPrice.setDateTime(LocalDateTime.now(ZoneOffset.UTC));
        retailPrice.setPrice(retailPrice.getPrice());

        return retailPrice;
    }
}
