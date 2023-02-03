package src.demo.model.entities.repositories;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import src.demo.model.entities.Product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductRepositoryTest {
    @Autowired
    ProductRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void searchByNameLikeIgnoreCase() {
        //given
        String name = "play1";
        var product = new Product(name, 2600);
        var product2 = new Product("play5", 3000);
        underTest.save(product);
        underTest.save(product2);
        //when
        Product result = underTest.findByNameContains(name);
        //then
        assertThat(product).isSameAs(result);
    }

    @Test
    void findByNameContains() {
        //given
        String name = "play1";
        var product = new Product(name, 2600);
        var product2 = new Product("play5", 3000);
        //when
        underTest.save(product);
        underTest.save(product2);
        Product result = underTest.findByNameContains(name);
        //then
        assertThat(product).isSameAs(result);
    }
}