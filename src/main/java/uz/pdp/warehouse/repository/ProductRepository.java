package uz.pdp.warehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.warehouse.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
}
