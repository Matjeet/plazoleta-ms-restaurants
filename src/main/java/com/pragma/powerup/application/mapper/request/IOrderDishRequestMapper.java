package com.pragma.powerup.application.mapper.request;

import com.pragma.powerup.application.dto.request.RegisterOrderDishRequestDto;
import com.pragma.powerup.domain.model.OrderDish;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IOrderDishRequestMapper {

    @Mapping(source = "idOrder", target = "idOrder")
    List<OrderDish> toOrderDishList(List<RegisterOrderDishRequestDto> orderDishRequestDtos, int idOrder);
}
