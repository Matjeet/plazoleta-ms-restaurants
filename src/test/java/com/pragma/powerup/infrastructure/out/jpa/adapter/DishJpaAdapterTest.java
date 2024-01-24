package com.pragma.powerup.infrastructure.out.jpa.adapter;

import com.pragma.powerup.domain.model.Dish;
import com.pragma.powerup.domain.model.Restaurant;
import com.pragma.powerup.infrastructure.exception.DifferentOwnerException;
import com.pragma.powerup.infrastructure.exception.DishAlreadyExistException;
import com.pragma.powerup.infrastructure.exception.DishNotFoundException;
import com.pragma.powerup.infrastructure.out.jpa.entity.CategoryEntity;
import com.pragma.powerup.infrastructure.out.jpa.entity.DishEntity;
import com.pragma.powerup.infrastructure.out.jpa.entity.RestaurantEntity;
import com.pragma.powerup.infrastructure.out.jpa.entity.StatusEntity;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DishJpaAdapterTest {

    private DishEntity dishEntity;
    private CategoryEntity categoryEntity;
    private StatusEntity statusEntity;
    private RestaurantEntity restaurantEntity;
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
        categoryEntity = new CategoryEntity();
        statusEntity = new StatusEntity();
        restaurantEntity = new RestaurantEntity();
        dish = new Dish();

        dishEntity.setId(1);
        dishEntity.setName("");
        dishEntity.setPrice(1);
        dishEntity.setDescription("");
        dishEntity.setUrlImage("");
        dishEntity.setIdRestaurant(1);
        dishEntity.setCategory(categoryEntity);
        dishEntity.setStatus(statusEntity);

        categoryEntity.setId(1);
        categoryEntity.setName("");

        statusEntity.setId(1);
        statusEntity.setName("");

        restaurantEntity.setId(1);
        restaurantEntity.setName("");
        restaurantEntity.setNit("");
        restaurantEntity.setAddress("");
        restaurantEntity.setPhone("");
        restaurantEntity.setLogoUrl("");
        restaurantEntity.setIdOwner(1);

        dish.setId(1);
        dish.setName("");
        dish.setPrice(1);
        dish.setDescription("");
        dish.setUrlImage("");
        dish.setIdCategory(1);
        dish.setIdRestaurant(1);
        dish.setIdStatus(1);

    }

    @Test
    void saveExistingDish() {

        Optional<DishEntity> optionalMock = Optional.of(dishEntity);

        when(dishRepository.findByNameAndPrice(anyString(), anyInt())).thenReturn(optionalMock);

        DishAlreadyExistException exception = assertThrows(DishAlreadyExistException.class, () ->{
            dishJpaAdapter.saveDish(dish);
        }, "No exception was made");

        verify(dishRepository, times(1)).findByNameAndPrice(anyString(), anyInt());
        assertNull(exception.getMessage());

    }

    @Test
    void saveNonExistingDish(){

        Optional<DishEntity> optionalMock = Optional.empty();

        when(dishRepository.findByNameAndPrice(anyString(), anyInt())).thenReturn(optionalMock);
        when(categoryRepository.getById(anyInt())).thenReturn(categoryEntity);
        when(statusRepository.getReferenceById(anyInt())).thenReturn(statusEntity);
        when(dishEntityMapper.toEntity(dish,categoryEntity, statusEntity)).thenReturn(dishEntity);
        when(dishRepository.save(dishEntity)).thenReturn(dishEntity);

        dishJpaAdapter.saveDish(dish);

        verify(categoryRepository, times(1)).getById(anyInt());
        verify(statusRepository, times(1)).getReferenceById(anyInt());
        verify(dishEntityMapper, times(1)).toEntity(dish, categoryEntity, statusEntity);
        verify(dishRepository, times(1)).save(dishEntity);
    }

    @Test
    void updateDish() {

        when(categoryRepository.getById(anyInt())).thenReturn(categoryEntity);
        when(statusRepository.getReferenceById(anyInt())).thenReturn(statusEntity);
        when(dishEntityMapper.toEntity(dish,categoryEntity, statusEntity)).thenReturn(dishEntity);
        when(dishRepository.save(dishEntity)).thenReturn(dishEntity);

        dishJpaAdapter.updateDish(dish);

        verify(categoryRepository, times(1)).getById(anyInt());
        verify(statusRepository, times(1)).getReferenceById(anyInt());
        verify(dishEntityMapper, times(1)).toEntity(dish, categoryEntity, statusEntity);
        verify(dishRepository, times(1)).save(dishEntity);

    }

    @Test
    void getExistingDish() {
        Optional<DishEntity> optionalMock = Optional.of(dishEntity);

        when(dishRepository.findById(anyInt())).thenReturn(optionalMock);
        when(dishRepository.getReferenceById(anyInt())).thenReturn(dishEntity);
        when(dishEntityMapper.toDish(dishEntity)).thenReturn(dish);

        Dish result = dishJpaAdapter.getDish(1);

        verify(dishRepository, times(1)).findById(anyInt());
        verify(dishRepository, times(1)).getReferenceById(anyInt());
        verify(dishEntityMapper, times(1)).toDish(dishEntity);
        assertInstanceOf(Dish.class, result);
    }

    @Test
    void getNonExistingDish(){

        Optional<DishEntity> optionalMock = Optional.empty();

        when(dishRepository.findById(anyInt())).thenReturn(optionalMock);

        DishNotFoundException exception = assertThrows(DishNotFoundException.class, () ->{
            dishJpaAdapter.getDish(1);
        }, "No exception was made");

        assertNull(exception.getMessage());
    }

    @Test
    void changeStatusWithIdOwnerSuccessAndEnableStatus() {

        statusEntity.setId(1);
        statusEntity.setName("habilitado");

        when(dishRepository.getReferenceById(anyInt())).thenReturn(dishEntity);
        when(restaurantRepository.getReferenceById(anyInt())).thenReturn(restaurantEntity);
        when(statusRepository.findByName(anyString())).thenReturn(statusEntity);
        when(dishRepository.save(dishEntity)).thenReturn(dishEntity);

        String result = dishJpaAdapter.changeStatus(1,1);

        assertEquals("deshabilitado", result);

    }

    @Test
    void changeStatusWithIdOwnerSuccessAndDisableStatus(){

        when(dishRepository.getReferenceById(anyInt())).thenReturn(dishEntity);
        when(restaurantRepository.getReferenceById(anyInt())).thenReturn(restaurantEntity);
        when(statusRepository.findByName(anyString())).thenReturn(statusEntity);
        when(dishRepository.save(dishEntity)).thenReturn(dishEntity);

        String result = dishJpaAdapter.changeStatus(1,1);

        assertEquals("habilitado", result);
    }

    @Test
    void changeStatusWithIdOwnerFailed(){

        when(dishRepository.getReferenceById(anyInt())).thenReturn(dishEntity);
        when(restaurantRepository.getReferenceById(anyInt())).thenReturn(restaurantEntity);

        DifferentOwnerException exception = assertThrows(DifferentOwnerException.class, () -> {
            dishJpaAdapter.changeStatus(1,2);
        }, "No exception was made");

        assertNull(exception.getMessage());
    }

    @Test
    void getMenuCategoryEmpty() {

        Pageable pageable = PageRequest.of(1,1);
        Page<DishEntity> dishEntityPage = Page.empty(PageRequest.of(1,1));
        Page<Dish> dishPage = Page.empty(PageRequest.of(1,1));

        when(dishRepository.findAllByIdRestaurant(pageable, 1)).thenReturn(dishEntityPage);

        Page<Dish> result = dishJpaAdapter.getMenu(pageable, 1, "");

        verify(dishRepository, times(1)).findAllByIdRestaurant(pageable,1);
        assertEquals(dishPage, result);

    }

    @Test
    void getMenuNotEmpty(){

        Pageable pageable = PageRequest.of(1,1);
        Page<DishEntity> dishEntityPage = Page.empty(PageRequest.of(1,1));
        Page<Dish> dishPage = Page.empty(PageRequest.of(1,1));

        when(categoryRepository.getByName(anyString())).thenReturn(categoryEntity);
        when(dishRepository.findAllByIdRestaurantAndCategory(pageable, 1, categoryEntity)).thenReturn(dishEntityPage);

        Page<Dish> result = dishJpaAdapter.getMenu(pageable, 1, "a");

        verify(dishRepository, times(1)).findAllByIdRestaurantAndCategory(pageable,1, categoryEntity);
        assertEquals(dishPage, result);

    }
}