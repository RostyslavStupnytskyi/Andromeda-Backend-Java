package rostyk.stupnytskiy.andromeda.service.account.seller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.andromeda.dto.request.account.seller_account.SellerDataRequest;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.SellerAccount;
import rostyk.stupnytskiy.andromeda.repository.account.seller.SellerAccountRepository;
import rostyk.stupnytskiy.andromeda.service.account.AccountService;
import rostyk.stupnytskiy.andromeda.service.CountryService;
import rostyk.stupnytskiy.andromeda.service.DeliveryTypeService;

@Service
public class SellerService {

    @Autowired
    private SellerAccountRepository sellerRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private DeliveryTypeService deliveryTypeService;

    @Autowired
    private CountryService countryService;

    public void updateSellerData(SellerDataRequest request){
        SellerAccount sellerAccount = findBySecurityContextHolder();
        if (request.getShopName() != null) sellerAccount.setShopName(request.getShopName());
        if (request.getTaxpayerNumber() != null) sellerAccount.setTaxpayerNumber(request.getTaxpayerNumber());
        sellerRepository.save(sellerAccount);
    }

    public SellerAccount findBySecurityContextHolder(){
        return findById(accountService.getIdBySecurityContextHolder());
    }

    public SellerAccount findById(Long id){
        return sellerRepository.findById(id).orElseThrow(IllegalAccessError::new);
    }
}
