package rostyk.stupnytskiy.andromeda.entity.statistics;

import lombok.*;
import rostyk.stupnytskiy.andromeda.entity.account.user_account.UserAccount;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.GoodsAdvertisement;

import javax.persistence.*;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.Date;

@Setter
@Getter
@Builder
@AllArgsConstructor

@Entity
public class UserAdvertisementView {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private GoodsAdvertisement goodsAdvertisement;

    private LocalDateTime dateTime;

    @ManyToOne
    private UserAccount user;


    public UserAdvertisementView() {
        this.dateTime = LocalDateTime.now();
    }

}
