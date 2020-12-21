package rostyk.stupnytskiy.andromeda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.andromeda.entity.advertisement.Advertisement;
import rostyk.stupnytskiy.andromeda.entity.advertisement.RetailPrice;
import rostyk.stupnytskiy.andromeda.entity.statistics.AdvertisementStatistics;
import rostyk.stupnytskiy.andromeda.repository.AdvertisementStatisticsRepository;

import java.util.HashSet;

@Service
public class AdvertisementStatisticsService {

    @Autowired
    private AdvertisementStatisticsRepository advertisementStatisticsRepository;

    @Autowired
    private AdvertisementService advertisementService;

}
