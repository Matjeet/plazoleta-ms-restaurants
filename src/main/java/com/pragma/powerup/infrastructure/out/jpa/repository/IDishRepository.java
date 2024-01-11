package com.pragma.powerup.infrastructure.out.jpa.repository;

import com.pragma.powerup.infrastructure.out.jpa.entity.CategoryEntity;
import com.pragma.powerup.infrastructure.out.jpa.entity.DishEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IDishRepository extends JpaRepository<DishEntity, Integer> {

    Optional<DishEntity> findByNameAndPrice(String name, int price);

    Optional<DishEntity> findById(int id);

    Page<DishEntity> getByIdRestaurant(int idRestaurant);

    Page<DishEntity> getByIdRestaurantAndCategory(int idRestaurant, CategoryEntity category);
}
