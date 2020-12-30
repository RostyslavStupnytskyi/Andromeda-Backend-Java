package rostyk.stupnytskiy.andromeda.service.feedback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.andromeda.dto.request.feedback.GoodsSellerFeedbackRequest;
import rostyk.stupnytskiy.andromeda.entity.feedback.GoodsSellerFeedback;
import rostyk.stupnytskiy.andromeda.repository.GoodsSellerFeedbackRepository;
import rostyk.stupnytskiy.andromeda.service.account.UserAccountService;
import rostyk.stupnytskiy.andromeda.service.order.GoodsOrderService;

@Service
public class GoodsSellerFeedbackService {

    @Autowired
    private GoodsSellerFeedbackRepository goodsSellerFeedbackRepository;

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private GoodsOrderService goodsOrderService;

    public void save(GoodsSellerFeedbackRequest request){
        goodsSellerFeedbackRepository.save(goodsSellerFeedbackRequestToGoodsSellerFeedback(request));
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
        // TODO recount the average rating of seller statistics
        return feedback;
    }

}
