package com.ecommerce.library.service.impl;

import com.ecommerce.library.entity.Category;
import com.ecommerce.library.exception.CategoryNotFoundException;
import com.ecommerce.library.repository.CategoryRepo;
import com.ecommerce.library.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public List<Category> findAll() {
        return categoryRepo.findAll();
    }

    @Override
    public void save(Category category) {
        try {
            category.setIs_deleted(false);
            category.setIs_activated(true);
            categoryRepo.save(category);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Category findById(Long id) {
        return categoryRepo.findById(id).orElseThrow(() -> new CategoryNotFoundException("Can't find category"));
    }

    @Override
    public void update(Category category) {
        Category categoryUpdate = null;
        try {
            categoryUpdate = categoryRepo.findById(category.getId()).get();
            categoryUpdate.setName(category.getName());
            categoryUpdate.setIs_activated(category.getIs_activated());
            categoryUpdate.setIs_deleted(category.getIs_deleted());
            categoryRepo.save(categoryUpdate);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void enabledById(Long id) {
        try {
            Optional<Category> category = categoryRepo.findById(id);
            if (category.isPresent()) {
                category.get().setIs_deleted(false);
                category.get().setIs_activated(true);
                categoryRepo.save(category.get());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void deleteById(Long id) {
        try {
            Optional<Category> category = categoryRepo.findById(id);
            if (category.isPresent()) {
                category.get().setIs_deleted(true);
                category.get().setIs_activated(false);
                categoryRepo.save(category.get());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}