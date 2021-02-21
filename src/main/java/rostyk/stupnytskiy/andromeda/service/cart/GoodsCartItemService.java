package rostyk.stupnytskiy.andromeda.service.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.andromeda.entity.cart.Cart;
import rostyk.stupnytskiy.andromeda.entity.cart.goods_cart_item.GoodsCartItem;
import rostyk.stupnytskiy.andromeda.repository.cart.GoodsCartItemRepository;
import rostyk.stupnytskiy.andromeda.service.DeliveryTypeService;
import rostyk.stupnytskiy.andromeda.service.advertisement.AdvertisementService;
import rostyk.stupnytskiy.andromeda.service.advertisement.goods_advertisement.parameter.ParameterService;

import java.time.LocalDateTime;

@Service
public class GoodsCartItemService {

    @Autowired
    private GoodsCartItemRepository goodsCartItemRepository;

    @Autowired
    private AdvertisementService advertisementService;

    @Autowired
    private DeliveryTypeService deliveryTypeService;

    @Autowired
    private ParameterService parameterService;

    public void addGoodsItemToCart(Cart cart, Long id, Long deliveryId, Long paramsValuesId) {
        if (!isExits(cart, id, paramsValuesId)) {
            GoodsCartItem goodsCartItem = createNewGoodsCartItem(cart, id, deliveryId, paramsValuesId);
            save(goodsCartItem);
        }
    }

    public void save(GoodsCartItem cartItem) {
        goodsCartItemRepository.save(cartItem);
    }

    public boolean isExits(Cart cart, Long id, Long paramsValuesId) {
        return goodsCartItemRepository.existsByCartAndGoodsAdvertisementIdAndAndValuesPriceCountId(cart, id, paramsValuesId);
    }

    public void auditIfItemCountLessOrEqualThanGoodsCountAndChangeIfBigger(GoodsCartItem item){
//        if (item.getCount() > item.getGoodsAdvertisement().getCount()){
//            item.setCount(item.getGoodsAdvertisement().getCount());
//            goodsCartItemRepository.save(item);
//        }
    }

    public GoodsCartItem createNewGoodsCartItem(Cart cart, Long advertisementId, Long deliveryId, Long paramsValuesId) {
        GoodsCartItem goodsCartItem = new GoodsCartItem();
        goodsCartItem.setCart(cart);
        goodsCartItem.setDate(LocalDateTime.now());
        goodsCartItem.setGoodsAdvertisement(advertisementService.findGoodsAdvertisementById(advertisementId));
        goodsCartItem.setValuesPriceCount(parameterService.findParametersValuesPriceCountById(paramsValuesId));
        goodsCartItem.setDeliveryType(deliveryTypeService.findById(deliveryId));
        return goodsCartItem;
    }

    public Integer updateCartItemCount(Long id,Cart cart, Integer count){
        GoodsCartItem goodsCartItem = findByIdAndCart(id, cart);
        if (count < 1) return 1;
        if (goodsCartItem.getValuesPriceCount().getCount() >= count) {
            goodsCartItem.setCount(count);
        } else {
            goodsCartItem.setCount(goodsCartItem.getValuesPriceCount().getCount());
        }
        save(goodsCartItem);
        return goodsCartItem.getCount();
    }

    public Integer checkCartItemCount(Long id,Cart cart, Integer count){
        GoodsCartItem goodsCartItem = findByIdAndCart(id, cart);
        if (count < 1) return 1;
        if (goodsCartItem.getValuesPriceCount().getCount() >= count) {
            goodsCartItem.setCount(count);
        } else {
            goodsCartItem.setCount(goodsCartItem.getValuesPriceCount().getCount());
        }
        return goodsCartItem.getCount();
    }



    public GoodsCartItem findByIdAndCart(Long id, Cart cart){
        return goodsCartItemRepository.findOneByIdAndCart(id,cart).orElseThrow(IllegalArgumentException::new);
    }

    public void deleteGoodsCartItem(Long id, Cart cart) {
        goodsCartItemRepository.delete(findByIdAndCart(id, cart));
    }
}
