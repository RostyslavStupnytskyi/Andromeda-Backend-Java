package rostyk.stupnytskiy.andromeda.service.feedback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.andromeda.dto.request.feedback.GoodsSellerFeedbackRequest;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.GoodsSellerAccount;
import rostyk.stupnytskiy.andromeda.entity.feedback.GoodsSellerFeedback;
import rostyk.stupnytskiy.andromeda.repository.feedback.GoodsSellerFeedbackRepository;
import rostyk.stupnytskiy.andromeda.service.account.UserAccountService;
import rostyk.stupnytskiy.andromeda.service.order.GoodsOrderService;
import rostyk.stupnytskiy.andromeda.service.statistics.account.goods_seller.GoodsSellerStatisticsService;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class GoodsSellerFeedbackService {

    @Autowired
    private GoodsSellerFeedbackRepository goodsSellerFeedbackRepository;

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private GoodsOrderService goodsOrderService;

    @Autowired
    private GoodsSellerStatisticsService goodsSellerStatisticsService;

    public void save(GoodsSellerFeedbackRequest request){
        GoodsSellerFeedback feedback = goodsSellerFeedbackRequestToGoodsSellerFeedback(request);
        goodsSellerFeedbackRepository.save(feedback);
        goodsSellerStatisticsService.incrementMonthStatisticsSellerFeedbacks(feedback.getSeller());
    }

    public List<GoodsSellerFeedback> findAllBySeller(GoodsSellerAccount seller) {
        return goodsSellerFeedbackRepository.findAllBySeller(seller);
    }

    public GoodsSellerFeedback goodsSellerFeedbackRequestToGoodsSellerFeedback(GoodsSellerFeedbackRequest request){
        GoodsSellerFeedback feedback = new GoodsSellerFeedback();
        feedback.setGoodsOrder(
                goodsOrderService.findOneByIdAndUser(
                        request.getOrderId(),userAccountService.findBySecurityContextHolder()
                ));
        feedback.setSeller(feedback.getGoodsOrder().getSeller());
        feedback.setUser(userAccountService.findBySecurityContextHolder());
        feedback.setCommunicationRating(request.getCommunicationRating());
        feedback.setServiceRating(request.getServiceRating());
        feedback.setDateTime(LocalDateTime.now());
        feedback.setText(request.getText());
        goodsOrderService.makeGoodsOrderClosed(feedback.getGoodsOrder());
        // TODO recount the average rating of seller statistics
        return feedback;
    }

}
