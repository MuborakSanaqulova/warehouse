package uz.pdp.warehouse.service;

import org.springframework.beans.factory.annotation.Autowired;
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

        Optional<Category> optionalCategory = categoryRepository.findById(categoryDto.getCategoryId());
        if (!optionalCategory.isPresent())
            return new Result("not found", false);

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

        Category category=optionalCategory.get();
        category.setActive(false);
        categoryRepository.save(category);
        return new Result("succesfully deleted", true);
    }
}
