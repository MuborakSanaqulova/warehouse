package uz.pdp.warehouse.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.warehouse.entity.Currency;
import uz.pdp.warehouse.entity.Measurement;
import uz.pdp.warehouse.entity.Warehouse;

import java.util.Optional;

public interface WarehouseRepository extends JpaRepository<Warehouse, Integer> {
    Optional<Warehouse> findByName(String name);

    Page<Warehouse> findAllByActiveTrue(Pageable pageable);

    Optional<Warehouse> findByIdAndActiveTrue(Integer id);

    Optional<Warehouse> findByNameAndActiveFalse(String name);
}
