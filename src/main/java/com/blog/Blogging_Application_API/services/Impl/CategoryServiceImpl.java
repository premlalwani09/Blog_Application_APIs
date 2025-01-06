package com.blog.Blogging_Application_API.services.Impl;

import com.blog.Blogging_Application_API.entities.Category;
import com.blog.Blogging_Application_API.exceptions.ResourceNotfoundException;
import com.blog.Blogging_Application_API.payloads.CategoryDTO;
import com.blog.Blogging_Application_API.repositories.CategoryRepo;
import com.blog.Blogging_Application_API.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<CategoryDTO> getAllCategories() {

        List<Category> categories = this.categoryRepo.findAll();

        List<CategoryDTO> categoryDTOS = categories.stream().map((cat) -> this.modelMapper.map(cat, CategoryDTO.class)).collect(Collectors.toList());

        return categoryDTOS;
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {

        Category cat = this.modelMapper.map(categoryDTO, Category.class);

        Category addedCat = this.categoryRepo.save(cat);

        return this.modelMapper.map(addedCat, CategoryDTO.class);
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Integer categoryId) {

        Category cat = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotfoundException("Category", "Category Id", categoryId));

        cat.setCategoryTitle(categoryDTO.getCategoryTitle());
        cat.setCategoryDescription(categoryDTO.getCategoryDescription());

        Category updateCat = this.categoryRepo.save(cat);

        return this.modelMapper.map(updateCat, CategoryDTO.class);
    }

    @Override
    public CategoryDTO getCategoryById(Integer categoryId) {

        Category cat = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotfoundException("Category", "Category Id", categoryId));

        return this.modelMapper.map(cat, CategoryDTO.class);
    }

    @Override
    public void deleteCategory(Integer categoryId) {

        Category cat = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotfoundException("Category", "Category Id", categoryId));

        this.categoryRepo.delete(cat);

    }
}
