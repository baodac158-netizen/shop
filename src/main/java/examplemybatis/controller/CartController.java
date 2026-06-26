package examplemybatis.controller;

import examplemybatis.pojo.CartItem;
import examplemybatis.service.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService){this.cartService = cartService;}

    // 获取购物车列表
    @GetMapping
    public List<CartItem> getCart(@RequestParam Long userId) {
        return cartService.getCartByUser(userId);
    }

    // 添加商品到购物车
    @PostMapping("/add")
    public void addToCart(@RequestBody CartItem cartItem) {
        cartService.addOrUpdateCart(cartItem);
    }

    // 更新商品数量
    @PutMapping("/update")
    public void updateCart(@RequestBody CartItem cartItem) {
        cartService.updateQuantity(cartItem);
    }

    // 删除商品
    @DeleteMapping("/delete")
    public void deleteCart(@RequestParam Long cartItemId) {
        cartService.deleteCart(cartItemId);
    }
}
