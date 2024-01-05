package com.pragma.powerup.infrastructure.out.jpa.adapter;

import com.pragma.powerup.domain.model.Dish;
import com.pragma.powerup.domain.spi.IDishPersistencePort;
import com.pragma.powerup.infrastructure.exception.DishAlreadyExistException;
import com.pragma.powerup.infrastructure.exception.DishNotFoundException;
import com.pragma.powerup.infrastructure.out.jpa.entity.CategoryEntity;
import com.pragma.powerup.infrastructure.out.jpa.entity.DishEntity;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IDishEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.ICategoryRepository;
import com.pragma.powerup.infrastructure.out.jpa.repository.IDishRepository;
import com.pragma.powerup.infrastructure.out.jpa.repository.IStatusRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DishJpaAdapter implements IDishPersistencePort {

    private final IDishRepository dishRepository;
    private final IDishEntityMapper dishEntityMapper;
    private final ICategoryRepository categoryRepository;
    private final IStatusRepository statusRepository;
    @Override
    public void saveDish(Dish dish) {

        if (dishRepository.findByNameAndPrice(dish.getName(), dish.getPrice()).isPresent()) {
            throw new DishAlreadyExistException();
        }
        else {
            dishRepository.save(
                    dishEntityMapper.toEntity(
                            dish,
                            categoryRepository.getById(dish.getIdCategory()),
                            statusRepository.getReferenceById(dish.getIdStatus())
                    )
            );
        }
    }

    @Override
    public void updateDish(Dish dish) {
        dishRepository.save(
                dishEntityMapper.toEntity(
                        dish,
                        categoryRepository.getById(dish.getIdCategory()),
                        statusRepository.getReferenceById(dish.getIdStatus())
                )
        );
    }

    @Override
    public Dish getDish(int idDish) {

        if(dishRepository.findById(idDish).isPresent()){
            return dishEntityMapper.toDish(dishRepository.getReferenceById(idDish));
        }
        else {
            throw new DishNotFoundException();
        }
    }
}
