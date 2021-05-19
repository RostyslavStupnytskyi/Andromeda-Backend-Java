package rostyk.stupnytskiy.andromeda.dto.response.order;

import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.dto.response.country.CurrencyResponse;
import rostyk.stupnytskiy.andromeda.entity.country.Currency;
import rostyk.stupnytskiy.andromeda.entity.order.GoodsOrder;
import rostyk.stupnytskiy.andromeda.entity.order.GoodsOrderPaymentDetails;
import rostyk.stupnytskiy.andromeda.entity.order.GoodsOrderPaymentMethod;

import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Getter
@Setter
public class GoodsOrderPaymentDetailsResponse {


    private CurrencyResponse currency;

    private GoodsOrderPaymentMethod payment;

    public GoodsOrderPaymentDetailsResponse(GoodsOrderPaymentDetails details) {
        this.currency = new CurrencyResponse(details.getCurrency());
        this.payment = details.getPaymentMethod();
    }
}
