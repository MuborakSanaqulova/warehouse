package uz.pdp.warehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.warehouse.entity.Output;

@Repository
public interface OutputRepository extends JpaRepository<Output, Integer> {
}
