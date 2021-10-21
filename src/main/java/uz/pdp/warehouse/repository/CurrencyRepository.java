package uz.pdp.warehouse.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.warehouse.entity.Currency;

import java.util.Optional;

public interface CurrencyRepository extends JpaRepository<Currency, Integer> {

    Optional<Currency> findByName(String name);

    Page<Currency> findAllByActiveTrue(Pageable pageable);

    Optional<Currency> findByIdAndActiveTrue(Integer id);

    Optional<Currency> findByNameAndActiveFalse(String name);


}
