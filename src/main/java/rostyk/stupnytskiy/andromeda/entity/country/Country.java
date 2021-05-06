package rostyk.stupnytskiy.andromeda.entity.country;

import lombok.*;

import javax.persistence.*;

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
    @Column(unique = true)
    private String countryCode; // один з парметрів збереження країн

    @Column(unique = true)
    private Long apiId; // id з вибраного API

    private Region region; // для зручного пошуку країн по регіону

    @Column(name = "english_name",unique = true)
    private String englishName; // назва країни англійською

}


