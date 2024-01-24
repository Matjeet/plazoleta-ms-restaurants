package com.pragma.powerup.infrastructure.out.jpa.adapter;

import com.pragma.powerup.domain.model.Dish;
import com.pragma.powerup.infrastructure.exception.DishAlreadyExistException;
import com.pragma.powerup.infrastructure.out.jpa.entity.DishEntity;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IDishEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.ICategoryRepository;
import com.pragma.powerup.infrastructure.out.jpa.repository.IDishRepository;
import com.pragma.powerup.infrastructure.out.jpa.repository.IRestaurantRepository;
import com.pragma.powerup.infrastructure.out.jpa.repository.IStatusRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DishJpaAdapterTest {

    private DishEntity dishEntity;
    private Dish dish;

    @Mock
    private IDishRepository dishRepository;
    @Mock
    private IDishEntityMapper dishEntityMapper;
    @Mock
    private ICategoryRepository categoryRepository;
    @Mock
    private IStatusRepository statusRepository;
    @Mock
    private IRestaurantRepository restaurantRepository;

    @InjectMocks
    private DishJpaAdapter dishJpaAdapter;

    @BeforeEach
    void setUp() {
        dishEntity = new DishEntity();
        dish = new Dish();
    }

    @Test
    void saveDish() {

        dishEntity.setId(1);
        dishEntity.setName("");
        dishEntity.setPrice(1);
        dishEntity.setDescription("");
        dishEntity.setUrlImage("");
        dishEntity.setIdRestaurant(1);

        dish.setId(1);
        dish.setName("");
        dish.setPrice(1);
        dish.setDescription("");
        dish.setUrlImage("");
        dish.setIdCategory(1);
        dish.setIdRestaurant(1);
        dish.setIdStatus(1);

        Optional<DishEntity> optionalMock = Optional.of(dishEntity);

        when(dishRepository.findByNameAndPrice(anyString(), anyInt())).thenReturn(optionalMock);

        DishAlreadyExistException exception = assertThrows(DishAlreadyExistException.class, () ->{
            dishJpaAdapter.saveDish(dish);
        }, "No exception was made");

        assertNull(exception.getMessage());

    }

    @Test
    void updateDish() {
    }

    @Test
    void getDish() {
    }

    @Test
    void changeStatus() {
    }

    @Test
    void getMenu() {
    }
}