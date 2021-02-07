package rostyk.stupnytskiy.andromeda.service.statistics.account.goods_seller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.GoodsSellerAccount;
import rostyk.stupnytskiy.andromeda.entity.statistics.account.goods_seller.GoodsSellerMonthStatistics;
import rostyk.stupnytskiy.andromeda.entity.statistics.account.goods_seller.GoodsSellerStatistics;
import rostyk.stupnytskiy.andromeda.entity.feedback.GoodsAdvertisementFeedback;
import rostyk.stupnytskiy.andromeda.entity.feedback.GoodsSellerFeedback;
import rostyk.stupnytskiy.andromeda.repository.statistics.account.GoodsSellerMonthStatisticsRepository;
import rostyk.stupnytskiy.andromeda.repository.statistics.account.GoodsSellerStatisticsRepository;
import rostyk.stupnytskiy.andromeda.service.feedback.GoodsAdvertisementFeedbackService;
import rostyk.stupnytskiy.andromeda.service.feedback.GoodsSellerFeedbackService;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

@Service
public class GoodsSellerStatisticsService {

    @Autowired
    private GoodsSellerStatisticsRepository goodsSellerStatisticsRepository;

    @Autowired
    private GoodsSellerFeedbackService goodsSellerFeedbackService;

    @Autowired
    private GoodsAdvertisementFeedbackService goodsAdvertisementFeedbackService;

    @Autowired
    private GoodsSellerMonthStatisticsRepository goodsSellerMonthStatisticsRepository;

    public void createStartStatistics(GoodsSellerAccount account) {
        GoodsSellerStatistics sellerStatistics = account.getStatistics();
        saveForNewMonthStatistics(sellerStatistics);
    }

    public GoodsSellerMonthStatistics saveForNewMonthStatistics(GoodsSellerStatistics sellerStatistics) {
        GoodsSellerMonthStatistics statistics = new GoodsSellerMonthStatistics();
        statistics.setSellerStatistics(sellerStatistics);
        return goodsSellerMonthStatisticsRepository.save(statistics);
    }



    public GoodsSellerMonthStatistics getBySellerAndMonthAndYear(GoodsSellerAccount seller, Month month, Integer year) {
        try {
            return goodsSellerMonthStatisticsRepository.findOneBySellerStatisticsSellerIdAndMonthAndYear(seller.getId(), month, year).orElseThrow(IllegalArgumentException::new);
        } catch (Exception e) {
            return saveForNewMonthStatistics(seller.getStatistics());
        }
    }

    public GoodsSellerMonthStatistics getMonthStatisticsForSellerByCurrentMonth(GoodsSellerAccount seller) {
        try {
            Month month = LocalDateTime.now().getMonth();
            Integer year = LocalDateTime.now().getYear();
            return goodsSellerMonthStatisticsRepository.findOneBySellerStatisticsSellerIdAndMonthAndYear(seller.getId(), month, year).orElseThrow(IllegalArgumentException::new);
        } catch (IllegalArgumentException e) {
            return saveForNewMonthStatistics(seller.getStatistics());
        }
    }

    public void incrementMonthStatisticsAdvertisementViews(GoodsSellerAccount seller) {
        GoodsSellerMonthStatistics statistics = getMonthStatisticsForSellerByCurrentMonth(seller);
        statistics.setAdvertisementViews(statistics.getAdvertisementViews() + 1);
        goodsSellerMonthStatisticsRepository.save(statistics);
    }

    public void incrementMonthStatisticsOrders(GoodsSellerAccount seller) {
        GoodsSellerMonthStatistics statistics = getMonthStatisticsForSellerByCurrentMonth(seller);
        statistics.setMonthOrders(statistics.getMonthOrders() + 1);
        goodsSellerMonthStatisticsRepository.save(statistics);
    }

    public void incrementMonthStatisticsProfileViews(GoodsSellerAccount seller) {
        GoodsSellerMonthStatistics statistics = getMonthStatisticsForSellerByCurrentMonth(seller);
        statistics.setProfileViews(statistics.getProfileViews() + 1);
        goodsSellerMonthStatisticsRepository.save(statistics);
    }

    public void incrementMonthStatisticsSellerFeedbacks(GoodsSellerAccount seller) {
        GoodsSellerMonthStatistics statistics = getMonthStatisticsForSellerByCurrentMonth(seller);
        statistics.setSellerFeedbacks(statistics.getSellerFeedbacks() + 1);
        goodsSellerMonthStatisticsRepository.save(statistics);
        recountMouthStatisticsRatings(seller);
    }

    public void incrementMonthStatisticsOrderFeedbacks(GoodsSellerAccount seller) {
        GoodsSellerMonthStatistics statistics = getMonthStatisticsForSellerByCurrentMonth(seller);
        statistics.setOrderFeedbacks(statistics.getOrderFeedbacks() + 1);
        goodsSellerMonthStatisticsRepository.save(statistics);
        recountMouthStatisticsRatings(seller);
    }

    public void recountMouthStatisticsRatings(GoodsSellerAccount seller) {
        GoodsSellerMonthStatistics statistics = getMonthStatisticsForSellerByCurrentMonth(seller);
        List<GoodsSellerFeedback> sellerFeedbacks = goodsSellerFeedbackService.findAllBySeller(seller);
        List<GoodsAdvertisementFeedback> advertisementFeedbacks = goodsAdvertisementFeedbackService.findAllGoodsAdvertisementFeedbacksByGoodsAdvertisementSeller(seller);

        statistics.setAverageCommunicationRating(
                Math.round(sellerFeedbacks
                        .stream()
                        .mapToDouble(GoodsSellerFeedback::getCommunicationRating).sum() / sellerFeedbacks.size() * 100.0) / 100.0
        );

        statistics.setAverageServiceRating(
                Math.round(sellerFeedbacks
                        .stream()
                        .mapToDouble(GoodsSellerFeedback::getServiceRating).sum() / sellerFeedbacks.size() * 100.0) / 100.0
        );

        statistics.setAverageOrderRating(
                Math.round(advertisementFeedbacks
                        .stream()
                        .mapToDouble(GoodsAdvertisementFeedback::getRating).sum() / advertisementFeedbacks.size() * 100.0) / 100.0
        );
        goodsSellerMonthStatisticsRepository.save(statistics);


    }

}
