package rostyk.stupnytskiy.andromeda.service.statistics.advertisement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.GoodsAdvertisement;
import rostyk.stupnytskiy.andromeda.entity.statistics.advertisement.GoodsAdvertisementMonthStatistics;
import rostyk.stupnytskiy.andromeda.entity.statistics.advertisement.GoodsAdvertisementStatistics;
import rostyk.stupnytskiy.andromeda.entity.feedback.GoodsAdvertisementFeedback;
import rostyk.stupnytskiy.andromeda.repository.statistics.advertisement.GoodsAdvertisementMonthStatisticsRepository;
import rostyk.stupnytskiy.andromeda.repository.statistics.advertisement.GoodsAdvertisementStatisticsRepository;
import rostyk.stupnytskiy.andromeda.service.feedback.GoodsAdvertisementFeedbackService;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

@Service
public class GoodsAdvertisementStatisticsService {

    @Autowired
    private GoodsAdvertisementStatisticsRepository goodsAdvertisementStatisticsRepository;

    @Autowired
    private GoodsAdvertisementFeedbackService goodsAdvertisementFeedbackService;

    @Autowired
    private GoodsAdvertisementMonthStatisticsRepository goodsAdvertisementMonthStatisticsRepository;

    public GoodsAdvertisementStatistics findOneByGoodsAdvertisementId(Long id) {
        return goodsAdvertisementStatisticsRepository.findOneByGoodsAdvertisementId(id).orElseThrow(IllegalAccessError::new);
    }

    public void createStartStatistics(GoodsAdvertisement goodsAdvertisement) {
        GoodsAdvertisementStatistics statistics = goodsAdvertisement.getStatistics();
        saveForNewMonthStatistics(statistics);
    }

    public GoodsAdvertisementMonthStatistics saveForNewMonthStatistics(GoodsAdvertisementStatistics advertisementStatistics) {
        GoodsAdvertisementMonthStatistics statistics = new GoodsAdvertisementMonthStatistics();
        statistics.setStatistics(advertisementStatistics);
        return goodsAdvertisementMonthStatisticsRepository.save(statistics);
    }

    public GoodsAdvertisementMonthStatistics getMonthStatisticsForAdvertisementByCurrentMonth(GoodsAdvertisementStatistics statistics) {
        try {
            Month month = LocalDateTime.now().getMonth();
            Integer year = LocalDateTime.now().getYear();
            return getByGoodsAdvertisementAndMonthAndYear(statistics, month, year);
        } catch (IllegalArgumentException e) {
            return saveForNewMonthStatistics(statistics);
        }
    }

    public GoodsAdvertisementMonthStatistics getMonthStatisticsByGoodsAdvertisementStatisticsAndMonthAndYear(GoodsAdvertisementStatistics statistics, Month month, Integer year) {
        try {
            return getByGoodsAdvertisementAndMonthAndYear(statistics, month, year);
        } catch (IllegalArgumentException e) {
            return saveForNewMonthStatistics(statistics);
        }
    }

    public GoodsAdvertisementMonthStatistics getByGoodsAdvertisementAndMonthAndYear(GoodsAdvertisementStatistics statistics, Month month, Integer year) {
        return goodsAdvertisementMonthStatisticsRepository.findOneByStatisticsAndMonthAndYear(statistics, month, year).orElseThrow(IllegalArgumentException::new);
    }

    public void incrementGoodsAdvertisementSoldNumber(Long goodsAdvertisementId, Integer number) {
        GoodsAdvertisementMonthStatistics statistics = getMonthStatisticsForAdvertisementByCurrentMonth(findOneByGoodsAdvertisementId(goodsAdvertisementId));
        statistics.setSold(statistics.getSold() + number);
        goodsAdvertisementMonthStatisticsRepository.save(statistics);
    }

    public void incrementGoodsAdvertisementOrdersNumber(Long goodsAdvertisementId) {
        GoodsAdvertisementMonthStatistics statistics = getMonthStatisticsForAdvertisementByCurrentMonth(findOneByGoodsAdvertisementId(goodsAdvertisementId));
        statistics.setOrders(statistics.getOrders() + 1);
        goodsAdvertisementMonthStatisticsRepository.save(statistics);
    }

    public void incrementGoodsAdvertisementFeedbacksNumber(Long goodsAdvertisementId) {
        GoodsAdvertisementMonthStatistics statistics = getMonthStatisticsForAdvertisementByCurrentMonth(findOneByGoodsAdvertisementId(goodsAdvertisementId));
        statistics.setFeedbacks(statistics.getFeedbacks() + 1);
        recountGoodsAdvertisementRating(goodsAdvertisementMonthStatisticsRepository.save(statistics).getStatistics().getGoodsAdvertisement().getId());
    }

    public void incrementGoodsAdvertisementViews(Long goodsAdvertisementId) {
        GoodsAdvertisementMonthStatistics statistics = getMonthStatisticsForAdvertisementByCurrentMonth(findOneByGoodsAdvertisementId(goodsAdvertisementId));
        statistics.setViews(statistics.getViews() + 1);
        goodsAdvertisementMonthStatisticsRepository.save(statistics);
    }

    public void incrementGoodsAdvertisementInLikeLists(Long goodsAdvertisementId) {
        GoodsAdvertisementMonthStatistics statistics = getMonthStatisticsForAdvertisementByCurrentMonth(findOneByGoodsAdvertisementId(goodsAdvertisementId));
        statistics.setInLikeList(statistics.getInLikeList() + 1);
        goodsAdvertisementMonthStatisticsRepository.save(statistics);
    }

    public void decrementGoodsAdvertisementInLikeLists(Long goodsAdvertisementId) {
        GoodsAdvertisementMonthStatistics statistics = getMonthStatisticsForAdvertisementByCurrentMonth(findOneByGoodsAdvertisementId(goodsAdvertisementId));
        statistics.setInLikeList(statistics.getInLikeList() - 1);
        goodsAdvertisementMonthStatisticsRepository.save(statistics);
    }

    public void recountGoodsAdvertisementRating(Long goodsAdvertisementId) {
        GoodsAdvertisementStatistics statistics = findOneByGoodsAdvertisementId(goodsAdvertisementId);
        List<GoodsAdvertisementFeedback> feedbacks = goodsAdvertisementFeedbackService.findAllGoodsAdvertisementFeedbacksByGoodsAdvertisementId(goodsAdvertisementId);

        statistics.setRating( Math.round(feedbacks.stream().mapToDouble( GoodsAdvertisementFeedback::getRating).sum()/feedbacks.size() * 100.0) / 100.0 );
        goodsAdvertisementStatisticsRepository.save(statistics);
    }



}
