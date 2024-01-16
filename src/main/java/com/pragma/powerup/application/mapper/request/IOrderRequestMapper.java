package com.pragma.powerup.application.mapper.request;

import com.pragma.powerup.application.dto.request.RegisterOrderRequestDto;
import com.pragma.powerup.domain.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IOrderRequestMapper {

    Order toOrder(RegisterOrderRequestDto registerOrderRequestDto, int idStatus);
}
