package rostyk.stupnytskiy.andromeda.entity.notification;

import lombok.*;
import rostyk.stupnytskiy.andromeda.entity.account.Account;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Setter
@Getter
@AllArgsConstructor
@Builder

@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account receiver;

    @Lob
    private String text;

    private NotificationType type;

    private LocalDate date;

    private LocalTime time;

    private Boolean isRead;

    public Notification() {
//        dateTime = LocalDateTime.now();
        time = LocalTime.now();
        date = LocalDate.now();
        isRead = false;
    }
}
