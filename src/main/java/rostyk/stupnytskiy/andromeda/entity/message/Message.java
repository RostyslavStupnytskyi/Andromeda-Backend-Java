package rostyk.stupnytskiy.andromeda.entity.message;

import lombok.*;
import rostyk.stupnytskiy.andromeda.entity.account.Account;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor

@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String text;

    private String image;

    private boolean isRead;

    private LocalDateTime sendTime;

    @ManyToOne
    private Chat chat;

    @ManyToOne
    private Account sender;

}
