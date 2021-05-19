package rostyk.stupnytskiy.andromeda.service.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.andromeda.dto.request.cart.GoodsCartItemForCountingPriceRequest;
import rostyk.stupnytskiy.andromeda.dto.response.cart.CartResponse;
import rostyk.stupnytskiy.andromeda.dto.response.cart.CartSellerPositionResponse;
import rostyk.stupnytskiy.andromeda.dto.response.cart.ChangeGoodsCartItemCountResponse;
import rostyk.stupnytskiy.andromeda.dto.response.cart.GoodsCartItemResponse;
import rostyk.stupnytskiy.andromeda.dto.response.country.CurrencyResponse;
import rostyk.stupnytskiy.andromeda.entity.account.goods_seller.GoodsSellerAccount;
import rostyk.stupnytskiy.andromeda.entity.account.user_account.UserAccount;
import rostyk.stupnytskiy.andromeda.entity.advertisement.goods_advertisement.GoodsAdvertisement;
import rostyk.stupnytskiy.andromeda.entity.cart.Cart;
import rostyk.stupnytskiy.andromeda.entity.cart.goods_cart_item.GoodsCartItem;
import rostyk.stupnytskiy.andromeda.repository.cart.CartItemRepository;
import rostyk.stupnytskiy.andromeda.repository.cart.CartRepository;
import rostyk.stupnytskiy.andromeda.service.CurrencyService;
import rostyk.stupnytskiy.andromeda.service.DeliveryTypeService;
import rostyk.stupnytskiy.andromeda.service.account.UserAccountService;
import rostyk.stupnytskiy.andromeda.service.advertisement.goods_advertisement.GoodsAdvertisementService;

import java.util.*;
import java.util.stream.Collectors;

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

    @Autowired
    private CurrencyService currencyService;

    public void addGoodsItemToCart(Long advertisementId, Long paramsValuesId) {
        UserAccount user = userAccountService.findBySecurityContextHolderOrReturnNull();
        if (advertisementId != null && paramsValuesId != null)
            goodsCartItemService.addGoodsItemToCart(user.getCart(), advertisementId, paramsValuesId);
    }

    public void deleteGoodsItemFromCart(Long cartItemId) {
        UserAccount user = userAccountService.findBySecurityContextHolderOrReturnNull();
        goodsCartItemService.deleteGoodsCartItem(cartItemId, user.getCart());
    }

    public ChangeGoodsCartItemCountResponse updateGoodsCartItemCount(Long cartItemId, Integer count) {
        UserAccount user = userAccountService.findBySecurityContextHolderOrReturnNull();
        count = goodsCartItemService.updateCartItemCount(cartItemId, user.getCart(), count);
        return new ChangeGoodsCartItemCountResponse(count);
    }

    public CartResponse getCartResponse() {
        Cart cart = userAccountService.findBySecurityContextHolderOrReturnNull().getCart();
        return new CartResponse(cart, currencyService.findCurrencyByCurrencyCode("USD"));
    }

    public List<CartSellerPositionResponse> getItemsForOrder(Long[] id) {
        Cart cart = userAccountService.findBySecurityContextHolderOrReturnNull().getCart();
        List<CartSellerPositionResponse> positions = new ArrayList<>();
        List<GoodsCartItem> cartItems = new ArrayList<>();
        Arrays.stream(id).forEach(i -> cartItems.add(goodsCartItemService.findByIdAndCart(i, cart)));

        for (GoodsSellerAccount seller : getSellers(cartItems)) {
            List<GoodsCartItem> items = new ArrayList<>();
            for (GoodsCartItem cartItem : cartItems)
                if (cartItem.getGoodsAdvertisement().getSeller().getId().equals(seller.getId()))
                    items.add(cartItem);
//            positions.add(new CartSellerPositionResponse(items, seller));
        }

        return positions;
    }

    private Set<GoodsSellerAccount> getSellers(List<GoodsCartItem> items) {
        Set<GoodsSellerAccount> sellers = new HashSet<>();
        items.forEach((i) -> sellers.add(i.getGoodsAdvertisement().getSeller()));
        return sellers;
    }

    public ChangeGoodsCartItemCountResponse checkGoodsCartItemCount(Long id, Integer count) {
        UserAccount user = userAccountService.findBySecurityContextHolderOrReturnNull();
        count = goodsCartItemService.checkCartItemCount(id, user.getCart(), count);
        return new ChangeGoodsCartItemCountResponse(count);
    }

    public CartSellerPositionResponse changeSellerPositionCurrency(Long[] ids, String currency) {
        List<GoodsCartItem> cartItems = new ArrayList<>();
        Cart cart = userAccountService.findBySecurityContextHolderOrReturnNull().getCart();
        for (int i = 0; i < ids.length; i++) {
            cartItems.add(goodsCartItemService.findByIdAndCart(ids[i], cart));
        }

        CartSellerPositionResponse cartSellerPositionResponse = new CartSellerPositionResponse(cartItems, cartItems.get(0).getGoodsAdvertisement().getSeller(), currencyService.findCurrencyByCurrencyCode("USD"));
        List<GoodsCartItemResponse> responses = new ArrayList<>();

        for (GoodsCartItem cartItem : cartItems) {
            GoodsCartItemResponse cartItemResponse = new GoodsCartItemResponse(cartItem);
            if (cartItem.getValuesPriceCount().hasCurrency(currency)) {
                cartItemResponse.getPriceCountResponse().setPrice(cartItem.getValuesPriceCount().getPriceByCurrency(currency));
                cartItemResponse.getPriceCountResponse().setPriceWithDiscount(cartItem.getValuesPriceCount().getPriceWithCurrentDiscount(currency));
                cartItemResponse.getPriceCountResponse().setExchanged(false);
            } else {
                cartItemResponse
                        .getPriceCountResponse()
                        .setPrice(
                                currencyService.exchangeCurrencyFromDollar(cartItem.getValuesPriceCount().getPriceByCurrency("USD"), currency)
                        );
                cartItemResponse
                        .getPriceCountResponse()
                        .setPriceWithDiscount(
                                currencyService.exchangeCurrencyFromDollar(cartItem.getValuesPriceCount().getPriceWithCurrentDiscount("USD"), currency)
                        );
                cartItemResponse.getPriceCountResponse().setExchanged(true);
            }
            responses.add(cartItemResponse);
        }
        cartSellerPositionResponse.setItems(responses);
        cartSellerPositionResponse.setCurrencyResponse(new CurrencyResponse(currencyService.findCurrencyByCurrencyCode(currency)));
        return cartSellerPositionResponse;
    }
}
