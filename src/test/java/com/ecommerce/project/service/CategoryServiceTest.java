package com.ecommerce.project.service;

import com.ecommerce.project.entity.Category;
import com.ecommerce.project.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllCategories() {
        Category category1 = new Category("Electronics");
        Category category2 = new Category("Books");
        when(categoryRepository.findAll()).thenReturn(Arrays.asList(category1, category2));

        List<Category> categories = categoryService.getAllCategories();
        assertEquals(2, categories.size());
    }

    @Test
    public void testCreateCategory() {
        Category category = new Category("New Category");
        when(categoryRepository.save(category)).thenReturn(category);

        Category createdCategory = categoryService.createCategory(category);
        assertNotNull(createdCategory);
        assertEquals("New Category", createdCategory.getName());
    }

    @Test
    public void testGetCategoryByIdFound() {
        Category category = new Category("Existing Category");
        category.setId(1L);
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        Category foundCategory = categoryService.getCategoryById(1L);
        assertEquals("Existing Category", foundCategory.getName());
    }

    @Test
    public void testGetCategoryByIdNotFound() {
        when(categoryRepository.findById(2L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            categoryService.getCategoryById(2L);
        });
        assertEquals("Category not found", exception.getMessage());
    }

    @Test
    public void testUpdateCategory() {
        Category existingCategory = new Category("Old Name");
        existingCategory.setId(1L);
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(existingCategory));
        when(categoryRepository.save(existingCategory)).thenReturn(existingCategory);

        Category updatedCategory = new Category("New Name");
        Category result = categoryService.updateCategory(1L, updatedCategory);
        assertEquals("New Name", result.getName());
    }

    @Test
    public void testDeleteCategory() {
        Category category = new Category("To be deleted");
        category.setId(1L);
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        categoryService.deleteCategory(1L);
        verify(categoryRepository, times(1)).delete(category);
    }
}
