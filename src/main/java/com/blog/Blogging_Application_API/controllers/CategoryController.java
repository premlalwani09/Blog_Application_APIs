package com.blog.Blogging_Application_API.controllers;

import com.blog.Blogging_Application_API.payloads.ApiResponse;
import com.blog.Blogging_Application_API.payloads.CategoryDTO;
import com.blog.Blogging_Application_API.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // Get All Catgory
    @GetMapping("/")
    public ResponseEntity<List<CategoryDTO>> getAllCategories(){

        List<CategoryDTO> categories = this.categoryService.getAllCategories();

        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    // Create Category
    @PostMapping("/")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO){

        CategoryDTO createCategory = this.categoryService.createCategory(categoryDTO);

        return new ResponseEntity<CategoryDTO>(createCategory, HttpStatus.CREATED);
    }

    // Update Category
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategoryById(@Valid @RequestBody CategoryDTO categoryDTO, @PathVariable Integer categoryId){

        CategoryDTO updatedCategory = this.categoryService.updateCategory(categoryDTO, categoryId);

        return new ResponseEntity<CategoryDTO>(updatedCategory, HttpStatus.OK);
    }

    // Get Category By Id
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Integer categoryId){

        CategoryDTO categoryDTOById = this.categoryService.getCategoryById(categoryId);

        return new ResponseEntity<CategoryDTO>(categoryDTOById, HttpStatus.OK);
    }

    // Delete Category By Id
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer categoryId){

        this.categoryService.deleteCategory(categoryId);

        return new ResponseEntity<ApiResponse>(new ApiResponse("Category is Deleted Successfully!", true), HttpStatus.OK);
    }
}
