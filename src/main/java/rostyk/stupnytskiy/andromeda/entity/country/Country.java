package rostyk.stupnytskiy.andromeda.entity.country;

import lombok.*;
import rostyk.stupnytskiy.andromeda.entity.DeliveryType;
import rostyk.stupnytskiy.andromeda.entity.account.Seller;
import rostyk.stupnytskiy.andromeda.entity.statistics.AccountStatistics;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
/*
* Дана сутність існує для того щоби зберігати країну, її id в API та назву (на випадок якщо API накриється будемо використовувати англійські імена і старатимемось найти інше API)
* все сортування, пошук та порівняння регіонів, здійснювани на фронті, використовуючи потужність машини користувача
* */
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String countryCode; // один з парметрів збереження країн

    private Long apiId; // id з вибраного API

    private Region region; // для зручного пошуку країн по регіону

    @Column(name = "english_name")
    private String englishName; // назва країни англійською

    @OneToMany(mappedBy = "country")
    private List<AccountStatistics> accountStatistics;

    @ManyToMany(mappedBy = "countriesWithStorage")
    private Set<Seller> sellers;

    @OneToMany(mappedBy = "country")
    private List<DeliveryType> deliveryTypes;

}


