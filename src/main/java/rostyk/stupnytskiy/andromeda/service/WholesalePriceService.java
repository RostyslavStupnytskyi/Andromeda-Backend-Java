package rostyk.stupnytskiy.andromeda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.andromeda.dto.request.advertisement.WholesalePriceRequest;
import rostyk.stupnytskiy.andromeda.dto.request.advertisement.WholesalePriceUnitRequest;
import rostyk.stupnytskiy.andromeda.entity.advertisement.WholesalePrice;
import rostyk.stupnytskiy.andromeda.entity.advertisement.WholesalePriceUnit;
import rostyk.stupnytskiy.andromeda.repository.WholesalePriceRepository;
import rostyk.stupnytskiy.andromeda.repository.WholesalePriceUnitRepository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.stream.Collectors;

@Service
public class WholesalePriceService {

    @Autowired
    private WholesalePriceRepository wholesalePriceRepository;

    @Autowired
    private WholesalePriceUnitRepository wholesalePriceUnitRepository;

    public WholesalePrice wholesalePriceRequestToWholesalePrice(WholesalePriceRequest request){
        WholesalePrice wholesalePrice = new WholesalePrice();
        wholesalePrice.setDateTime(LocalDateTime.now(ZoneOffset.UTC));
        wholesalePrice.setPriceUnits(request.getPriceUnits()
                .stream()
                .map(this::wholesalePriceUnitRequestToWholesalePriceUnit)
                .collect(Collectors.toList())
        );

        return wholesalePrice;
    }

    public WholesalePriceUnit wholesalePriceUnitRequestToWholesalePriceUnit(WholesalePriceUnitRequest request){
        WholesalePriceUnit wholesalePriceUnit = new WholesalePriceUnit();
        wholesalePriceUnit.setMax(request.getMax());
        wholesalePriceUnit.setMin(request.getMin());
        wholesalePriceUnit.setPrice(request.getPrice());

        return wholesalePriceUnit;
    }

}
