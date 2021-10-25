package uz.pdp.warehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.pdp.warehouse.common.Result;
import uz.pdp.warehouse.entity.*;
import uz.pdp.warehouse.payload.InputProductDto;
import uz.pdp.warehouse.repository.InputProductRepository;
import uz.pdp.warehouse.repository.InputRepository;
import uz.pdp.warehouse.repository.ProductRepository;

import java.util.Optional;

@Service
public class InputProductService {

    @Autowired
    InputProductRepository inputProductRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    InputRepository inputRepository;

    public Result post(InputProductDto inputProductDto) {

        Optional<Input> inputOptional = inputRepository.findById(inputProductDto.getInputId());
        if (!inputOptional.isPresent())
            return new Result("input not found", false);

        Optional<Product> optionalProduct = productRepository.findById(inputProductDto.getProductId());
        if (!optionalProduct.isPresent())
            return new Result("product not found", false);

        InputProduct inputProduct = new InputProduct();
        inputProduct.setAmount(inputProductDto.getAmount());
        inputProduct.setPrice(inputProductDto.getPrice());
        inputProduct.setInput(inputOptional.get());
        inputProduct.setProduct(optionalProduct.get());

        inputProductRepository.save(inputProduct);
        return new Result("successfully saved", true);

    }

    public Page<InputProduct> getAll(Pageable pageable) {

        return inputProductRepository.findAll(pageable);

    }

    public Result findOne(Integer id) {

        Optional<InputProduct> inputProduct = inputProductRepository.findById(id);
        if (inputProduct.isPresent())
            return new Result("success", true, inputProduct.get());

        return new Result("doesnt exist", false);

    }

    public Result delete(Integer id) {
        Optional<InputProduct> inputProduct = inputProductRepository.findById(id);
        if (inputProduct.isPresent()){
            inputProductRepository.deleteById(id);
            return new Result("successfully deleted", true);
        }
        return new Result("input product not found", false);
    }


    public Result edit(Integer id, InputProductDto inputProductDto) {

        Optional<InputProduct> byId = inputProductRepository.findById(id);
        if (!byId.isPresent())
            return new Result("input product not found", false);

        Optional<Input> inputOptional = inputRepository.findById(inputProductDto.getInputId());
        if (!inputOptional.isPresent())
            return new Result("input not found", false);

        Optional<Product> optionalProduct = productRepository.findById(inputProductDto.getProductId());
        if (!optionalProduct.isPresent())
            return new Result("product not found", false);

        InputProduct inputProduct = new InputProduct();
        inputProduct.setId(id);
        inputProduct.setAmount(inputProductDto.getAmount());
        inputProduct.setPrice(inputProductDto.getPrice());
        inputProduct.setInput(inputOptional.get());
        inputProduct.setProduct(optionalProduct.get());

        inputProductRepository.save(inputProduct);
        return new Result("successfully edited", true);

    }
}
