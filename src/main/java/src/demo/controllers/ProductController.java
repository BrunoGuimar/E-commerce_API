package src.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import src.demo.model.entities.Product;
import src.demo.model.entities.repositories.ProductRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductRepository repository;

    @PostMapping
    public Product addProduct(Product product){
        Product productAlreadyExists = repository.findByNameContainsIgnoreCase(product.getName());
        if(productAlreadyExists != null){
            productAlreadyExists.setAmount(productAlreadyExists.getAmount() + 1);
            repository.save(productAlreadyExists);
            return productAlreadyExists;
        }
        repository.save(product);
        return product;

    }

    @PutMapping
    public Product setProduct(Product product, @RequestParam int id){
        Product finalProduct = repository.findById(id).get();
        if(product.getName() != null){
            finalProduct.setName(product.getName());
        }
        if(product.getPrice() != 0){
            finalProduct.setPrice(product.getPrice());
        }
        if(product.getAmount() > 0){
            finalProduct.setAmount(product.getAmount());
        }
        return repository.save(finalProduct);
    }

    @GetMapping
    public Iterable<Product> getProducts(){
        return repository.findAll();
    }

    @GetMapping(path = "/{name}")
    public Product getProductByName(@PathVariable String name){
        return repository.searchByNameLikeIgnoreCase(name);
    }
    @GetMapping(path = "/contains/{name}")
    public Product getProductsIfNameContains(@PathVariable String name){
        return repository.findByNameContainsIgnoreCase(name);
    }
    @GetMapping(path = "/price/{biggerThan}")
    public Object getProductsWithPriceBiggerThan(@PathVariable double biggerThan){
        List<Product> products = new ArrayList<>();
        Iterable<Product> productsFiltered = repository.findAll();
        productsFiltered.forEach(product -> products.add(product));
        return products.stream().filter(product -> product.getPrice() > biggerThan);
    }
    @DeleteMapping(path = "/{id}")
    public void deleteProductById(@PathVariable int id){
        repository.deleteById(id);
    }
}
