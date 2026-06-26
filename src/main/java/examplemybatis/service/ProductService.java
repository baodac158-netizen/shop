package examplemybatis.service;

import examplemybatis.pojo.Product;

import java.util.List;

public interface ProductService {
    List<Product> listAll();

    Product selectById(Integer id);

    List<Product> searchByName(String keyword);

    void add(Product product);

    void delete(Integer integer);
}

