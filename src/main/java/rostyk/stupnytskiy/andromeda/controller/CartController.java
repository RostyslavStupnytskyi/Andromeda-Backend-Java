package rostyk.stupnytskiy.andromeda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rostyk.stupnytskiy.andromeda.service.cart.CartService;

@CrossOrigin
@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping
    private void addGoodsItemToCart(Long id){
        cartService.addGoodsItemToCart(id);
    }

    @PutMapping
    private void updateGoodsCartItemCount(Long id, Integer count){
        cartService.updateGoodsCartItemCount(id, count);
    }

    @DeleteMapping
    private void delete(Long id){
        cartService.deleteGoodsItemFromCart(id);
    }
}
