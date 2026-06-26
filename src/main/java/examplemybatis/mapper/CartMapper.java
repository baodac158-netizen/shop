package examplemybatis.mapper;

import examplemybatis.pojo.CartItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface CartMapper {

    // 查询某个用户的购物车列表
    List<CartItem> listByUserId(@Param("userId") Long userId);

    // 添加购物车项
    int insert(CartItem cartItem);

    // 更新数量
    int updateQuantity(@Param("id") Long id, @Param("quantity") Integer quantity);

    // 删除购物车项
    int delete(@Param("id") Long id);

}
