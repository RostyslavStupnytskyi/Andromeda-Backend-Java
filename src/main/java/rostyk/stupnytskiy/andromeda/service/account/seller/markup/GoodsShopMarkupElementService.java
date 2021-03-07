package rostyk.stupnytskiy.andromeda.service.account.seller.markup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.andromeda.dto.request.account.seller_account.goods_seller.markup.GoodsShopMarkupAdvertisementRowRequest;
import rostyk.stupnytskiy.andromeda.dto.request.account.seller_account.goods_seller.markup.GoodsShopMarkupAdvertisementViewRequest;
import rostyk.stupnytskiy.andromeda.dto.request.account.seller_account.goods_seller.markup.GoodsShopMarkupAdvertisingBannerRequest;
import rostyk.stupnytskiy.andromeda.dto.response.account.seller.goods_seller.markup.elements.GoodsShopMarkupAdvertisementRowResponse;
import rostyk.stupnytskiy.andromeda.dto.response.account.seller.goods_seller.markup.elements.GoodsShopMarkupAdvertisementViewResponse;
import rostyk.stupnytskiy.andromeda.dto.response.account.seller.goods_seller.markup.elements.GoodsShopMarkupAdvertisingBannerResponse;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.GoodsSellerAccount;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.markup.GoodsShopMarkup;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.markup.GoodsShopMarkupElement;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.markup.GoodsShopMarkupElementType;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.markup.GoodsShopMarkupLine;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.markup.elements.GoodsShopMarkupAdvertisingBanner;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.markup.elements.advertisement_row.GoodsShopMarkupAdvertisementRow;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.markup.elements.advertisement_row.GoodsShopMarkupAdvertisementRowType;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.markup.elements.advertisement_view.GoodsShopMarkupAdvertisementView;
import rostyk.stupnytskiy.andromeda.repository.account.seller.goods_seller.markup.GoodsShopMarkupElementRepository;
import rostyk.stupnytskiy.andromeda.repository.account.seller.goods_seller.markup.element.GoodsShopMarkupAdvertisementRowRepository;
import rostyk.stupnytskiy.andromeda.repository.account.seller.goods_seller.markup.element.GoodsShopMarkupAdvertisementViewRepository;
import rostyk.stupnytskiy.andromeda.repository.account.seller.goods_seller.markup.element.GoodsShopMarkupAdvertisingBannerRepository;
import rostyk.stupnytskiy.andromeda.service.account.seller.GoodsSellerAccountService;
import rostyk.stupnytskiy.andromeda.service.advertisement.goods_advertisement.GoodsAdvertisementService;
import rostyk.stupnytskiy.andromeda.tools.FileTool;

import java.io.IOException;
import java.util.stream.Collectors;

import static rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.markup.GoodsShopMarkupElementType.*;
import static rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.markup.elements.advertisement_row.GoodsShopMarkupAdvertisementRowType.*;

@Service
public class GoodsShopMarkupElementService {

    @Autowired
    private GoodsShopMarkupElementRepository goodsShopMarkupElementRepository;

    @Autowired
    private GoodsShopMarkupAdvertisingBannerRepository goodsShopMarkupAdvertisingBannerRepository;

    @Autowired
    private GoodsShopMarkupAdvertisementRowRepository goodsShopMarkupAdvertisementRowRepository;

    @Autowired
    private GoodsShopMarkupAdvertisementViewRepository goodsShopMarkupAdvertisementViewRepository;

    @Autowired
    private GoodsAdvertisementService goodsAdvertisementService;

    @Autowired
    private GoodsShopMarkupLineService goodsShopMarkupLineService;

    @Autowired
    private GoodsSellerAccountService goodsSellerAccountService;

    @Autowired
    private FileTool fileTool;

    public void processAdvertisingBannerRequest(GoodsShopMarkupAdvertisingBannerRequest request) {
        GoodsShopMarkupElement element = createElementByType(MARKUP_ADVERTISING_BANNER, request.getPosition(), request.getLine());
        GoodsShopMarkupAdvertisingBanner banner = bannerRequestToBanner(request);
        banner.setElement(element);
        saveAdvertisingBanner(banner);
    }

    public void processAdvertisementViewRequest(GoodsShopMarkupAdvertisementViewRequest request) {
        GoodsShopMarkupElement element = createElementByType(MARKUP_ADVERTISEMENT_VIEW, request.getPosition(), request.getLine());
        GoodsShopMarkupAdvertisementView view = advertisementViewRequestToAdvertisementView(request);
        view.setElement(element);
        saveAdvertisementView(view);
    }

    public void processAdvertisementRowRequest(GoodsShopMarkupAdvertisementRowRequest request) {
        GoodsShopMarkupElement element = createElementByType(MARKUP_ADVERTISEMENTS_ROW, request.getPosition(), request.getLine());
        GoodsShopMarkupAdvertisementRow row = advertisementRowRequestToAdvertisementRow(request);
        row.setElement(element);
        saveAdvertisementRow(row);
    }

    public GoodsShopMarkupAdvertisementViewResponse getViewResponseByElementId(Long id) {
        GoodsShopMarkupAdvertisementView view = getViewByElementId(id);
        GoodsSellerAccount goodsSeller = view.getElement().getLine().getMarkup().getGoodsSeller();
        switch (view.getViewType()) {
            case VIEW_CHOSEN_ADVERTISEMENT:
                return new GoodsShopMarkupAdvertisementViewResponse(view, goodsAdvertisementService.findById(view.getId()));
            case VIEW_POPULAR_ADVERTISEMENT:
                return new GoodsShopMarkupAdvertisementViewResponse(view, goodsAdvertisementService.getPopularGoodsAdvertisementsForGoodsSellerProfile(goodsSeller).get(0));
            case VIEW_MOST_ORDERED_ADVERTISEMENT:
                return new GoodsShopMarkupAdvertisementViewResponse(view, goodsAdvertisementService.getMostSoldGoodsAdvertisementsForGoodsSellerProfile(goodsSeller).get(0));
        }
        return null;
    }

    public GoodsShopMarkupAdvertisementRowResponse getRowResponseByElementId(Long id) {
        GoodsShopMarkupAdvertisementRow row = getRowByElementId(id);
        GoodsSellerAccount goodsSeller = row.getElement().getLine().getMarkup().getGoodsSeller();
        switch (row.getRowType()) {
            case ROW_NEW_ADVERTISEMENTS:
                return new GoodsShopMarkupAdvertisementRowResponse(row, goodsAdvertisementService.getNewGoodsAdvertisementsForGoodsSellerProfile(goodsSeller));
            case ROW_POPULAR_ADVERTISEMENTS:
                return new GoodsShopMarkupAdvertisementRowResponse(row, goodsAdvertisementService.getPopularGoodsAdvertisementsForGoodsSellerProfile(goodsSeller));
            case ROW_MOST_ORDERED_ADVERTISEMENTS:
                return new GoodsShopMarkupAdvertisementRowResponse(row, goodsAdvertisementService.getMostSoldGoodsAdvertisementsForGoodsSellerProfile(goodsSeller));
        }
        return null;
    }

    public void saveAdvertisingBanner(GoodsShopMarkupAdvertisingBanner advertisingBanner) {
        goodsShopMarkupAdvertisingBannerRepository.save(advertisingBanner);
    }

    public void saveAdvertisementView(GoodsShopMarkupAdvertisementView advertisementView) {
        goodsShopMarkupAdvertisementViewRepository.save(advertisementView);
    }

    public void saveAdvertisementRow(GoodsShopMarkupAdvertisementRow advertisementRow) {
        goodsShopMarkupAdvertisementRowRepository.save(advertisementRow);
    }

    public GoodsShopMarkupAdvertisingBanner getBannerByElementId(Long id) {
        return goodsShopMarkupAdvertisingBannerRepository.findOneByElementId(id).orElseThrow(IllegalArgumentException::new);
    }

    public GoodsShopMarkupAdvertisementRow getRowByElementId(Long id) {
        return goodsShopMarkupAdvertisementRowRepository.findOneByElementId(id).orElseThrow(IllegalArgumentException::new);
    }

    public GoodsShopMarkupAdvertisementView getViewByElementId(Long id) {
        return goodsShopMarkupAdvertisementViewRepository.findOneByElementId(id).orElseThrow(IllegalArgumentException::new);
    }

    public GoodsShopMarkupAdvertisingBanner bannerRequestToBanner(GoodsShopMarkupAdvertisingBannerRequest request) {
        GoodsShopMarkupAdvertisingBanner banner = new GoodsShopMarkupAdvertisingBanner();
        request.setImages(request.getImages().stream().map((i) -> {
            try {
                return fileTool.saveShopImage(i, goodsSellerAccountService.findBySecurityContextHolder().getId());
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }).collect(Collectors.toList()));

        banner.setImages(request.getImages());
        return banner;
    }

    public GoodsShopMarkupAdvertisementView advertisementViewRequestToAdvertisementView(GoodsShopMarkupAdvertisementViewRequest request) {
        GoodsShopMarkupAdvertisementView advertisementView = new GoodsShopMarkupAdvertisementView();

        advertisementView.setViewType(request.getViewType());
        advertisementView.setGoodsAdvertisement(goodsAdvertisementService.findById(request.getGoodsAdvertisementId()));

        return advertisementView;
    }

    public GoodsShopMarkupAdvertisementRow advertisementRowRequestToAdvertisementRow(GoodsShopMarkupAdvertisementRowRequest request) {
        GoodsShopMarkupAdvertisementRow advertisementRow = new GoodsShopMarkupAdvertisementRow();
        advertisementRow.setRowType(request.getRowType());
        return advertisementRow;
    }

    public void createDefaultElements(GoodsShopMarkup markup) {
        GoodsShopMarkupLine line = goodsShopMarkupLineService.getMarkupLineByMarkupAndLine(markup, 1);
        saveDefaultMarkupElement(line, ROW_POPULAR_ADVERTISEMENTS);
        line = goodsShopMarkupLineService.getMarkupLineByMarkupAndLine(markup, 2);
        saveDefaultMarkupElement(line, ROW_MOST_ORDERED_ADVERTISEMENTS);
        line = goodsShopMarkupLineService.getMarkupLineByMarkupAndLine(markup, 3);
        saveDefaultMarkupElement(line, ROW_NEW_ADVERTISEMENTS);
    }

    private void saveDefaultMarkupElement(GoodsShopMarkupLine line, GoodsShopMarkupAdvertisementRowType rowType) {
        GoodsShopMarkupElement element = new GoodsShopMarkupElement(MARKUP_ADVERTISEMENTS_ROW, 1);
        element.setLine(line);
        element = goodsShopMarkupElementRepository.save(element);

        GoodsShopMarkupAdvertisementRow advertisementRow = new GoodsShopMarkupAdvertisementRow();
        advertisementRow.setElement(element);
        advertisementRow.setRowType(rowType);

        saveAdvertisementRow(advertisementRow);
    }

    private GoodsShopMarkupElement createElementByType(GoodsShopMarkupElementType elementType, Integer startPosition, Integer lineOrder) {
        GoodsShopMarkup markup = goodsSellerAccountService.findBySecurityContextHolder().getMarkup();
        GoodsShopMarkupElement element = new GoodsShopMarkupElement(elementType, startPosition);
        GoodsShopMarkupLine line = goodsShopMarkupLineService.getMarkupLineByMarkupAndLine(markup, lineOrder);
        element.setLine(line);
        return goodsShopMarkupElementRepository.save(element);
    }


}
