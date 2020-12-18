package rostyk.stupnytskiy.andromeda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.andromeda.dto.request.account.SellerDataRequest;
import rostyk.stupnytskiy.andromeda.dto.request.country.SellerCountriesRequest;
import rostyk.stupnytskiy.andromeda.dto.request.delivery.SellerDeliveryRequest;
import rostyk.stupnytskiy.andromeda.entity.account.Seller;
import rostyk.stupnytskiy.andromeda.entity.country.Country;
import rostyk.stupnytskiy.andromeda.entity.statistics.AccountStatistics;
import rostyk.stupnytskiy.andromeda.repository.SellerRepository;

import java.util.List;

@Service
public class SellerService {

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountStatisticsService accountStatisticsService;

    @Autowired
    private DeliveryTypeService deliveryTypeService;

    @Autowired
    private CountryService countryService;

    public void updateData(SellerDataRequest request){
        Seller seller = getSellerBySecurityContextHolder();
        if (request.getTaxpayerNumber() != null) seller.setTaxpayerNumber(request.getTaxpayerNumber());
        if(request.getShopName() != null) seller.setShopName(request.getShopName());
        if (request.getCountryApiId()!= null) {
            AccountStatistics statistics = seller.getAccount().getStatistics();
            statistics.setCountry(countryService.findCountryByApiId(request.getCountryApiId()));
            accountStatisticsService.save(statistics);
        } else if (request.getCountryCode() != null){
            AccountStatistics statistics = seller.getAccount().getStatistics();
            statistics.setCountry(countryService.findCountryByCountryCode(request.getCountryCode()));
            accountStatisticsService.save(statistics);
        }
        sellerRepository.save(seller);
    }

    public void updateSellerCountryCodes(SellerCountriesRequest request){
        Seller seller = getSellerBySecurityContextHolder();
        if (request.getCountriesId() != null){
            seller.setCountriesWithStorage(countryService.getCountriesSetByIds(request.getCountriesId()));
        } else if (request.getCountryCodes() != null){
            seller.setCountriesWithStorage(countryService.getCountriesSetByCodes(request.getCountryCodes()));
        }
        sellerRepository.save(seller);
    }

    public void updateSellerDeliveryCodes(SellerDeliveryRequest request) {
        Seller seller = getSellerBySecurityContextHolder();
        seller.setDeliveryTypes(deliveryTypeService.getDeliverySetByIds(request.getDeliveriesId()));
        sellerRepository.save(seller);
    }

    private Seller getSellerBySecurityContextHolder(){
        return accountService.getAccountBySecurityContextHolder().getSeller();
    }
}
