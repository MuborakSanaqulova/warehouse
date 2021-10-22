package uz.pdp.warehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.pdp.warehouse.common.Result;
import uz.pdp.warehouse.entity.Supplier;
import uz.pdp.warehouse.repository.SupplierRepository;

import java.util.Optional;

@Service
public class SupplierService {

    @Autowired
    SupplierRepository supplierRepository;

    public Result post(Supplier supplier) {
        Optional<Supplier> optionalSupplier = supplierRepository.findByPhoneNumber(supplier.getPhoneNumber());

        if (optionalSupplier.isPresent()){
            if(optionalSupplier.get().isActive()){
                return new Result("already exist", false);
            }
            else {
                supplierRepository.delete(optionalSupplier.get());
            }
        }

        supplierRepository.save(supplier);
        return new Result("suceesfully added", true);
    }

    public Page<Supplier> getAll(Pageable pageable) {return supplierRepository.findAllByActiveTrue(pageable);}

    public Result findOne(Integer id) {
        Optional<Supplier> optionalSupplier = supplierRepository.findByIdAndActiveTrue(id);
        return optionalSupplier.map(client -> new Result("success", true, client))
                .orElseGet(() -> new Result("not found", false));
    }

    public Result delete(Integer id) {
        Optional<Supplier> optionalSupplier = supplierRepository.findByIdAndActiveTrue(id);
        if (optionalSupplier.isPresent()){
            Supplier supplier = optionalSupplier.get();
            supplier.setActive(false);
            supplierRepository.save(supplier);
            return new Result("deleted", true);
        }
        return new Result("not found", false);
    }

    public Result edit(Integer id, Supplier supplier) {
        Optional<Supplier> optionalSupplier = supplierRepository.findByIdAndActiveTrue(id);
        if (!optionalSupplier.isPresent()){
            return new Result("not found", false);
        }

        Optional<Supplier> numberAndActiveFalse = supplierRepository.findByPhoneNumberAndActiveFalse(supplier.getPhoneNumber());
        if (numberAndActiveFalse.isPresent()){
            supplierRepository.delete(numberAndActiveFalse.get());
        }

        supplier.setId(id);
        supplierRepository.save(supplier);
        return new Result("successfully edited", true, optionalSupplier.get());
    }

}
