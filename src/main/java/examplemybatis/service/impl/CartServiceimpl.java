package examplemybatis.service.impl;

import examplemybatis.mapper.CartMapper;
import examplemybatis.pojo.CartItem;
import examplemybatis.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceimpl implements CartService {


    private final CartMapper cartMapper;

    @Autowired
    public CartServiceimpl(CartMapper cartMapper){this.cartMapper = cartMapper;}


    @Override
    public List<CartItem> getCartByUser(Long userId) {
        return cartMapper.listByUserId(userId);
    }

    @Override
    public void addOrUpdateCart(CartItem cartItem) {
        cartMapper.insert(cartItem);
    }

    @Override
    public void updateQuantity(CartItem cartItem) {
        Long id = cartItem.getId();
        Integer quantity = cartItem.getQuantity();
        cartMapper.updateQuantity(id,quantity);
    }

    @Override
    public void deleteCart(Long cartItemId) {
        cartMapper.delete(cartItemId);
    }
}
