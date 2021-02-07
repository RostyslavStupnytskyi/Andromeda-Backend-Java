package rostyk.stupnytskiy.andromeda.service.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.andromeda.dto.request.cart.GoodsCartItemForCountingPriceRequest;
import rostyk.stupnytskiy.andromeda.dto.response.cart.CartResponse;
import rostyk.stupnytskiy.andromeda.dto.response.cart.CartSellerPositionResponse;
import rostyk.stupnytskiy.andromeda.dto.response.cart.ChangeGoodsCartItemCountResponse;
import rostyk.stupnytskiy.andromeda.dto.response.cart.GoodsCartItemResponse;
import rostyk.stupnytskiy.andromeda.entity.account.seller_account.goods_seller.GoodsSellerAccount;
import rostyk.stupnytskiy.andromeda.entity.account.user_account.UserAccount;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.GoodsAdvertisement;
import rostyk.stupnytskiy.andromeda.entity.cart.Cart;
import rostyk.stupnytskiy.andromeda.entity.cart.goods_cart_item.GoodsCartItem;
import rostyk.stupnytskiy.andromeda.repository.cart.CartItemRepository;
import rostyk.stupnytskiy.andromeda.repository.cart.CartRepository;
import rostyk.stupnytskiy.andromeda.service.DeliveryTypeService;
import rostyk.stupnytskiy.andromeda.service.account.UserAccountService;
import rostyk.stupnytskiy.andromeda.service.advertisement.goods_advertisement.GoodsAdvertisementService;

import java.util.*;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private GoodsCartItemService goodsCartItemService;

    @Autowired
    private GoodsAdvertisementService goodsAdvertisementService;

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private DeliveryTypeService deliveryTypeService;

//    @Autowired
//    private GoodsSellerAccountService goodsSellerAccountService;


    public void addGoodsItemToCart(Long advertisementId, Long deliveryTypeId) {
        UserAccount user = userAccountService.findBySecurityContextHolder();
        goodsCartItemService.addGoodsItemToCart(user.getCart(), advertisementId, deliveryTypeId);
    }

    public void deleteGoodsItemFromCart(Long cartItemId) {
        UserAccount user = userAccountService.findBySecurityContextHolder();
        goodsCartItemService.deleteGoodsCartItem(cartItemId, user.getCart());
    }

    public ChangeGoodsCartItemCountResponse updateGoodsCartItemCount(Long cartItemId, Integer count) {
        UserAccount user = userAccountService.findBySecurityContextHolder();
        count = goodsCartItemService.updateCartItemCount(cartItemId, user.getCart(), count);
        return new ChangeGoodsCartItemCountResponse(count,
//                goodsCartItemService.findByIdAndCart(cartItemId, user.getCart()).getGoodsAdvertisement().getPriceForCart(count)
                1.0
        );
    }

    public CartResponse getCartResponse() {
        Cart cart = userAccountService.findBySecurityContextHolder().getCart();
        cart.getCartItems().forEach(goodsCartItemService::auditIfItemCountLessOrEqualThanGoodsCountAndChangeIfBigger);
        cart = userAccountService.findBySecurityContextHolder().getCart();
        return new CartResponse(cart);
    }

    public Double countCartPrice(List<GoodsCartItemForCountingPriceRequest> items) {
        double sum = 0.0;
        for (GoodsCartItemForCountingPriceRequest item : items) {
            GoodsAdvertisement advertisement = goodsAdvertisementService.findById(item.getAdvertisementId());
//            sum += advertisement.getPriceByCount(item.getCount());
        }
        return sum;
    }

    public List<CartSellerPositionResponse> getItemsForOrder(Long[] id) {
        Cart cart = userAccountService.findBySecurityContextHolder().getCart();
        List<CartSellerPositionResponse> positions = new ArrayList<>();
        List<GoodsCartItem> cartItems = new ArrayList<>();
        Arrays.stream(id).forEach(i -> cartItems.add(goodsCartItemService.findByIdAndCart(i, cart)));

        for (GoodsSellerAccount seller : getSellers(cartItems)) {
            List<GoodsCartItem> items = new ArrayList<>();
            for (GoodsCartItem cartItem : cartItems)
                if (cartItem.getGoodsAdvertisement().getSeller().getId().equals(seller.getId()))
                    items.add(cartItem);
            positions.add(new CartSellerPositionResponse(items, seller));
        }

        return positions;
    }

    private Set<GoodsSellerAccount> getSellers(List<GoodsCartItem> items) {
        Set<GoodsSellerAccount> sellers = new HashSet<>();
        items.forEach((i) -> sellers.add(i.getGoodsAdvertisement().getSeller()));
        return sellers;
    }

    public ChangeGoodsCartItemCountResponse checkGoodsCartItemCount(Long id, Integer count) {
        UserAccount user = userAccountService.findBySecurityContextHolder();
        count = goodsCartItemService.checkCartItemCount(id, user.getCart(), count);
        return new ChangeGoodsCartItemCountResponse(count,
//                goodsCartItemService.findByIdAndCart(id, user.getCart()).getGoodsAdvertisement().getPriceForCart(count)
                1.0
        );
    }

    public CartSellerPositionResponse formSellerPosition(Long advertisementId, Long deliveryId, Integer count) {
        GoodsAdvertisement advertisement = goodsAdvertisementService.findById(advertisementId);
        GoodsCartItemResponse item = new GoodsCartItemResponse(advertisement, deliveryTypeService.findById(deliveryId), (count != null ? count : 1));
        return new CartSellerPositionResponse(item, advertisement.getSeller());
    }
}
