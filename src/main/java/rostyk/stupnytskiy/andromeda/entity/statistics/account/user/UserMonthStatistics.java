package rostyk.stupnytskiy.andromeda.entity.statistics.account.user;

import lombok.*;
import org.apache.tomcat.jni.Local;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor

@Entity
public class UserMonthStatistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Month month;

    private Integer year;

    private Long orders;

    private Long sellerFeedbacks;

    private Long orderFeedbacks;

    @OneToMany(mappedBy = "monthStatistics")
    private List<UserAdvertisementView> advertisementViews;

    @ManyToOne
    private UserStatistics userStatistics;

    public UserMonthStatistics() {
        this.month = LocalDateTime.now().getMonth();
        this.year = LocalDateTime.now().getYear();
        this.orders = 0L;
        this.sellerFeedbacks = 0L;
        this.orderFeedbacks = 0L;
    }
}
