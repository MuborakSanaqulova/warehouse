package uz.pdp.warehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.pdp.warehouse.common.Result;
import uz.pdp.warehouse.entity.User;
import uz.pdp.warehouse.entity.Warehouse;
import uz.pdp.warehouse.payload.UserDto;
import uz.pdp.warehouse.repository.UserRepository;
import uz.pdp.warehouse.repository.WarehouseRepository;

import java.util.*;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    WarehouseRepository warehouseRepository;

    public Result post(UserDto userDto) {
        Optional<User> optionalUser = userRepository.findByPhoneNumber(userDto.getPhoneNumber());
        if (optionalUser.isPresent()) {
            if (optionalUser.get().isActive()) {
                return new Result("already exist", false);
            } else {
                userRepository.delete(optionalUser.get());
            }
        }

        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPassword(userDto.getPassword());
        user.setPhoneNumber(userDto.getPhoneNumber());

        Set<Warehouse> warehouses = new HashSet<>();
        for (Integer warehouseId : userDto.getWarehouses()) {
            Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(warehouseId);
            optionalWarehouse.ifPresent(warehouses::add);
        }

        user.setWarehouses(warehouses);
        userRepository.save(user);
        return new Result("successfully saved", true);

    }


    public Page<User> getAll(Pageable pageable) {
        return userRepository.findAllByActiveTrue(pageable);
    }


    public Result findOne(Integer id) {

        Optional<User> optionalUser = userRepository.findByIdAndActiveTrue(id);

        return optionalUser.map(user -> new Result("success", true, user))
                .orElseGet(() -> new Result("not found", false));
    }


    public Result delete(Integer id) {
        Optional<User> optionalUser = userRepository.findByIdAndActiveTrue(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setActive(false);
            userRepository.save(user);
            return new Result("successfully deleted", true);
        }
        return new Result("not found", false);
    }

    public Result edit(Integer id, UserDto userDto) {
        Optional<User> optionalUser = userRepository.findByIdAndActiveTrue(id);
        if (!optionalUser.isPresent()){
            return new Result("not found", false);
        }

        Optional<User> userOptional = userRepository.findByPhoneNumberAndActiveFalse(userDto.getPhoneNumber());
        userOptional.ifPresent(user -> userRepository.deleteById(user.getId()));

        User user = new User();
        user.setId(id);
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPassword(userDto.getPassword());
        user.setPhoneNumber(userDto.getPhoneNumber());

        Set<Warehouse> warehouses = new HashSet<>();
        for (Integer warehouseId : userDto.getWarehouses()) {
            Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(warehouseId);
            optionalWarehouse.ifPresent(warehouses::add);
        }
        user.setWarehouses(warehouses);
        userRepository.save(user);
        return new Result("successfully edited", true, user);
    }
}
