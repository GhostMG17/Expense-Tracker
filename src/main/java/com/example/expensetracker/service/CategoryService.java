package com.example.expensetracker.service;

import com.example.expensetracker.entity.Category;
import com.example.expensetracker.exception.BadRequestException;
import com.example.expensetracker.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {
    private List<Category> categories = new ArrayList<>();

    public List<Category> getAllCategories() {
        return categories;
    }

    public Category findById(Long id){
        for (Category category : categories){
            if (category.getId().equals(id)){
                return category;
            }
        }
        throw new NotFoundException("Category not found with id: " + id);
    }

    public Category create(Category category) {
        if (category.getName() == null || category.getName().isBlank()) {
            throw new BadRequestException("Category name can not be empty");
        }

        boolean nameExists = categories.stream()
                .anyMatch(c -> category.getName().equalsIgnoreCase(c.getName()));

        if (nameExists) {
            throw new BadRequestException("Category with this name already exists");
        }

        Category newCategory = new Category(categories.size() + 1L, category.getName());
        categories.add(newCategory);
        return newCategory;

    }
}
