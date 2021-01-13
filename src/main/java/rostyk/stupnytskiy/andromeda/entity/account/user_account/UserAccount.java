package rostyk.stupnytskiy.andromeda.entity.account.user_account;

import lombok.*;
import rostyk.stupnytskiy.andromeda.entity.UserDeliveryAddress;
import rostyk.stupnytskiy.andromeda.entity.cart.Cart;
import rostyk.stupnytskiy.andromeda.entity.account.Account;
import rostyk.stupnytskiy.andromeda.entity.account.UserRole;
import rostyk.stupnytskiy.andromeda.entity.order.GoodsOrder;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor

@Entity
public class UserAccount extends Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @OneToOne(cascade = CascadeType.ALL)
    private Cart cart;

    @OneToMany(mappedBy = "user")
    private List<GoodsOrder> goodsOrders;

    @OneToOne
    private UserDeliveryAddress defaultAddress;

    @OneToMany(mappedBy = "user")
    private List<UserDeliveryAddress> addresses;

    @OneToOne(cascade = CascadeType.ALL)
    private UserSettings settings;

    public UserAccount() {
        super.setUserRole(UserRole.ROLE_USER);
    }
}
