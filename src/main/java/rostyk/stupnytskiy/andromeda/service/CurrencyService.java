package rostyk.stupnytskiy.andromeda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import rostyk.stupnytskiy.andromeda.dto.request.country.CurrencyRequest;
import rostyk.stupnytskiy.andromeda.dto.response.country.CurrencyResponse;
import rostyk.stupnytskiy.andromeda.entity.country.Currency;
import rostyk.stupnytskiy.andromeda.repository.country.CurrencyRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CurrencyService {

    @Autowired
    private CurrencyRepository currencyRepository;

    private final RestTemplate restTemplate = new RestTemplateBuilder().build();

    public void reloadCurrencyValues() {
        String url = "https://api.privatbank.ua/p24api/pubinfo?json&exchange&coursid=5";
        String response = this.restTemplate.getForObject(url, String.class);
        System.out.println("Get = " + response);
        response = response.substring(2, response.length() - 2);
        System.out.println("Cut = " + response);
        String[] currencies = response.split("},\\{");
        String[][][] result = new String[4][4][2];
        for (int i = 0; i < currencies.length; i++) {
            String[] transit = currencies[i].split(",");
            for (int j = 0; j < transit.length; j++) {
                String[] transit2 = transit[j].split(":");
                for (int k = 0; k < transit2.length; k++) {
                    result[i][j][k] = transit2[k].substring(1, transit2[k].length() - 1);
                }
            }
        }
        Currency dollar = findCurrencyByCurrencyCode("USD");
        Currency euro = findCurrencyByCurrencyCode("EUR");
        Currency ruble = findCurrencyByCurrencyCode("RUB");

        dollar.setPriceInHryvnia(new Double(result[0][3][1]));
        euro.setPriceInHryvnia(new Double(result[1][3][1]));
        ruble.setPriceInHryvnia(new Double(result[2][3][1]));

        currencyRepository.save(dollar);
        currencyRepository.save(euro);
        currencyRepository.save(ruble);
//        System.out.println(Arrays.deepToString(result));
    }


    public void save(CurrencyRequest request) {
        currencyRepository.save(currencyRequestToCurrency(request, null));
    }


    public void update(CurrencyRequest request, Long id) {
        currencyRepository.save(currencyRequestToCurrency(request, findById(id)));
    }

    public Currency findById(Long id) {
        return currencyRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Currency with id " + " doesn't exist"));
    }

    public Currency currencyRequestToCurrency(CurrencyRequest request, Currency currency) {
        if (currency == null) currency = new Currency();
        currency.setName(request.getName());
        currency.setSymbol(request.getSymbol());
        currency.setCode(request.getCode());
        return currency;
    }


    public List<CurrencyResponse> geAll() {
        return currencyRepository.findAll()
                .stream()
                .map(CurrencyResponse::new)
                .collect(Collectors.toList());
    }

    public Currency findCurrencyByCurrencyCode(String code) {
        return currencyRepository.findOneByCode(code).orElseThrow(() -> new IllegalArgumentException("No currency found"));
    }
}
