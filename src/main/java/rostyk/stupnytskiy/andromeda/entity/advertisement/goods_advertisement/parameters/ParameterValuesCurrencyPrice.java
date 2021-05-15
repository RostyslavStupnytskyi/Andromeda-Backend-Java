package rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.parameters;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.entity.country.Currency;

import javax.persistence.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class ParameterValuesCurrencyPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Currency currency;

    private Double price;

    @ManyToOne
    private ParametersValuesPriceCount parametersValuesPriceCount;
}
