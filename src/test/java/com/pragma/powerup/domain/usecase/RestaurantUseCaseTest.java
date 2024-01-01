package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.exception.ParameterNotValidException;
import com.pragma.powerup.domain.model.Restaurant;
import com.pragma.powerup.domain.spi.IRestaurantPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestaurantUseCaseTest {

    private Restaurant restaurant;

    @Mock
    private IRestaurantPersistencePort restaurantPersistencePort;

    @InjectMocks
    private RestaurantUseCase restaurantUseCase;
    @BeforeEach
    void setUp() {
        restaurant = new Restaurant();
    }

    @Test
    void saveRestaurantSuccess() {
        restaurant.setName("D1");
        restaurant.setNit("123456795");
        restaurant.setAddress("Cra 13 sur #54-678");
        restaurant.setPhone("+573226749122");
        restaurant.setLogoUrl("https://logo.com");
        restaurant.setIdOwner(1);

        when(restaurantPersistencePort.saveRestaurant(restaurant)).thenReturn(true);

        restaurantUseCase.saveRestaurant(restaurant);

        verify(restaurantPersistencePort, times(1)).saveRestaurant(restaurant);
    }

    @Test
    void saveRestaurantFail() {
        restaurant.setName("2134");
        restaurant.setNit("12345679A");
        restaurant.setAddress("Cra 13 sur #54-678");
        restaurant.setPhone("3226749122");
        restaurant.setLogoUrl("https://logo.com");
        restaurant.setIdOwner(1);

        ParameterNotValidException exception = assertThrows(ParameterNotValidException.class, () -> {
            restaurantUseCase.saveRestaurant(restaurant);
        }, "No exception was made");

        assertNull(exception.getMessage());
    }
}