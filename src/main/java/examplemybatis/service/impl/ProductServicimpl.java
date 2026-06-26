package examplemybatis.service.impl;

import examplemybatis.mapper.ProductMapper;
import examplemybatis.pojo.Product;
import examplemybatis.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServicimpl implements ProductService {

    private final ProductMapper productMapper;

    @Autowired
    public ProductServicimpl(ProductMapper productMapper){
        this.productMapper = productMapper;
    }

    @Override
    public List<Product> listAll() {
        return productMapper.findAll();
    }

    @Override
    public Product selectById(Integer id) {
        return productMapper.findById(id);
    }

    @Override
    public List<Product> searchByName(String keyword) {
        return productMapper.searchByName("%" + keyword + "%");
    }

    @Override
    public void add(Product product) {
        productMapper.insert(product);
    }

    @Override
    public void delete(Integer integer) {
        productMapper.delete(integer);
    }


}

