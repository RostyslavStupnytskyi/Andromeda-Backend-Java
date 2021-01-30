package rostyk.stupnytskiy.andromeda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rostyk.stupnytskiy.andromeda.dto.request.PaginationRequest;
import rostyk.stupnytskiy.andromeda.dto.request.feedback.GoodsAdvertisementFeedbackRequest;
import rostyk.stupnytskiy.andromeda.dto.request.feedback.GoodsSellerFeedbackRequest;
import rostyk.stupnytskiy.andromeda.dto.response.PageResponse;
import rostyk.stupnytskiy.andromeda.dto.response.feedback.GoodsAdvertisementFeedbackResponse;
import rostyk.stupnytskiy.andromeda.service.feedback.GoodsAdvertisementFeedbackService;
import rostyk.stupnytskiy.andromeda.service.feedback.GoodsSellerFeedbackService;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/feedback")
public class FeedbackController {

    @Autowired
    private GoodsSellerFeedbackService goodsSellerFeedbackService;

    @Autowired
    private GoodsAdvertisementFeedbackService goodsAdvertisementFeedbackService;

    @GetMapping
    private PageResponse<GoodsAdvertisementFeedbackResponse> findPageByAdvertisement(Long id, PaginationRequest request) {
        return goodsAdvertisementFeedbackService.getGoodsAdvertisementFeedbacksPage(id, request);
    }

    @PostMapping("/goods-seller")
    private void leaveGoodsSellerFeedback(@Valid @RequestBody GoodsSellerFeedbackRequest request) {
        goodsSellerFeedbackService.save(request);
    }

    @PostMapping("/goods-advertisement")
    private void leaveGoodsAdvertisementFeedback(@Valid @RequestBody GoodsAdvertisementFeedbackRequest request) {
        goodsAdvertisementFeedbackService.save(request);
    }

}
