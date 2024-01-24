package com.pragma.powerup.infrastructure.out.jpa.adapter;

import com.pragma.powerup.domain.model.Category;
import com.pragma.powerup.infrastructure.out.jpa.entity.CategoryEntity;
import com.pragma.powerup.infrastructure.out.jpa.mapper.ICategoryEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.ICategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryJpaAdapterTest {

    private CategoryEntity categoryEntity;
    private Category category;

    @Mock
    private ICategoryRepository categoryRepository;
    @Mock
    private ICategoryEntityMapper categoryEntityMapper;

    @InjectMocks
    private CategoryJpaAdapter categoryJpaAdapter;

    @BeforeEach
    void setUp() {
        categoryEntity = new CategoryEntity();
        category = new Category();

        categoryEntity.setId(1);
        categoryEntity.setName("");

        category.setId(1);
        category.setName("");
    }

    @Test
    void saveExistingCategory() {

        Optional<CategoryEntity> optionalMock = Optional.of(categoryEntity);

        when(categoryRepository.findByName(anyString())).thenReturn(optionalMock);
        when(categoryRepository.getByName(anyString())).thenReturn(categoryEntity);
        when(categoryEntityMapper.toCategory(categoryEntity)).thenReturn(category);

        Category result = categoryJpaAdapter.saveCategory(category);

        verify(categoryRepository, times(1)).findByName(anyString());
        verify(categoryRepository, times(1)).getByName(anyString());
        verify(categoryEntityMapper, times(1)).toCategory(categoryEntity);
        assertInstanceOf(Category.class, result);
    }

    @Test
    void saveNonExistingCategory(){

        Optional<CategoryEntity> optionalMock = Optional.empty();

        when(categoryRepository.findByName(anyString())).thenReturn(optionalMock);
        when(categoryEntityMapper.toEntity(category)).thenReturn(categoryEntity);
        when(categoryRepository.save(categoryEntity)).thenReturn(categoryEntity);
        when(categoryEntityMapper.toCategory(categoryEntity)).thenReturn(category);

        Category result = categoryJpaAdapter.saveCategory(category);

        verify(categoryRepository, times(1)).findByName(anyString());
        verify(categoryEntityMapper, times(1)).toEntity(category);
        verify(categoryRepository, times(1)).save(categoryEntity);
        verify(categoryEntityMapper, times(1)).toCategory(categoryEntity);
        assertInstanceOf(Category.class, result);



    }
}