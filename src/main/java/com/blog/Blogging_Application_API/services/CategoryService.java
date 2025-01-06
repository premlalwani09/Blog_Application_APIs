package com.blog.Blogging_Application_API.services;

import com.blog.Blogging_Application_API.payloads.CategoryDTO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CategoryService {

    // get All
    public List<CategoryDTO> getAllCategories();

    // create
    public CategoryDTO createCategory(CategoryDTO categoryDTO);

    // update
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Integer categoryId);

    // get By Id
    public CategoryDTO getCategoryById(Integer categoryId);

    // delete
    public void deleteCategory(Integer categoryId);


}
