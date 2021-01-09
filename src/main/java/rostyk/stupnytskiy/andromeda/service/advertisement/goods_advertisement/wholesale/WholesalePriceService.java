package rostyk.stupnytskiy.andromeda.service.advertisement.goods_advertisement.wholesale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.andromeda.dto.request.advertisement.goods_advertisement.wholesale.WholesalePriceRequest;
import rostyk.stupnytskiy.andromeda.dto.request.advertisement.goods_advertisement.wholesale.WholesalePriceUnitRequest;
import rostyk.stupnytskiy.andromeda.entity.advertisement.Advertisement;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.wholesale.WholesaleGoodsAdvertisement;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.wholesale.WholesalePrice;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.wholesale.WholesalePriceUnit;
import rostyk.stupnytskiy.andromeda.repository.WholesalePriceRepository;
import rostyk.stupnytskiy.andromeda.repository.WholesalePriceUnitRepository;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

@Service
public class WholesalePriceService {

    @Autowired
    private WholesalePriceRepository wholesalePriceRepository;

    @Autowired
    private WholesalePriceUnitRepository wholesalePriceUnitRepository;


    public WholesalePrice save(WholesalePriceRequest request, WholesaleGoodsAdvertisement advertisement) {
        WholesalePrice wholesalePrice = wholesalePriceRepository.save(wholesalePriceRequestToWholesalePrice(advertisement));
        request.getPriceUnits().forEach(
                (priceUnit) -> wholesalePriceUnitRepository.save(wholesalePriceUnitRequestToWholesalePriceUnit(priceUnit, wholesalePrice))
        );
        return wholesalePrice;
    }

    public WholesalePrice wholesalePriceRequestToWholesalePrice(WholesaleGoodsAdvertisement advertisement) {
        WholesalePrice wholesalePrice = new WholesalePrice();
        wholesalePrice.setDateTime(LocalDateTime.now());
        wholesalePrice.setAdvertisement(advertisement);
        return wholesalePrice;
    }

    public WholesalePrice wholesalePriceRequestToWholesalePrice(WholesalePriceRequest request) {
        WholesalePrice wholesalePrice = new WholesalePrice();
        wholesalePrice.setDateTime(LocalDateTime.now());
        return wholesalePrice;
    }

    public WholesalePriceUnit wholesalePriceUnitRequestToWholesalePriceUnit(WholesalePriceUnitRequest request, WholesalePrice wholesalePrice) {
        WholesalePriceUnit wholesalePriceUnit = new WholesalePriceUnit();
        wholesalePriceUnit.setMax(request.getMax());
        wholesalePriceUnit.setMin(request.getMin());
        wholesalePriceUnit.setPrice(request.getPrice());
        wholesalePriceUnit.setWholesalePrice(wholesalePrice);
        return wholesalePriceUnit;
    }

    public void validWholesaleUnit(WholesalePriceRequest request) {

        List<WholesalePriceUnitRequest> priceUnits = request.getPriceUnits();

        ListIterator<WholesalePriceUnitRequest> iterator = priceUnits.listIterator();

        while (iterator.hasNext()) {
            WholesalePriceUnitRequest p = iterator.next();
            if (iterator.hasNext())
                if (p.getMax() <= p.getMin())
                    throw new IllegalArgumentException("WholesalePrice has unexpected values");
        }

        for (int i = 1; i < priceUnits.size(); i++) {
            WholesalePriceUnitRequest previousPrice = priceUnits.get(i - 1);
            WholesalePriceUnitRequest currentPrice = priceUnits.get(i);
            if (currentPrice.getMin() <= previousPrice.getMax())
                throw new IllegalArgumentException("WholesalePrice has unexpected values");
        }
    }


}
