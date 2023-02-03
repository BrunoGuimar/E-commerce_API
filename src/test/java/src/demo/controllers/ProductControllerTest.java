package src.demo.controllers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import src.demo.model.entities.Product;
import src.demo.model.entities.repositories.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductControllerTest {
    @Autowired
    ProductRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void shouldGetProductsWithPriceBiggerThanInformed() {
        //given
        var product1 = new Product("TEST", 1000);
        var product2 = new Product("TEST", 2000);
        var product3 = new Product("TEST", 3000);
        underTest.save(product1);
        underTest.save(product2);
        underTest.save(product3);
        //when
        List<Product> products = new ArrayList<>();
        Iterable<Product> productsFiltered = underTest.findAll();
        productsFiltered.forEach(product -> products.add(product));
        Stream<Product> actual =  products.stream().filter(product -> product.getPrice() > 2999);
        List actualList = actual.toList();
        //then
        assertThat(actualList).isNotEmpty();
    }
}