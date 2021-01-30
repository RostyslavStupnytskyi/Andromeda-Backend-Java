package rostyk.stupnytskiy.andromeda.service.statistics.advertisement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.andromeda.entity.statistics.advertisement.GoodsAdvertisementStatistics;
import rostyk.stupnytskiy.andromeda.entity.feedback.GoodsAdvertisementFeedback;
import rostyk.stupnytskiy.andromeda.repository.GoodsAdvertisementStatisticsRepository;
import rostyk.stupnytskiy.andromeda.service.feedback.GoodsAdvertisementFeedbackService;

import java.util.List;

@Service
public class GoodsAdvertisementStatisticsService {

    @Autowired
    private GoodsAdvertisementStatisticsRepository goodsAdvertisementStatisticsRepository;

    @Autowired
    private GoodsAdvertisementFeedbackService goodsAdvertisementFeedbackService;

    public GoodsAdvertisementStatistics findOneByGoodsAdvertisementId(Long id) {
        return goodsAdvertisementStatisticsRepository.findOneByGoodsAdvertisementId(id).orElseThrow(IllegalAccessError::new);
    }

    public void incrementGoodsAdvertisementSoldNumber(Long goodsAdvertisementId, Integer number) {
        GoodsAdvertisementStatistics statistics = findOneByGoodsAdvertisementId(goodsAdvertisementId);
        statistics.setSold(statistics.getSold() + number);
        goodsAdvertisementStatisticsRepository.save(statistics);
    }

    public void incrementGoodsAdvertisementOrdersNumber(Long goodsAdvertisementId) {
        GoodsAdvertisementStatistics statistics = findOneByGoodsAdvertisementId(goodsAdvertisementId);
        statistics.setOrders(statistics.getOrders() + 1);
        goodsAdvertisementStatisticsRepository.save(statistics);
    }

    public void incrementGoodsAdvertisementFeedbacksNumber(Long goodsAdvertisementId) {
        GoodsAdvertisementStatistics statistics = findOneByGoodsAdvertisementId(goodsAdvertisementId);
        statistics.setFeedbacks(statistics.getFeedbacks() + 1);
        recountGoodsAdvertisementRating(goodsAdvertisementStatisticsRepository.save(statistics).getGoodsAdvertisement().getId());
    }

    public void incrementGoodsAdvertisementViews(Long goodsAdvertisementId) {
        GoodsAdvertisementStatistics statistics = findOneByGoodsAdvertisementId(goodsAdvertisementId);
        statistics.setViews(statistics.getViews() + 1);
        goodsAdvertisementStatisticsRepository.save(statistics);
    }

    public void incrementGoodsAdvertisementInLikeLists(Long goodsAdvertisementId) {
        GoodsAdvertisementStatistics statistics = findOneByGoodsAdvertisementId(goodsAdvertisementId);
        statistics.setInLikesList(statistics.getInLikesList() + 1);
        goodsAdvertisementStatisticsRepository.save(statistics);
    }

    public void recountGoodsAdvertisementRating(Long goodsAdvertisementId) {
        GoodsAdvertisementStatistics statistics = findOneByGoodsAdvertisementId(goodsAdvertisementId);
        List<GoodsAdvertisementFeedback> feedbacks = goodsAdvertisementFeedbackService.findAllGoodsAdvertisementFeedbacksByGoodsAdvertisementId(goodsAdvertisementId);

        statistics.setRating( Math.round(feedbacks.stream().mapToDouble( GoodsAdvertisementFeedback::getRating).sum()/feedbacks.size() * 100.0) / 100.0 );
        goodsAdvertisementStatisticsRepository.save(statistics);
    }
}
