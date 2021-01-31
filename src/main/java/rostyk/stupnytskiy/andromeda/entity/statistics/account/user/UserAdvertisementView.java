package rostyk.stupnytskiy.andromeda.entity.statistics.account.user;

import lombok.*;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.GoodsAdvertisement;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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
    private UserMonthStatistics monthStatistics;

    public UserAdvertisementView() {
        this.dateTime = LocalDateTime.now();
    }

}
