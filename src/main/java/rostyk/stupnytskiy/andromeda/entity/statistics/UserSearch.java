package rostyk.stupnytskiy.andromeda.entity.statistics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.andromeda.entity.account.user_account.UserAccount;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@AllArgsConstructor

@Entity
public class UserSearch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateTime;

    @ManyToOne
    private UserAccount user;

    private String value;


    public UserSearch() {
        this.dateTime = LocalDateTime.now();
    }
}
