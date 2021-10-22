package uz.pdp.warehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import uz.pdp.warehouse.common.Result;
import uz.pdp.warehouse.entity.Client;
import uz.pdp.warehouse.entity.Supplier;
import uz.pdp.warehouse.service.SupplierService;

@RestController
@RequestMapping("/supplier")
public class SupplierController {

    @Autowired
    SupplierService supplierService;

    @PostMapping
    public Result post(@RequestBody Supplier supplier){
        return supplierService.post(supplier);
    }

    @GetMapping
    public Page<Supplier> getAll(@PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        return supplierService.getAll(pageable);
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id){
        return supplierService.findOne(id);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        return supplierService.delete(id);
    }

    @PutMapping("/{id}")
    public Result edit(@PathVariable Integer id, @RequestBody Supplier supplier){
        return supplierService.edit(id, supplier);
    }

}
