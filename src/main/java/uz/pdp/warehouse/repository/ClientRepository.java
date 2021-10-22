package uz.pdp.warehouse.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.warehouse.entity.Client;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Integer> {

    Optional<Client> findByPhoneNumber(String phoneNumber);

    Page<Client> findAllByActiveTrue(Pageable pageable);

    Optional<Client> findByIdAndActiveTrue(Integer id);

    Optional<Client> findByPhoneNumberAndActiveFalse(String phoneNumber);
}
