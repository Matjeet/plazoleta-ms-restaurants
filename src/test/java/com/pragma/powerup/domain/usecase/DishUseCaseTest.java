package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.exception.InvalidPriceException;
import com.pragma.powerup.domain.exception.ParameterNotValidException;
import com.pragma.powerup.domain.model.Dish;
import com.pragma.powerup.domain.model.Restaurant;
import com.pragma.powerup.domain.spi.IDishPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DishUseCaseTest {

    private Dish dish;
    @Mock
    private IDishPersistencePort dishPersistencePort;
    @InjectMocks
    private DishUseCase dishUseCase;

    @BeforeEach
    void setUp() {
        dish = new Dish();
    }

    @Test
    void saveDishSuccess() {
        dish.setName("Pizza");
        dish.setPrice(20000);
        dish.setDescription("Pizza con piña");
        dish.setUrlImage("https://pizzaConPiniaImagen.com");
        dish.setIdCategory(1);
        dish.setIdRestaurant(1);

        doNothing().when(dishPersistencePort).saveDish(dish);

        dishUseCase.saveDish(dish);

        verify(dishPersistencePort, times(1)).saveDish(dish);
    }

    @Test
    void saveDishFailed() {
        dish.setName("Pizza");
        dish.setPrice(-134568);
        dish.setDescription("Pizza con piña");
        dish.setUrlImage("https://pizzaConPiniaImagen.com");
        dish.setIdCategory(1);
        dish.setIdRestaurant(1);

        InvalidPriceException exception = assertThrows(InvalidPriceException.class, () -> {
            dishUseCase.saveDish(dish);
        }, "No exception was made");

        assertNull(exception.getMessage());
    }
}