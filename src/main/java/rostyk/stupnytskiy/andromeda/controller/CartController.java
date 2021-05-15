package rostyk.stupnytskiy.andromeda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rostyk.stupnytskiy.andromeda.dto.request.cart.GoodsCartItemForCountingPriceRequest;
import rostyk.stupnytskiy.andromeda.dto.response.cart.CartResponse;
import rostyk.stupnytskiy.andromeda.dto.response.cart.CartSellerPositionResponse;
import rostyk.stupnytskiy.andromeda.dto.response.cart.ChangeGoodsCartItemCountResponse;
import rostyk.stupnytskiy.andromeda.service.cart.CartService;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping
    private void addGoodsItemToCart(Long id, Long deliveryId, Long paramsValuesId) {
        cartService.addGoodsItemToCart(id, deliveryId, paramsValuesId);
    }

    @PutMapping
    private ChangeGoodsCartItemCountResponse updateGoodsCartItemCount(Long id, Integer count) {
        return cartService.updateGoodsCartItemCount(id, count);
    }

    @PutMapping("/check")
    private ChangeGoodsCartItemCountResponse checkGoodsCartItemCount(Long id, Integer count) {
        return cartService.checkGoodsCartItemCount(id, count);
    }

    @GetMapping("exchange")
    private CartSellerPositionResponse changeSellerPositionCurrency(Long[] ids, String currency) {
        return cartService.changeSellerPositionCurrency(ids, currency);
    }

    @DeleteMapping
    private void delete(Long id) {
        cartService.deleteGoodsItemFromCart(id);
    }

    @GetMapping
    private CartResponse getCart() {
        return cartService.getCartResponse();
    }

    @GetMapping("/items")
    private List<CartSellerPositionResponse> getItemsForOrder(Long[] id) {
        return cartService.getItemsForOrder(id);
    }
}
