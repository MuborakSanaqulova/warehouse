package uz.pdp.warehouse.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.pdp.warehouse.entity.InputProduct;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface InputProductRepository extends JpaRepository<InputProduct, Integer> {

    @Query(value = "select sum(price) from input_product join input i on i.id = input_product.input_id where i.date>=:start and i.date<=:end", nativeQuery = true)
    Double getInputSumPrice(LocalDateTime start, LocalDateTime end);

    @Query(value = "select sum(amount) from input_product join input i on i.id = input_product.input_id where i.date>=:start and i.date<=:end", nativeQuery = true)
    Double getInputSumAmount(LocalDateTime start, LocalDateTime end);

    Page<InputProduct> findAllByExpireDateIsBefore(LocalDate expireDate, Pageable pageable);
    Long countAllByExpireDateIsBefore(LocalDate expireDate);

}

/**
 * Dashboard
 * Kunlik kirim bo’lgan mahsulotlar (qiymati, umumiy summasi)
 * Kunlik eng ko’p chiqim qilingan mahsulotlar
 * Yaroqlilik muddati yetib qolgan mahsulotlar soni. Hohlasa to’liqroq ma’lumot olishi mumkin.
 */