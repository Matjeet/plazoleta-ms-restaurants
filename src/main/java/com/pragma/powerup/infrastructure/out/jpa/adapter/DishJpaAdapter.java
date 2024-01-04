package com.pragma.powerup.infrastructure.out.jpa.adapter;

import com.pragma.powerup.domain.model.Dish;
import com.pragma.powerup.domain.spi.IDishPersistencePort;
import com.pragma.powerup.infrastructure.exception.DishAlreadyExistException;
import com.pragma.powerup.infrastructure.out.jpa.entity.CategoryEntity;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IDishEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.ICategoryRepository;
import com.pragma.powerup.infrastructure.out.jpa.repository.IDishRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DishJpaAdapter implements IDishPersistencePort {

    private final IDishRepository dishRepository;
    private final IDishEntityMapper dishEntityMapper;
    private final ICategoryRepository categoryRepository;
    @Override
    public void saveDish(Dish dish) {

        if (dishRepository.findByNameAndPrice(dish.getName(), dish.getPrice()).isPresent()) {
            throw new DishAlreadyExistException();
        }
        else {
            dishRepository.save(
                    dishEntityMapper.toEntity(dish, categoryRepository.getById(dish.getIdCategory()))
            );
        }
    }

    @Override
    public void updateDish(Dish dish) {
        
    }

    @Override
    public Dish getDish(int idDish) {
        return null;
    }
}
