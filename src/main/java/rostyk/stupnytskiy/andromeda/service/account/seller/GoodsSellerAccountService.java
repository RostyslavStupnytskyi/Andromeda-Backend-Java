package rostyk.stupnytskiy.andromeda.service.account.seller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.andromeda.dto.request.account.ImageRequest;
import rostyk.stupnytskiy.andromeda.dto.request.account.goods_seller.GoodsSellerDataRequest;
import rostyk.stupnytskiy.andromeda.dto.request.account.goods_seller.GoodsSellerMainDaraRequest;
import rostyk.stupnytskiy.andromeda.dto.response.account.goods_seller.GoodsSellerDataResponse;
import rostyk.stupnytskiy.andromeda.entity.account.goods_seller.GoodsSellerAccount;
import rostyk.stupnytskiy.andromeda.repository.account.goods_seller.GoodsSellerAccountRepository;
import rostyk.stupnytskiy.andromeda.service.account.AccountService;
import rostyk.stupnytskiy.andromeda.service.CountryService;
import rostyk.stupnytskiy.andromeda.service.DeliveryTypeService;
import rostyk.stupnytskiy.andromeda.tools.FileTool;

import java.io.IOException;

@Service
public class GoodsSellerAccountService {

    @Autowired
    private GoodsSellerAccountRepository goodsSellerRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private CountryService countryService;

    @Autowired
    private DeliveryTypeService deliveryTypeService;

    @Autowired
    private FileTool fileTool;

    // save the main goods seller data
    public void updateGoodsSellerData(GoodsSellerDataRequest request) {
        GoodsSellerAccount goodsSellerAccount = findBySecurityContextHolder();

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
                goodsSellerAccount.setAvatar(fileTool.saveUserImage(request.getAvatar(), goodsSellerAccount.getId()));
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


    public GoodsSellerDataResponse getGoodsSellerData() {
        GoodsSellerAccount seller = findBySecurityContextHolder();
        return new GoodsSellerDataResponse(seller);
    }

    public void changeGoodsSellerBanner(ImageRequest request) {
        GoodsSellerAccount account = findBySecurityContextHolder();
        if (request.getImage() != null) {
            try {
                account.getSettings().setBannerImage(fileTool.saveUserImage(request.getImage(), account.getId()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            account.getSettings().setBannerImage(null);
        }
        goodsSellerRepository.save(account);
    }

    public void updateGoodsSellerMainData(GoodsSellerMainDaraRequest request) {
        GoodsSellerAccount account = findBySecurityContextHolder();
        if (request.getDescription().trim().isEmpty()) {
            account.setDescription(null);
        } else {
            account.setDescription(request.getDescription().trim());
        }
        account.setShopName(request.getShopName());
        goodsSellerRepository.save(account);
    }
}
