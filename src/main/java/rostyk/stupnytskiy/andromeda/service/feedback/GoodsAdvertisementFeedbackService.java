package rostyk.stupnytskiy.andromeda.service.feedback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.andromeda.dto.request.feedback.GoodsAdvertisementFeedbackRequest;
import rostyk.stupnytskiy.andromeda.entity.feedback.GoodsAdvertisementFeedback;
import rostyk.stupnytskiy.andromeda.repository.GoodsAdvertisementFeedbackRepository;
import rostyk.stupnytskiy.andromeda.service.account.UserAccountService;
import rostyk.stupnytskiy.andromeda.service.advertisement.goods_advertisement.GoodsAdvertisementService;
import rostyk.stupnytskiy.andromeda.service.order.GoodsOrderItemService;

@Service
public class GoodsAdvertisementFeedbackService {

    @Autowired
    private GoodsAdvertisementFeedbackRepository goodsAdvertisementFeedbackRepository;

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private GoodsOrderItemService goodsOrderItemService;

    @Autowired
    private GoodsAdvertisementService goodsAdvertisementService;


    public void save(GoodsAdvertisementFeedbackRequest request){
        goodsAdvertisementFeedbackRepository.save(goodsAdvertisementFeedbackRequestToGoodsAdvertisementFeedback(request));
    }

    private GoodsAdvertisementFeedback goodsAdvertisementFeedbackRequestToGoodsAdvertisementFeedback(GoodsAdvertisementFeedbackRequest request) {
        GoodsAdvertisementFeedback feedback = new GoodsAdvertisementFeedback();
        feedback.setGoodsAdvertisement(goodsAdvertisementService.findById(request.getGoodsAdvertisementId()));
        feedback.setText(request.getText());
        feedback.setRating(request.getRating());
        feedback.setGoodsOrderItem(
                goodsOrderItemService.findByIdAndUser(request.getGoodsOrderItemId(), userAccountService.findBySecurityContextHolder()
                ));
        feedback.setCountry(feedback.getGoodsOrderItem().getGoodsOrder().getDeliveryDetails().getCountry());
//        feedback.setImage(); TODO + make rating recount for advertisement statistics
        return feedback;
    }

}
