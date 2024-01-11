package com.pragma.powerup.application.mapper.request;

import com.pragma.powerup.application.dto.request.RegisterDishDto;
import com.pragma.powerup.domain.model.Dish;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IDishRequestMapper {

    Dish toDish(RegisterDishDto registerDishDto);
}
