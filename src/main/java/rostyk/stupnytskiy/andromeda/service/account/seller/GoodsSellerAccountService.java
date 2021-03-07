package rostyk.stupnytskiy.andromeda.service.account.seller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.andromeda.dto.request.account.seller_account.goods_seller.GoodsSellerDataRequest;
import rostyk.stupnytskiy.andromeda.dto.response.account.seller.goods_seller.GoodsSellerDataResponse;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.GoodsSellerAccount;
import rostyk.stupnytskiy.andromeda.entity.statistics.account.goods_seller.GoodsSellerMonthStatistics;
import rostyk.stupnytskiy.andromeda.entity.statistics.account.goods_seller.GoodsSellerStatistics;
import rostyk.stupnytskiy.andromeda.repository.account.seller.goods_seller.GoodsSellerAccountRepository;
import rostyk.stupnytskiy.andromeda.service.account.AccountService;
import rostyk.stupnytskiy.andromeda.service.CountryService;
import rostyk.stupnytskiy.andromeda.service.DeliveryTypeService;
import rostyk.stupnytskiy.andromeda.service.account.seller.markup.GoodsShopMarkupService;
import rostyk.stupnytskiy.andromeda.service.statistics.account.goods_seller.GoodsSellerStatisticsService;
import rostyk.stupnytskiy.andromeda.tools.FileTool;

import java.io.IOException;
import java.time.Month;

@Service
public class GoodsSellerAccountService {

    @Autowired
    private GoodsSellerAccountRepository goodsSellerRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private GoodsSellerStatisticsService goodsSellerStatisticsService;

    @Autowired
    private CountryService countryService;

    @Autowired
    private DeliveryTypeService deliveryTypeService;

    @Autowired
    private GoodsShopMarkupService goodsShopMarkupService;

    @Autowired
    private FileTool fileTool;

    // save the main goods seller data
    public void updateGoodsSellerData(GoodsSellerDataRequest request) {
        GoodsSellerAccount goodsSellerAccount = findBySecurityContextHolder();

        if (request.getOnlySellerCountryDelivery() != null)
            goodsSellerAccount.setOnlySellerCountryDelivery(request.getOnlySellerCountryDelivery());

        if (request.getDeliveryTypesId() != null)
            goodsSellerAccount.setDeliveryTypes(deliveryTypeService.getDeliverySetByIds(request.getDeliveryTypesId()));

        if (request.getShopName() != null)
            goodsSellerAccount.setShopName(request.getShopName());

        if (request.getSendNewOrderNotifications() != null)
            goodsSellerAccount.getSettings().setSendNewOrderNotifications(request.getSendNewOrderNotifications());

        if (request.getSendOrderReceivedNotifications() != null)
            goodsSellerAccount.getSettings().setSendOrderReceivedNotifications(request.getSendOrderReceivedNotifications());

        if (request.getAvatar() != null) {
            try {
                goodsSellerAccount.setAvatar(fileTool.saveUserAvatarImage(request.getAvatar(), goodsSellerAccount.getId()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        goodsSellerRepository.save(goodsSellerAccount);
    }


    public GoodsSellerAccount findBySecurityContextHolder() {
        return findById(accountService.getIdBySecurityContextHolder());
    }

    public GoodsSellerAccount findById(Long id) {
        return goodsSellerRepository.findById(id).orElseThrow(IllegalAccessError::new);
    }

    public void addStatisticsForEach() {
        for (GoodsSellerAccount sellerAccount : goodsSellerRepository.findAll()) {
            if (sellerAccount.getStatistics() == null) {
                sellerAccount.setStatistics(new GoodsSellerStatistics());
                goodsSellerStatisticsService.createStartStatistics(goodsSellerRepository.save(sellerAccount));
            }
        }
    }

    public GoodsSellerDataResponse getGoodsSellerData() {
        GoodsSellerAccount seller = findBySecurityContextHolder();
        return new GoodsSellerDataResponse(seller);
    }

    public GoodsSellerMonthStatistics findMonthStatisticsByIdAndMonthAndYear(Integer month, Integer year) {
        GoodsSellerAccount sellerAccount = findBySecurityContextHolder();
        Month m = Month.of(month + 1);
        return goodsSellerStatisticsService.getBySellerAndMonthAndYear(sellerAccount, m, year);
    }

    public void createMarkupForAll() {
        goodsSellerRepository.findAll().forEach((s) -> {
            if (s.getMarkup() == null) goodsShopMarkupService.createDefaultMarkup(s);
        });
    }
}
