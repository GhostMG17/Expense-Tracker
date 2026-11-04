package com.example.expensetracker.service;

import com.example.expensetracker.entity.Category;
import com.example.expensetracker.exception.BadRequestException;
import com.example.expensetracker.exception.NotFoundException;
import com.example.expensetracker.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category findById(Long id){
        return categoryRepository.findById(id).
                orElseThrow(()-> new NotFoundException("Category not found with id: " + id));
    }

    public Category create(Category category) {
        if (category.getName() == null || category.getName().isBlank()) {
            throw new BadRequestException("Category name can not be empty");
        }

        if (categoryRepository.existsByNameIgnoreCase(category.getName())){
            throw new BadRequestException("Category with this name already exists");
        }

        Category newCategory = new Category(null, category.getName());
        categoryRepository.save(newCategory);
        return newCategory;

    }
}
