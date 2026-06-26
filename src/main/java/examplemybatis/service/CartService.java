package examplemybatis.service;


import examplemybatis.pojo.CartItem;

import java.util.List;

public interface CartService {

    List<CartItem> getCartByUser(Long userId);

    void addOrUpdateCart(CartItem cartItem);

    void updateQuantity(CartItem cartItem);

    void deleteCart(Long cartItemId);
}
