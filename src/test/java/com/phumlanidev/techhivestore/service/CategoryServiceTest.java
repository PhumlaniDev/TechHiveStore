package com.phumlanidev.techhivestore.service;

import com.phumlanidev.techhivestore.dto.CategoryDto;
import com.phumlanidev.techhivestore.mapper.CategoryMapper;
import com.phumlanidev.techhivestore.model.Category;
import com.phumlanidev.techhivestore.model.Users;
import com.phumlanidev.techhivestore.repository.CategoryRepository;
import com.phumlanidev.techhivestore.repository.UsersRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

@ActiveProfiles("test")
class CategoryServiceTest {

        @Mock
        private CategoryRepository categoryRepository;

        @Mock
        private CategoryMapper categoryMapper;

        @Mock
        private UsersRepository usersRepository;

        @Mock
        private SecurityContext securityContext;

        @Mock
        private Authentication authentication;

        @InjectMocks
        private CategoryService categoryService;

        private AutoCloseable autoCloseable;

        @BeforeEach
        void setUp() {
            autoCloseable = openMocks(this);
            SecurityContextHolder.setContext(securityContext);
            when(securityContext.getAuthentication()).thenReturn(authentication);
            when(authentication.getName()).thenReturn("admin");
        }

        @AfterEach
        void tearDown() throws Exception {
            autoCloseable.close();
        }

//        @Test
//        void saveCategorySuccessfully() {
//            Category category = new Category();
//            when(categoryRepository.save(category)).thenReturn(category);
//
//            Category result = categoryService.saveCategory(category);
//
//            assertEquals(category, result);
//            verify(categoryRepository, times(1)).save(category);
//        }
//
//        @Test
//        void createCategorySuccessfully() {
//            Category category = new Category();
//            category.setCategoryName("Electronics");
//            Users admin = new Users();
//            when(usersRepository.findByUsername("admin")).thenReturn(admin);
//            when(categoryRepository.existsByCategoryName("Electronics")).thenReturn(false);
//            when(categoryRepository.save(category)).thenReturn(category);
//            when(categoryMapper.toDTO(category)).thenReturn(new CategoryDto());
//
//            CategoryDto result = categoryService.createCategory(category);
//
//            assertNotNull(result);
//            verify(categoryRepository, times(1)).save(category);
//        }
//
//        @Test
//        void createCategoryWithNullNameThrowsException() {
//            Category category = new Category();
//
//            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
//                categoryService.createCategory(category);
//            });
//
//            assertEquals("Category name cannot be null or empty", exception.getMessage());
//        }
//
//        @Test
//        void createCategoryThatAlreadyExistsThrowsException() {
//            Category category = new Category();
//            category.setCategoryName("Electronics");
//            when(categoryRepository.existsByCategoryName("Electronics")).thenReturn(true);
//
//            RuntimeException exception = assertThrows(RuntimeException.class, () -> {
//                categoryService.createCategory(category);
//            });
//
//            assertEquals("Category already exists", exception.getMessage());
//        }
//
//        @Test
//        void updateCategorySuccessfully() {
//            Category category = new Category();
//            Users admin = new Users();
//            when(usersRepository.findByUsername("admin")).thenReturn(admin);
//            when(categoryRepository.save(category)).thenReturn(category);
//            when(categoryMapper.toDTO(category)).thenReturn(new CategoryDto());
//
//            CategoryDto result = categoryService.updateCategory(category);
//
//            assertNotNull(result);
//            verify(categoryRepository, times(1)).save(category);
//        }
//
//        @Test
//        void findAllCategoriesSuccessfully() {
//            when(categoryRepository.findAll()).thenReturn(Collections.emptyList());
//
//            List<CategoryDto> result = categoryService.findAllCategories();
//
//            assertNotNull(result);
//            assertTrue(result.isEmpty());
//            verify(categoryRepository, times(1)).findAll();
//        }
//
//        @Test
//        void findCategoryByIdSuccessfully() {
//            Category category = new Category();
//            when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
//            when(categoryMapper.toDTO(category)).thenReturn(new CategoryDto());
//
//            CategoryDto result = categoryService.findCategoryById(1L);
//
//            assertNotNull(result);
//            verify(categoryRepository, times(1)).findById(1L);
//        }
//
//        @Test
//        void findCategoryByIdNotFoundThrowsException() {
//            when(categoryRepository.findById(1L)).thenReturn(Optional.empty());
//
//            RuntimeException exception = assertThrows(RuntimeException.class, () -> {
//                categoryService.findCategoryById(1L);
//            });
//
//            assertEquals("Category not found", exception.getMessage());
//        }
//
//        @Test
//        void deleteCategorySuccessfully() {
//            Category category = new Category();
//            when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
//
//            categoryService.deleteCategory(1L);
//
//            verify(categoryRepository, times(1)).delete(category);
//        }
//
//        @Test
//        void deleteCategoryNotFoundThrowsException() {
//            when(categoryRepository.findById(1L)).thenReturn(Optional.empty());
//
//            RuntimeException exception = assertThrows(RuntimeException.class, () -> {
//                categoryService.deleteCategory(1L);
//            });
//
//            assertEquals("Category not found", exception.getMessage());
//        }
}