package rostyk.stupnytskiy.andromeda.entity.message;

import lombok.*;
import rostyk.stupnytskiy.andromeda.entity.account.Account;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor

@Entity
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(mappedBy = "chats")
    private List<Account> members;

    @OneToMany(mappedBy = "chat")
    private List<Message> messages;

    public Chat(List<Account> members) {
        this.members = members;
    }
}
