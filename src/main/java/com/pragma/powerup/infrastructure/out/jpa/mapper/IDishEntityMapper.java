package com.pragma.powerup.infrastructure.out.jpa.mapper;

import com.pragma.powerup.domain.model.Dish;
import com.pragma.powerup.infrastructure.out.jpa.entity.CategoryEntity;
import com.pragma.powerup.infrastructure.out.jpa.entity.DishEntity;
import com.pragma.powerup.infrastructure.out.jpa.entity.StatusEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IDishEntityMapper {

    @Mapping(source = "dish.id", target = "id")
    @Mapping(source = "dish.name", target = "name")
    DishEntity toEntity(Dish dish, CategoryEntity category, StatusEntity status);

    @Mapping(source = "dishEntity.category.id", target = "idCategory")
    @Mapping(source = "dishEntity.status.id", target = "idStatus")
    Dish toDish(DishEntity dishEntity);
}
