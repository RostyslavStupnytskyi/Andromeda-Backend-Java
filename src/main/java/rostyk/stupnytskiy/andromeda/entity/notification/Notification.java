package rostyk.stupnytskiy.andromeda.entity.notification;

import lombok.*;
import rostyk.stupnytskiy.andromeda.entity.account.Account;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Account account;

    @Lob
    private String text;

    private NotificationType type;

    private LocalDateTime dateTime;
}
