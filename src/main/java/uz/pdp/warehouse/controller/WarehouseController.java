package uz.pdp.warehouse.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import uz.pdp.warehouse.common.Result;
import uz.pdp.warehouse.entity.Warehouse;
import uz.pdp.warehouse.service.WarehouseService;

@RestController
@RequestMapping("/warehouse")
public class WarehouseController {

    @Autowired
    WarehouseService warehouseService;

    @PostMapping
    public Result post(@RequestBody Warehouse warehouse) {
        return warehouseService.post(warehouse);
    }

    @GetMapping
    public Page<Warehouse> getAll(@PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return warehouseService.getAll(pageable);
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return warehouseService.findOne(id);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return warehouseService.delete(id);
    }

    @PutMapping("/{id}")
    public Result edit(@PathVariable Integer id, @RequestBody Warehouse warehouse) {
        return warehouseService.edit(id, warehouse);
    }

}
