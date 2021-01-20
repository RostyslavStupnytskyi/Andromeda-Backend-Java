package rostyk.stupnytskiy.andromeda.service.feedback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.andromeda.dto.request.feedback.GoodsAdvertisementFeedbackRequest;
import rostyk.stupnytskiy.andromeda.entity.account.user_account.UserAccount;
import rostyk.stupnytskiy.andromeda.entity.feedback.GoodsAdvertisementFeedback;
import rostyk.stupnytskiy.andromeda.repository.GoodsAdvertisementFeedbackRepository;
import rostyk.stupnytskiy.andromeda.service.account.UserAccountService;
import rostyk.stupnytskiy.andromeda.service.advertisement.goods_advertisement.GoodsAdvertisementService;
import rostyk.stupnytskiy.andromeda.service.order.GoodsOrderItemService;
import rostyk.stupnytskiy.andromeda.tools.FileTool;

import java.io.IOException;

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

    @Autowired
    private FileTool fileTool;


    public void save(GoodsAdvertisementFeedbackRequest request){
        goodsAdvertisementFeedbackRepository.save(goodsAdvertisementFeedbackRequestToGoodsAdvertisementFeedback(request));
    }

    private GoodsAdvertisementFeedback goodsAdvertisementFeedbackRequestToGoodsAdvertisementFeedback(GoodsAdvertisementFeedbackRequest request) {
        GoodsAdvertisementFeedback feedback = new GoodsAdvertisementFeedback();
        UserAccount userAccount = userAccountService.findBySecurityContextHolder();
        feedback.setText(request.getText());
        feedback.setRating(request.getRating());
        feedback.setGoodsOrderItem(
                goodsOrderItemService.findByIdAndUser(request.getGoodsOrderItemId(), userAccount
                ));
        feedback.setGoodsAdvertisement(feedback.getGoodsOrderItem().getGoodsAdvertisement());
        feedback.setUserAccount(userAccount);
        feedback.setCountry(feedback.getGoodsOrderItem().getGoodsOrder().getDeliveryDetails().getCountry());

        if (request.getImages() != null) {
            request.getImages().forEach((i) -> {
                try {
                    feedback.getImages().add(fileTool.saveFeedbackImage(i, userAccount.getId()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
//        feedback.setImage(); TODO + make rating recount for advertisement statistics
        return feedback;
    }

}
