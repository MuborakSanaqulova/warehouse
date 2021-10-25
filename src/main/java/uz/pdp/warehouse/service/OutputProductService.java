package uz.pdp.warehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.pdp.warehouse.common.Result;
import uz.pdp.warehouse.entity.Output;
import uz.pdp.warehouse.entity.OutputProduct;
import uz.pdp.warehouse.entity.Product;
import uz.pdp.warehouse.payload.OutputProductDto;
import uz.pdp.warehouse.repository.OutputProductRepository;
import uz.pdp.warehouse.repository.OutputRepository;
import uz.pdp.warehouse.repository.ProductRepository;

import java.util.Optional;

@Service
public class OutputProductService {

    @Autowired
    OutputProductRepository outputProductRepository;

    @Autowired
    OutputRepository outputRepository;

    @Autowired
    ProductRepository productRepository;

    public Result post(OutputProductDto outputProductDto) {

        Optional<Output> optionalOutput = outputRepository.findById(outputProductDto.getOutputId());
        if (!optionalOutput.isPresent())
            return new Result("output not found", false);

        Optional<Product> optionalProduct = productRepository.findById(outputProductDto.getProductId());
        if (!optionalProduct.isPresent())
            return new Result("product not found", false);

        OutputProduct outputProduct = new OutputProduct();
        outputProduct.setAmount(outputProductDto.getAmount());
        outputProduct.setPrice(outputProductDto.getPrice());
        outputProduct.setOutput(optionalOutput.get());
        outputProduct.setProduct(optionalProduct.get());

        outputProductRepository.save(outputProduct);
        return new Result("successfully saved", true);

    }

    public Page<OutputProduct> getAll(Pageable pageable) {

        return outputProductRepository.findAll(pageable);

    }

    public Result findOne(Integer id) {

        Optional<OutputProduct> outputProduct = outputProductRepository.findById(id);
        if (outputProduct.isPresent())
            return new Result("success", true, outputProduct.get());

        return new Result("doesnt exist", false);

    }

    public Result delete(Integer id) {
        Optional<OutputProduct> outputProduct = outputProductRepository.findById(id);
        if (outputProduct.isPresent()){
            outputProductRepository.deleteById(id);
            return new Result("successfully deleted", true);
        }
        return new Result("output product not found", false);
    }


    public Result edit(Integer id, OutputProductDto outputProductDto) {

        Optional<OutputProduct> byId = outputProductRepository.findById(id);
        if (!byId.isPresent())
            return new Result("output product not found", false);

        Optional<Output> optionalOutput = outputRepository.findById(outputProductDto.getOutputId());
        if (!optionalOutput.isPresent())
            return new Result("output not found", false);

        Optional<Product> optionalProduct = productRepository.findById(outputProductDto.getProductId());
        if (!optionalProduct.isPresent())
            return new Result("product not found", false);

        OutputProduct outputProduct = new OutputProduct();
        outputProduct.setId(id);
        outputProduct.setAmount(outputProductDto.getAmount());
        outputProduct.setPrice(outputProductDto.getPrice());
        outputProduct.setOutput(optionalOutput.get());
        outputProduct.setProduct(optionalProduct.get());

        outputProductRepository.save(outputProduct);
        return new Result("successfully edited", true);

    }
}
