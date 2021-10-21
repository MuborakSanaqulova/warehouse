package uz.pdp.warehouse.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.warehouse.entity.Measurement;

import java.util.Optional;

public interface MeasurementRepository extends JpaRepository<Measurement, Integer> {
    Optional<Measurement> findByName(String name);

    Page<Measurement> findAllByActiveTrue(Pageable pageable);

    Optional<Measurement> findByIdAndActiveTrue(Integer id);

    Optional<Measurement> findByNameAndActiveFalse(String name);
}
