package examplemybatis.mapper;

import examplemybatis.pojo.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ProductMapper {

    List<Product> findAll();

    Product findById(@Param("id") Integer id);

    void insert(Product product);

    void update(Product product);

    void delete(@Param("id") Integer id);

    List<Product> searchByName(String s);
}
