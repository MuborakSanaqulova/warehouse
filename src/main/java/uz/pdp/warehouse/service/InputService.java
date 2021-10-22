package uz.pdp.warehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.pdp.warehouse.common.Result;
import uz.pdp.warehouse.entity.Currency;
import uz.pdp.warehouse.entity.Input;
import uz.pdp.warehouse.entity.Supplier;
import uz.pdp.warehouse.entity.Warehouse;
import uz.pdp.warehouse.payload.InputDto;
import uz.pdp.warehouse.repository.CurrencyRepository;
import uz.pdp.warehouse.repository.InputRepository;
import uz.pdp.warehouse.repository.SupplierRepository;
import uz.pdp.warehouse.repository.WarehouseRepository;


import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class InputService {

    @Autowired
    InputRepository inputRepository;

    @Autowired
    SupplierRepository supplierRepository;

    @Autowired
    WarehouseRepository warehouseRepository;

    @Autowired
    CurrencyRepository currencyRepository;

    public Result post(InputDto inputDto) {

        Optional<Supplier> bySupplierId = supplierRepository.findById(inputDto.getSupplierId());
        if (!bySupplierId.isPresent())
            return new Result("supplier not found", false);

        Optional<Warehouse> byWarehouseId = warehouseRepository.findById(inputDto.getWarehouseId());
        if (!byWarehouseId.isPresent())
            return new Result("warehouse not found", false);

        Optional<Currency> byCurrencyId = currencyRepository.findById(inputDto.getCurrencyId());
        if (!byCurrencyId.isPresent())
            return new Result("currency not found", false);

        Input input = new Input();
        input.setDate(LocalDateTime.now());
        input.setCurrency(byCurrencyId.get());
        input.setSupplier(bySupplierId.get());
        input.setWarehouse(byWarehouseId.get());
        input.setFactureNumber(inputDto.getFactureNumber());
        inputRepository.save(input);

        return new Result("saved",true);


    }

    public Page<Input> getAll(Pageable pageable) {

        return inputRepository.findAll(pageable);

    }

    public Result findOne(Integer id) {
        Optional<Input> optionalInput = inputRepository.findById(id);
        if (optionalInput.isPresent()){
            return new Result("success", true, optionalInput.get());
        }
        return new Result("not found", false);
    }

    public Result delete(Integer id) {
        Optional<Input> optionalInput = inputRepository.findById(id);
        if (optionalInput.isPresent()){
            inputRepository.deleteById(id);
            return new Result("successfully deleted", true);
        }
        return new Result("not found", false);
    }
//
//    public Result edit(Integer id, InputDto inputDto) {
//
//    }
}
