package rostyk.stupnytskiy.andromeda.entity.account;

import lombok.*;
import rostyk.stupnytskiy.andromeda.entity.Address;
import rostyk.stupnytskiy.andromeda.entity.Cart;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
    private Account account;

    @OneToOne(mappedBy = "user")
    private Cart cart;
}
