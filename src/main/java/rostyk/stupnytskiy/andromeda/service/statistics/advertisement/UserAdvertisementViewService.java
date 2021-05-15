package rostyk.stupnytskiy.andromeda.service.statistics.advertisement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.GoodsAdvertisement;
import rostyk.stupnytskiy.andromeda.repository.statistics.UserAdvertisementViewRepository;

@Service
public class UserAdvertisementViewService {

    @Autowired
    private UserAdvertisementViewRepository userAdvertisementViewRepository;

    public int getViewCountByAdvertisementAndYearAndMonth(GoodsAdvertisement advertisement, int year, int month) {
        return userAdvertisementViewRepository.findCountOfViewsByAdvertisementAndMonthAndYear(advertisement, year, month);
    }
}
