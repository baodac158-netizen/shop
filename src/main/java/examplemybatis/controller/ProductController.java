package examplemybatis.controller;

import examplemybatis.pojo.Product;
import examplemybatis.pojo.Result;
import examplemybatis.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {


    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService){this.productService = productService;}

    @GetMapping
    public List<Product> listAll() {
        return productService.listAll();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Integer id) {
        return productService.selectById(id);
    }

    @GetMapping("/search")
    public List<Product> searchByName(@RequestParam String keyword) {
        return productService.searchByName(keyword);
    }

    @PostMapping
    public Result add(@RequestBody Product product) {
        productService.add(product);
        return Result.success();
    }
    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable Integer ids){

        productService.delete(ids);
        return Result.success();
    }


}

