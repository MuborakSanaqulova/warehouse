package uz.pdp.warehouse.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.warehouse.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByPhoneNumber(String phoneNumber);

    Page<User> findAllByActiveTrue(Pageable pageable);

    Optional<User> findByIdAndActiveTrue(Integer id);

    Optional<User> findByPhoneNumberAndActiveFalse(String phoneNumber);
}
