package com.pragma.powerup.application.mapper.response;

import com.pragma.powerup.application.dto.response.OrderDishListResponseDto;
import com.pragma.powerup.application.dto.response.OrderPageResponseDto;
import com.pragma.powerup.application.dto.response.RestaurantsPageResponseDto;
import com.pragma.powerup.application.dto.response.StatusOrderResponseDto;
import com.pragma.powerup.domain.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IOrderResponseMapper {

    @Mapping(source = "order.id", target = "id")
    @Mapping(source = "restaurantsPageResponseDto", target = "restaurant")
    @Mapping(source = "statusOrderResponseDto", target = "status")
    @Mapping(source = "orderDishListResponseDtos", target = "orderDishes")
    OrderPageResponseDto toOrderPageDto(
            Order order,
            RestaurantsPageResponseDto restaurantsPageResponseDto,
            StatusOrderResponseDto statusOrderResponseDto,
            List<OrderDishListResponseDto> orderDishListResponseDtos
    );
}
