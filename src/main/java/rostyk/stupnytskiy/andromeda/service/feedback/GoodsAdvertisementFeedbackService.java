package rostyk.stupnytskiy.andromeda.service.feedback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.andromeda.dto.request.PaginationRequest;
import rostyk.stupnytskiy.andromeda.dto.request.feedback.GoodsAdvertisementFeedbackRequest;
import rostyk.stupnytskiy.andromeda.dto.response.PageResponse;
import rostyk.stupnytskiy.andromeda.dto.response.feedback.GoodsAdvertisementFeedbackResponse;
import rostyk.stupnytskiy.andromeda.entity.account.goods_seller.GoodsSellerAccount;
import rostyk.stupnytskiy.andromeda.entity.account.user_account.UserAccount;
import rostyk.stupnytskiy.andromeda.entity.feedback.GoodsAdvertisementFeedback;
import rostyk.stupnytskiy.andromeda.repository.feedback.GoodsAdvertisementFeedbackRepository;
import rostyk.stupnytskiy.andromeda.service.account.UserAccountService;
import rostyk.stupnytskiy.andromeda.service.advertisement.goods_advertisement.GoodsAdvertisementService;
import rostyk.stupnytskiy.andromeda.service.order.GoodsOrderItemService;
import rostyk.stupnytskiy.andromeda.tools.FileTool;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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


    public void save(GoodsAdvertisementFeedbackRequest request) {
        GoodsAdvertisementFeedback feedback = goodsAdvertisementFeedbackRequestToGoodsAdvertisementFeedback(request);
        goodsAdvertisementFeedbackRepository.save(feedback);
    }

    public PageResponse<GoodsAdvertisementFeedbackResponse> getGoodsAdvertisementFeedbacksPage(Long id, PaginationRequest request) {
        final Page<GoodsAdvertisementFeedback> page = goodsAdvertisementFeedbackRepository.findPageByGoodsAdvertisementId(id, request.mapToPageable());

        return new PageResponse<>(page
                .get()
                .map(GoodsAdvertisementFeedbackResponse::new)
                .collect(Collectors.toList()),
                page.getTotalElements(),
                page.getTotalPages());
    }

    public List<GoodsAdvertisementFeedback> findAllGoodsAdvertisementFeedbacksByGoodsAdvertisementId(Long id) {
        return goodsAdvertisementFeedbackRepository.findAllByGoodsAdvertisementId(id);
    }

    public List<GoodsAdvertisementFeedback> findAllGoodsAdvertisementFeedbacksByGoodsAdvertisementSeller(GoodsSellerAccount seller) {
        return goodsAdvertisementFeedbackRepository.findAllByGoodsAdvertisementSeller(seller);
    }


    private GoodsAdvertisementFeedback goodsAdvertisementFeedbackRequestToGoodsAdvertisementFeedback(GoodsAdvertisementFeedbackRequest request) {
        GoodsAdvertisementFeedback feedback = new GoodsAdvertisementFeedback();
        UserAccount userAccount = userAccountService.findBySecurityContextHolderOrReturnNull();
        feedback.setText(request.getText());
        feedback.setRating(request.getRating());
        feedback.setGoodsOrderItem(
                goodsOrderItemService.findByIdAndUser(request.getGoodsOrderItemId(), userAccount
                ));
        feedback.setGoodsAdvertisement(feedback.getGoodsOrderItem().getGoodsAdvertisement());
        feedback.setUserAccount(userAccount);
        feedback.setCreationDate(LocalDateTime.now());
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
