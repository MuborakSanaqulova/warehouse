package uz.pdp.warehouse.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.pdp.warehouse.entity.Product;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    Optional<Product> findByName(String name);

    Page<Product> findAllByActiveTrue(Pageable pageable);

    Optional<Product> findByIdAndActiveTrue(Integer id);

    Optional<Product> findByNameAndActiveFalse(String name);

    @Transactional
    @Modifying
    @Query(value = "update product set active = false where category_id = :id",nativeQuery = true)
    void deleteByCategoryQuery(Integer id);
}
