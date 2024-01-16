package com.pragma.powerup.infrastructure.out.jpa.repository;

import com.pragma.powerup.infrastructure.out.jpa.entity.OrderEntity;
import com.pragma.powerup.infrastructure.out.jpa.entity.RestaurantEntity;
import com.pragma.powerup.infrastructure.out.jpa.entity.StatusEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IOrderRepository extends JpaRepository<OrderEntity, Integer> {

    Optional<OrderEntity> findByIdClient(int idClient);

    Page<OrderEntity> findByStatusAndRestaurant(Pageable pageable, StatusEntity statusEntity, RestaurantEntity restaurantEntity);
}
