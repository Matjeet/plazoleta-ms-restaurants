package com.pragma.powerup.infrastructure.out.jpa.mapper;

import com.pragma.powerup.domain.model.OrderDish;
import com.pragma.powerup.infrastructure.out.jpa.entity.DishEntity;
import com.pragma.powerup.infrastructure.out.jpa.entity.OrderDishEntity;
import com.pragma.powerup.infrastructure.out.jpa.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IOrderDishEntityMapper {

    @Mapping(source = "orderDish.id", target = "id")
    @Mapping(source = "dishEntity", target = "dish")
    @Mapping(source = "orderEntity", target = "order")
    OrderDishEntity toEntity(OrderDish orderDish, DishEntity dishEntity, OrderEntity orderEntity);
}
