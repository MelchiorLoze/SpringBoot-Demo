package com.example.demo.list_product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.product.Product;
import com.example.demo.product.ProductRepository;

@RestController
@RequestMapping("/list-products")
public class ListProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ListProductRepository listProductRepository;

    @GetMapping
    public List<ListProduct> getAllListProducts() {
        return listProductRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ListProduct createListProduct(@RequestBody Product product) {
        product = productRepository.save(product);
        return listProductRepository.save(new ListProduct(product, 1));
    }
}
