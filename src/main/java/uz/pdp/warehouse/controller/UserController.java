package uz.pdp.warehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import uz.pdp.warehouse.common.Result;
import uz.pdp.warehouse.entity.User;
import uz.pdp.warehouse.payload.UserDto;
import uz.pdp.warehouse.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping
    public Result post(@RequestBody UserDto userDto){
        return userService.post(userDto);
    }

    @GetMapping
    public Page<User> getAll(@PageableDefault(sort = "id", direction = Sort.Direction.ASC)Pageable pageable){
        return userService.getAll(pageable);
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id){
        return userService.findOne(id);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        return userService.delete(id);
    }

    @PutMapping("/{id}")
    public Result edit(@PathVariable Integer id, @RequestBody UserDto userDto){
        return userService.edit(id, userDto);
    }

}
