package uz.pdp.warehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import uz.pdp.warehouse.common.Result;
import uz.pdp.warehouse.entity.Input;
import uz.pdp.warehouse.entity.Supplier;
import uz.pdp.warehouse.payload.InputDto;
import uz.pdp.warehouse.service.InputService;

@RestController
@RequestMapping("/input")
public class InputController {

    @Autowired
    InputService inputService;

    @PostMapping
    public Result post(@RequestBody InputDto inputDto){
        return inputService.post(inputDto);
    }

    @GetMapping
    public Page<Input> getAll(@PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        return inputService.getAll(pageable);
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id){
        return inputService.findOne(id);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        return inputService.delete(id);
    }
//
//    @PutMapping("/{id}")
//    public Result edit(@PathVariable Integer id, @RequestBody InputDto inputDto){
//        return inputService.edit(id, inputDto);
//    }


}
