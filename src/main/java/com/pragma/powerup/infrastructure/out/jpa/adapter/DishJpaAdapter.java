package com.pragma.powerup.infrastructure.out.jpa.adapter;

import com.pragma.powerup.domain.Constants;
import com.pragma.powerup.domain.model.Dish;
import com.pragma.powerup.domain.spi.IDishPersistencePort;
import com.pragma.powerup.infrastructure.exception.DifferentOwnerException;
import com.pragma.powerup.infrastructure.exception.DishAlreadyExistException;
import com.pragma.powerup.infrastructure.exception.DishNotFoundException;
import com.pragma.powerup.infrastructure.out.jpa.entity.DishEntity;
import com.pragma.powerup.infrastructure.out.jpa.entity.RestaurantEntity;
import com.pragma.powerup.infrastructure.out.jpa.entity.StatusEntity;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IDishEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.ICategoryRepository;
import com.pragma.powerup.infrastructure.out.jpa.repository.IDishRepository;
import com.pragma.powerup.infrastructure.out.jpa.repository.IRestaurantRepository;
import com.pragma.powerup.infrastructure.out.jpa.repository.IStatusRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DishJpaAdapter implements IDishPersistencePort {

    private final IDishRepository dishRepository;
    private final IDishEntityMapper dishEntityMapper;
    private final ICategoryRepository categoryRepository;
    private final IStatusRepository statusRepository;
    private final IRestaurantRepository restaurantRepository;
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

    @Override
    public String changeStatus(int idDish, int idOwner) {
        DishEntity dish = dishRepository.getReferenceById(idDish);
        RestaurantEntity restaurant = restaurantRepository.getReferenceById(dish.getIdRestaurant());
        if (restaurant.getIdOwner() == idOwner){
            if (dish.getStatus().getName().equals(Constants.ENABLE)){
                StatusEntity status = statusRepository.findByName(Constants.DISABLE);
                dish.setStatus(status);
                dishRepository.save(dish);
                return Constants.DISABLE;
            }
            else {
                StatusEntity status = statusRepository.findByName(Constants.ENABLE);
                dish.setStatus(status);
                dishRepository.save(dish);
                return Constants.ENABLE;
            }
        } else{
            throw new DifferentOwnerException();
        }
    }
}
