package com.pragma.powerup.infrastructure.out.jpa.adapter;

import com.pragma.powerup.domain.model.Category;
import com.pragma.powerup.domain.spi.ICategoryPersistencePort;
import com.pragma.powerup.infrastructure.out.jpa.mapper.ICategoryEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.ICategoryRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CategoryJpaAdapter implements ICategoryPersistencePort {

    private final ICategoryRepository categoryRepository;
    private final ICategoryEntityMapper categoryEntityMapper;
    @Override
    public Category saveCategory(Category category) {
        if(categoryRepository.findByName(category.getName()).isPresent()){
            return categoryEntityMapper.toCategory(categoryRepository.getByName(category.getName()));
        }
        return categoryEntityMapper.toCategory(
                categoryRepository.save(
                        categoryEntityMapper.toEntity(category)
                )
        );
    }

    @Override
    public Category getCategory(int id) {
        return categoryEntityMapper.toCategory(categoryRepository.getReferenceById(id));
    }
}
