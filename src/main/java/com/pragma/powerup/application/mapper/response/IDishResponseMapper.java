package com.pragma.powerup.application.mapper.response;

import com.pragma.powerup.application.dto.response.DishPageResponseDto;
import com.pragma.powerup.domain.model.Category;
import com.pragma.powerup.domain.model.Dish;
import com.pragma.powerup.domain.model.Status;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IDishResponseMapper {

    @Mapping(source = "category.name", target = "category.name")
    @Mapping(source = "status.name", target = "status.name")
    @Mapping(source = "dish.name", target = "name")
    DishPageResponseDto toDishPageDto(Dish dish, Category category, Status status);
}
