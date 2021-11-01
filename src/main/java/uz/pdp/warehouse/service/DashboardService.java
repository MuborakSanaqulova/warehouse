package uz.pdp.warehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.pdp.warehouse.common.LocalDateTimeConverter;
import uz.pdp.warehouse.common.Result;
import uz.pdp.warehouse.entity.InputProduct;
import uz.pdp.warehouse.entity.Product;
import uz.pdp.warehouse.repository.InputProductRepository;

import javax.persistence.Convert;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DashboardService {

    @Autowired
    InputProductRepository inputProductRepository;


    public Result getInputSum() {
        LocalDateTime start = LocalDate.now().atStartOfDay();
        LocalDateTime end = start.plusHours(24);
        Double sumPrice = inputProductRepository.getInputSumPrice(start, end);
        Double sumAmount = inputProductRepository.getInputSumAmount(start, end);
        return new Result("success", true, "amount: " + sumAmount + " price: " + sumPrice);

    }

    public Result getExpiredProducts(Pageable pageable) {

        LocalDate currentDate = LocalDate.now();
        Long dateIsBefore = inputProductRepository.countAllByExpireDateIsBefore(currentDate);
        Page<InputProduct> all = inputProductRepository.findAllByExpireDateIsBefore(currentDate, pageable);
        return new Result("expired products: " + dateIsBefore, true, all);

    }

//    public Product maxOutputProduct() {
//    }
}
