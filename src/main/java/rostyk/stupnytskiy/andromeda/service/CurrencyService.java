package rostyk.stupnytskiy.andromeda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.andromeda.dto.request.country.CurrencyRequest;
import rostyk.stupnytskiy.andromeda.entity.country.Currency;
import rostyk.stupnytskiy.andromeda.repository.CurrencyRepository;

@Service
public class CurrencyService {

    @Autowired
    private CurrencyRepository currencyRepository;

    public void save(CurrencyRequest request) {
        currencyRepository.save(currencyRequestToCurrency(request,null));
    }


    public void update(CurrencyRequest request, Long id){
        currencyRepository.save(currencyRequestToCurrency(request,findById(id)));
    }

    public Currency findById(Long id) {
        return currencyRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Currency with id " + " doesn't exist"));
    }

    public Currency currencyRequestToCurrency(CurrencyRequest request, Currency currency){
        if (currency == null) currency = new Currency();
        currency.setName(request.getName());
        currency.setSymbol(request.getSymbol());
        currency.setCode(request.getCode());
        return  currency;
    }


}
