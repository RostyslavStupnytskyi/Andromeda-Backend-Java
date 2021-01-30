package rostyk.stupnytskiy.andromeda.entity.statistics.account.user;

import lombok.*;
import rostyk.stupnytskiy.andromeda.entity.account.user_account.UserAccount;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor

@Entity
public class UserStatistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime registrationDate;

    @OneToMany(mappedBy = "userStatistics")
    private List<UserMonthStatistics> monthStatisticsList = new ArrayList<>();

    @OneToOne(mappedBy = "userStatistics")
    private UserAccount user;

    public UserStatistics() {
        this.registrationDate = LocalDateTime.now();
    }
}
