package com.pragma.powerup.application.mapper.response;

import com.pragma.powerup.application.dto.response.DishPageResponseDto;
import com.pragma.powerup.application.dto.response.OrderDishListResponseDto;
import com.pragma.powerup.domain.model.OrderDish;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IOrderDishResponseMapper {

    @Mapping(source = "dishPageResponseDto", target = "dish")
    OrderDishListResponseDto toOrderDishDto(OrderDish orderDish, DishPageResponseDto dishPageResponseDto);
}
