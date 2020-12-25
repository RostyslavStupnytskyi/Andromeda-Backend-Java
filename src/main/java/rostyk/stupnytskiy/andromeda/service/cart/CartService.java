package rostyk.stupnytskiy.andromeda.service.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.andromeda.entity.account.user_account.UserAccount;
import rostyk.stupnytskiy.andromeda.entity.cart.goods_cart_item.GoodsCartItem;
import rostyk.stupnytskiy.andromeda.repository.CartItemRepository;
import rostyk.stupnytskiy.andromeda.repository.CartRepository;
import rostyk.stupnytskiy.andromeda.repository.GoodsCartItemRepository;
import rostyk.stupnytskiy.andromeda.service.account.UserAccountService;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private GoodsCartItemService goodsCartItemService;

    @Autowired
    private UserAccountService userAccountService;


    public void addGoodsItemToCart(Long advertisementId){
        UserAccount user = userAccountService.findBySecurityContextHolder();
        goodsCartItemService.addGoodsItemToCart(user.getCart(),advertisementId);
    }

    public void deleteGoodsItemFromCart(Long cartItemId){
        UserAccount user = userAccountService.findBySecurityContextHolder();
        goodsCartItemService.deleteGoodsCartItem(cartItemId, user.getCart());
    }

    public void updateGoodsCartItemCount(Long cartItemId, Integer count){
        UserAccount user = userAccountService.findBySecurityContextHolder();
        goodsCartItemService.updateCartItemCount(cartItemId, user.getCart(),count);
    }
}
