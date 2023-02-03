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
        return repository.save(product);
    }

    @PutMapping
    public Product setProduct(Product product, @RequestParam int id){
        Product oldProduct = repository.findById(id).get();
        if(product.getName() == null){
            product.setPrice(product.getPrice());
            product.setName(oldProduct.getName());
            repository.save(product);
        }
        if(product.getPrice() == 0){
            product.setName(product.getName());
            product.setPrice(oldProduct.getPrice());
            repository.save(product);
        }
        return product;
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
        return repository.findByNameContains(name);
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
