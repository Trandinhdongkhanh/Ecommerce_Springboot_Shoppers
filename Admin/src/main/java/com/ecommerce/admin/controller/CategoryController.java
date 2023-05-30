package com.ecommerce.admin.controller;

import com.ecommerce.library.entity.Category;
import com.ecommerce.library.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categories")
    public String categories(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/api/login";
        }
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("size", categories.size());
        model.addAttribute("title", "Category");
        model.addAttribute("categoryNew", new Category());
        return "categories";
    }

    @PostMapping("/add-category")
    public String add(@ModelAttribute("categoryNew") Category category, RedirectAttributes ra) {
        try {
            categoryService.save(category);
            ra.addFlashAttribute("success", "Added successfully");
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            ra.addFlashAttribute("failed", "Failed to add because duplicate name");
        } catch (Exception e) {
            e.printStackTrace();
            ra.addFlashAttribute("failed", "Error server");
        }
        return "redirect:/api/categories";

    }

    @GetMapping("/delete-category-permanently/{id}")
    public String deletePermanently(@PathVariable("id") Long id, RedirectAttributes ra) {
        try {
            categoryService.deletePermanently(id);
            ra.addFlashAttribute("success", "Delete permanently successfully");
        } catch (Exception ex) {
            ra.addFlashAttribute("failed", "Can't delete permanently");
            ex.printStackTrace();
        }
        return "redirect:/api/categories";
    }

    @RequestMapping(value = "/findById", method = {RequestMethod.PUT, RequestMethod.GET})
    @ResponseBody
    public Category findById(Long id) {
        return categoryService.findById(id);
    }

    @GetMapping("/update-category")
    public String update(Category category, RedirectAttributes ra) {
        try {
            categoryService.update(category);
            ra.addFlashAttribute("success", "Updated successfully");
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            ra.addFlashAttribute("failed", "Failed to update because duplicate name");
        } catch (Exception e) {
            e.printStackTrace();
            ra.addFlashAttribute("failed", "Error server");
        }
        return "redirect:/api/categories";
    }

    @RequestMapping(value = "/delete-category", method = {RequestMethod.PUT, RequestMethod.GET})
    public String delete(Long id, RedirectAttributes ra) {
        try {
            categoryService.deleteById(id);
            ra.addFlashAttribute("success", "Deleted successfully");
        } catch (Exception e) {
            e.printStackTrace();
            ra.addFlashAttribute("failed", "Failed to deleted");
        }
        return "redirect:/api/categories";
    }

    @RequestMapping(value = "/enable-category", method = {RequestMethod.PUT, RequestMethod.GET})
    public String enable(Long id, RedirectAttributes ra) {
        try {
            categoryService.enabledById(id);
            ra.addFlashAttribute("success", "Enabled successfully");
        } catch (Exception e) {
            e.printStackTrace();
            ra.addFlashAttribute("failed", "Failed to enabled");
        }
        return "redirect:/api/categories";
    }
}
