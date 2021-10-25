package uz.pdp.warehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.pdp.warehouse.common.Result;
import uz.pdp.warehouse.entity.Attachment;
import uz.pdp.warehouse.entity.Category;
import uz.pdp.warehouse.entity.Measurement;
import uz.pdp.warehouse.entity.Product;
import uz.pdp.warehouse.payload.ProductDto;
import uz.pdp.warehouse.repository.AttachmentRepository;
import uz.pdp.warehouse.repository.CategoryRepository;
import uz.pdp.warehouse.repository.MeasurementRepository;
import uz.pdp.warehouse.repository.ProductRepository;

import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    MeasurementRepository measurementRepository;

    @Autowired
    AttachmentRepository attachmentRepository;

    public Result post(ProductDto productDto) {

        Optional<Attachment> optionalAttachment = attachmentRepository.findById(productDto.getAttachmentId());
        if (!optionalAttachment.isPresent())
            return new Result("attachment not found", false);

        Optional<Category> optionalCategory = categoryRepository.findById(productDto.getCategoryId());
        if (!optionalCategory.isPresent())
            return new Result("category not found", false);

        Optional<Measurement> optionalMeasurement = measurementRepository.findById(productDto.getMeasurementId());
        if (!optionalMeasurement.isPresent())
            return new Result("measurement not found", false);

        Product product = new Product();
        Optional<Product> optionalProduct = productRepository.findByName(productDto.getName());
        if (!optionalProduct.isPresent()) {
            product.setName(productDto.getName());
            product.setAttachment(optionalAttachment.get());
            product.setCategory(optionalCategory.get());
            product.setMeasurement(optionalMeasurement.get());
            productRepository.save(product);
            productRepository.save(product);
            return new Result("successfully added", true);
        }

        if (optionalProduct.get().isActive())
            return new Result("already exist product", false);

        product.setId(product.getId());
        product.setName(productDto.getName());
        product.setActive(true);
        product.setAttachment(optionalAttachment.get());
        product.setCategory(optionalCategory.get());
        product.setMeasurement(optionalMeasurement.get());
        productRepository.save(product);
        return new Result("successfully added", true);

    }

    public Page<Product> getAll(Pageable pageable) {

        return productRepository.findAllByActiveTrue(pageable);

    }

    public Result findOne(Integer id) {

        Optional<Product> byIdAndActiveTrue = productRepository.findByIdAndActiveTrue(id);
        if (!byIdAndActiveTrue.isPresent())
            return new Result("product doesnt exist", false);

        return new Result("success", true, byIdAndActiveTrue.get());

    }

    public Result delete(Integer id) {

        Optional<Product> byIdAndActiveTrue = productRepository.findByIdAndActiveTrue(id);
        if (byIdAndActiveTrue.isPresent()){
            Product product = byIdAndActiveTrue.get();
            product.setActive(false);
            productRepository.save(product);
            return new Result("successfully deleted", true);
        }

        return new Result("product not found", false);

    }

    public Result edit(Integer id, ProductDto productDto) {

        Optional<Product> byIdAndActiveTrue = productRepository.findByIdAndActiveTrue(id);
        if (!byIdAndActiveTrue.isPresent())
            return new Result("product not found", false);

        Optional<Attachment> optionalAttachment = attachmentRepository.findById(productDto.getAttachmentId());
        if (!optionalAttachment.isPresent())
            return new Result("attachment not found", false);

        Optional<Category> optionalCategory = categoryRepository.findById(productDto.getCategoryId());
        if (!optionalCategory.isPresent())
            return new Result("category not found", false);

        Optional<Measurement> optionalMeasurement = measurementRepository.findById(productDto.getMeasurementId());
        if (!optionalMeasurement.isPresent())
            return new Result("measurement not found", false);

        Optional<Product> byIdAndActiveFalse = productRepository.findByNameAndActiveFalse(productDto.getName());
        byIdAndActiveFalse.ifPresent(product -> productRepository.deleteById(product.getId()));

        Product product = new Product();
        product.setId(id);
        product.setName(productDto.getName());
        product.setAttachment(optionalAttachment.get());
        product.setCategory(optionalCategory.get());
        product.setMeasurement(optionalMeasurement.get());
        productRepository.save(product);
        return new Result("successfully edited", true);
    }

    public void deleteByCategoryId(Integer id){
        productRepository.deleteByCategoryQuery(id);
    }
}
