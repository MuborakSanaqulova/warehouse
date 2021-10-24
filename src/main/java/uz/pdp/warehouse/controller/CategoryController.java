package uz.pdp.warehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.warehouse.common.Result;
import uz.pdp.warehouse.payload.CategoryDto;
import uz.pdp.warehouse.service.CategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PostMapping
    public Result post(@RequestBody CategoryDto categoryDto){
        return categoryService.post(categoryDto);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        return categoryService.delete(id);
    }
}
