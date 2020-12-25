package rostyk.stupnytskiy.andromeda.service.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.andromeda.entity.cart.Cart;
import rostyk.stupnytskiy.andromeda.entity.cart.goods_cart_item.GoodsCartItem;
import rostyk.stupnytskiy.andromeda.repository.GoodsCartItemRepository;
import rostyk.stupnytskiy.andromeda.service.advertisement.AdvertisementService;

@Service
public class GoodsCartItemService {

    @Autowired
    private GoodsCartItemRepository goodsCartItemRepository;

    @Autowired
    private AdvertisementService advertisementService;

    public void addGoodsItemToCart(Cart cart, Long id) {
        if (!isExits(cart, id)) {
            GoodsCartItem goodsCartItem = createNewGoodsCartItem(cart, id);
            save(goodsCartItem);
        }
    }

    public void save(GoodsCartItem cartItem) {
        goodsCartItemRepository.save(cartItem);
    }

    public boolean isExits(Cart cart, Long id) {
        return goodsCartItemRepository.existsByCartAndGoodsAdvertisementId(cart, id);
    }

    public GoodsCartItem createNewGoodsCartItem(Cart cart, Long advertisementId) {
        GoodsCartItem goodsCartItem = new GoodsCartItem();
        goodsCartItem.setCart(cart);
        goodsCartItem.setGoodsAdvertisement(advertisementService.findGoodsAdvertisementById(advertisementId));
        return goodsCartItem;
    }

    public void updateCartItemCount(Long id,Cart cart, Integer count){
        GoodsCartItem goodsCartItem = findByIdAndCart(id, cart);
        if (goodsCartItem.getGoodsAdvertisement().getCount() >= count && count <= 999) {
            goodsCartItem.setCount(count);
            save(goodsCartItem);
        }
    }

    public GoodsCartItem findByIdAndCart(Long id, Cart cart){
        return goodsCartItemRepository.findOneByIdAndCart(id,cart).orElseThrow(IllegalArgumentException::new);
    }

    public void deleteGoodsCartItem(Long id, Cart cart) {
        goodsCartItemRepository.delete(findByIdAndCart(id, cart));
    }
}
