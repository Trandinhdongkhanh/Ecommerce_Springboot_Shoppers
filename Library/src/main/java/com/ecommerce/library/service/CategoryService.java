package com.ecommerce.library.service;

import com.ecommerce.library.entity.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();
    void save(Category category);
    Category findById(Long id);
    void update(Category category);
    void enabledById(Long id);
    void deleteById(Long id);
    List<Category> findAllByActivated();
    void deletePermanently(Long id);
}
