package com.ecommerce.library.service.impl;

import com.ecommerce.library.entity.Category;
import com.ecommerce.library.repository.CategoryRepo;
import com.ecommerce.library.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepo cateRepo;

    @Override
    public List<Category> findAll() {
        return cateRepo.findAll();
    }

    @Override
    public void save(Category category) {
        category.setIs_deleted(false);
        category.setIs_activated(true);
        cateRepo.save(category);
    }

    @Override
    public Category findById(Long id) {
        return cateRepo.findById(id).get();
    }

    @Override
    public void update(Category category) {
        Category categoryUpdate = null;
        try {
            categoryUpdate= cateRepo.findById(category.getId()).get();
            categoryUpdate.setName(category.getName());
            categoryUpdate.setIs_activated(category.getIs_activated());
            categoryUpdate.setIs_deleted(category.getIs_deleted());
        }catch (Exception e){
            e.printStackTrace();
        }
        cateRepo.save(categoryUpdate);
    }

    @Override
    public void deleteById(Long id) {
        Category category = cateRepo.getById(id);
        category.setIs_deleted(true);
        category.setIs_activated(false);
        cateRepo.save(category);
    }

    @Override
    public List<Category> findAllByActivated() {
        return cateRepo.findAllByIs_activated();
    }

    @Override
    public void deletePermanently(Long id) {
        cateRepo.deleteById(id);
    }

    @Override
    public void enabledById(Long id) {
        Category category = cateRepo.getById(id);
        category.setIs_activated(true);
        category.setIs_deleted(false);
        cateRepo.save(category);
    }

//    @Override
//    public List<Category> findAllByActivated() {
//        return cateRepo.findAllByActivated();
//    }
//
//    @Override
//    public List<CategoryDto> getCategoryAndProduct() {
//        return cateRepo.getCategoryAndProduct();
//    }
}
