package uz.pdp.warehouse.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.warehouse.entity.Category;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Optional<Category> findByName(String name);

    Page<Category> findAllByActiveTrue(Pageable pageable);

    Optional<Category> findByIdAndActiveTrue(Integer id);

    Optional<Category> findByNameAndActiveFalse(String name);

    Optional<Category> findByNameAndActiveTrue(String name);
}
