package rostyk.stupnytskiy.andromeda.dto.response.country;

import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.entity.country.Currency;

@Getter
@Setter
public class CurrencyResponse {

    private String name;

    private String code;

    private String symbol;

    public CurrencyResponse(Currency currency) {
        this.name = currency.getName();
        this.code = currency.getCode();
        this.symbol = currency.getSymbol();
    }
}
