package src.demo.model.entities.repositories;

import org.springframework.data.repository.CrudRepository;
import src.demo.model.entities.Product;

public interface ProductRepository extends CrudRepository<Product, Integer> {

    Product searchByNameLikeIgnoreCase(String name);
    Product findByNameContains(String name);


}
