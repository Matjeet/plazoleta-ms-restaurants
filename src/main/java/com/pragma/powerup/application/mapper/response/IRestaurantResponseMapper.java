package com.pragma.powerup.application.mapper.response;

import com.pragma.powerup.application.dto.response.RestaurantsPageResponseDto;
import com.pragma.powerup.domain.model.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IRestaurantResponseMapper {

    Page<RestaurantsPageResponseDto> toRestaurantsPageDto(Page<Restaurant> restaurants);
}
