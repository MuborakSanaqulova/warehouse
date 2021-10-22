package uz.pdp.warehouse.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.warehouse.entity.Client;
import uz.pdp.warehouse.entity.Supplier;

import java.util.Optional;

public interface SupplierRepository extends JpaRepository<Supplier, Integer> {

    Optional<Supplier> findByPhoneNumber(String phoneNumber);

    Page<Supplier> findAllByActiveTrue(Pageable pageable);

    Optional<Supplier> findByIdAndActiveTrue(Integer id);

    Optional<Supplier> findByPhoneNumberAndActiveFalse(String phoneNumber);

}
