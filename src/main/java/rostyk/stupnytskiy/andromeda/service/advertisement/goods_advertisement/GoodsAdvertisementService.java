package rostyk.stupnytskiy.andromeda.service.advertisement.goods_advertisement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.GoodsAdvertisement;
import rostyk.stupnytskiy.andromeda.repository.GoodsAdvertisementRepository;

@Service
public class GoodsAdvertisementService {

    @Autowired
    private GoodsAdvertisementRepository goodsAdvertisementRepository;

    public GoodsAdvertisement findById(Long id){
        return goodsAdvertisementRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }
}
