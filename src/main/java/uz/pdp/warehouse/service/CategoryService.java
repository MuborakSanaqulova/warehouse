package uz.pdp.warehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.pdp.warehouse.common.Result;
import uz.pdp.warehouse.entity.Category;
import uz.pdp.warehouse.payload.CategoryDto;
import uz.pdp.warehouse.repository.CategoryRepository;

import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProductService productService;


    public Result post(CategoryDto categoryDto) {
        Optional<Category> categoryOptional = categoryRepository.findByName(categoryDto.getName());
        Category category = new Category();
        if (categoryOptional.isPresent()) {
            return new Result("Already exist", false);
        }

        if (categoryDto.getCategoryId() == null) {
            category.setName(categoryDto.getName());
            categoryRepository.save(category);
            return new Result("successfully post", true);
        }

        Optional<Category> optionalCategory = categoryRepository.findByIdAndActiveTrue(categoryDto.getCategoryId());
        if (!optionalCategory.isPresent())
            return new Result("parent category not found", false);

        if (categoryDto.getName().equals(optionalCategory.get().getName()))
            return new Result("this category cannot be it's own category", false);

        category.setName(categoryDto.getName());
        category.setParentCategory(optionalCategory.get());
        categoryRepository.save(category);
        return new Result("successfully saved", true);
    }

    public Result delete(Integer id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (!optionalCategory.isPresent())
            return new Result("not found", false);

        productService.deleteByCategoryId(id);

        Category category=optionalCategory.get();
        category.setActive(false);
        categoryRepository.save(category);
        return new Result("succesfully deleted", true);
    }

    public Page<Category> getAll(Pageable pageable) {
       return categoryRepository.findAllByActiveTrue(pageable);
    }

    public Result findOne(Integer id) {

        Optional<Category> byIdAndActiveTrue = categoryRepository.findByIdAndActiveTrue(id);
        if (byIdAndActiveTrue.isPresent())
            return new Result("success", true, byIdAndActiveTrue.get());

        return new Result("category not found", false);
    }

    public Result edit(Integer id, CategoryDto categoryDto) {

        Optional<Category> optionalCategory = categoryRepository.findByIdAndActiveTrue(id);
        if (!optionalCategory.isPresent()) {
            return new Result("Category does not exist", false);
        }
        Optional<Category> byName = categoryRepository.findByNameAndActiveFalse(categoryDto.getName());
        byName.ifPresent(value -> categoryRepository.delete(value));


        Optional<Category> categoryOptional = categoryRepository.findByNameAndActiveTrue(categoryDto.getName());

        if (categoryOptional.isPresent() && !id.equals(categoryOptional.get().getId()))
            return new Result("Already exist", false);

        if (categoryDto.getCategoryId() == null) {
            Category category = new Category();
            category.setId(id);
            category.setName(categoryDto.getName());
            categoryRepository.save(category);
            return new Result("Successfully saved", true);
        }

        Optional<Category> parentCategory = categoryRepository.findByIdAndActiveTrue(categoryDto.getCategoryId());

        if (!parentCategory.isPresent())
            return new Result("Category does not exist with id: " + categoryDto.getCategoryId(), false);

        if (categoryDto.getName().equals(parentCategory.get().getName()))
            return new Result("parent category can not be equal with this", false);

        Category category = new Category();
        category.setId(id);
        category.setName(categoryDto.getName());
        category.setParentCategory(parentCategory.get());

        categoryRepository.save(category);
        return new Result("Successfully saved", true);
    }
}
