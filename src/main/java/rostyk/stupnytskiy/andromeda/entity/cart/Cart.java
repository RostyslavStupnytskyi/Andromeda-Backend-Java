package rostyk.stupnytskiy.andromeda.entity.cart;

import lombok.*;
import rostyk.stupnytskiy.andromeda.entity.account.user_account.UserAccount;
import rostyk.stupnytskiy.andromeda.entity.cart.goods_cart_item.GoodsCartItem;

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

    @OneToOne(mappedBy = "cart", fetch = FetchType.LAZY)
    private UserAccount user;

    @OneToMany(mappedBy = "cart", fetch = FetchType.LAZY)
    private List<GoodsCartItem> cartItems;
}
