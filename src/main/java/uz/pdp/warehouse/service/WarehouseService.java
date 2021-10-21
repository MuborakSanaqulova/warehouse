package uz.pdp.warehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.pdp.warehouse.common.Result;
import uz.pdp.warehouse.entity.Warehouse;
import uz.pdp.warehouse.repository.WarehouseRepository;

import java.util.Optional;

@Service
public class WarehouseService {

    @Autowired
    WarehouseRepository warehouseRepository;

    public Result post(Warehouse warehouse) {

        Optional<Warehouse> warehouseOptional = warehouseRepository.findByName(warehouse.getName());
        if (!warehouseOptional.isPresent()) {
            warehouseRepository.save(warehouse);
            return new Result("warehouse successfully added", true);
        }
        if (warehouseOptional.get().isActive())
            return new Result("already exist warehouse", false);

        warehouse.setId(warehouseOptional.get().getId());
        warehouse.setActive(true);
        warehouseRepository.save(warehouse);
        return new Result("successfully added", true);
    }

    public Page<Warehouse> getAll(Pageable pageable) {
        return warehouseRepository.findAllByActiveTrue(pageable);
    }


    public Result findOne(Integer id) {
        Optional<Warehouse> optionalWarehouse = warehouseRepository.findByIdAndActiveTrue(id);

        return optionalWarehouse.map(warehouse -> new Result("Success", true, warehouse))
                .orElseGet(() -> new Result("Not exist", false));
    }

    public Result delete(Integer id) {
        Optional<Warehouse> optionalWarehouse = warehouseRepository.findByIdAndActiveTrue(id);
        if (optionalWarehouse.isPresent()){
            Warehouse warehouse = optionalWarehouse.get();
            warehouse.setActive(false);
            warehouseRepository.save(warehouse);
            return new Result("deleted", true);
        }
        return new Result("warehouse not found", false);
    }

    public Result edit(Integer id, Warehouse warehouse) {

        Optional<Warehouse> optionalWarehouse = warehouseRepository.findByIdAndActiveTrue(id);

        if (!optionalWarehouse.isPresent()){
            return new Result("warehouse not found", false);
        }

        Optional<Warehouse> warehouseOptional = warehouseRepository.findByNameAndActiveFalse(warehouse.getName());
        warehouseOptional.ifPresent(value -> warehouseRepository.deleteById(value.getId()));

        warehouse.setId(id);
        warehouseRepository.save(warehouse);
        return new Result("successfully edited", true, warehouse);

    }

}
