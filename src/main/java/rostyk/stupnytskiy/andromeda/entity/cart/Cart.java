package rostyk.stupnytskiy.andromeda.entity.cart;

import lombok.*;
import rostyk.stupnytskiy.andromeda.entity.account.user_account.UserAccount;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "cart")
    private UserAccount user;

    @OneToMany(mappedBy = "cart")
    private List<CartItem> cartItems;
}
