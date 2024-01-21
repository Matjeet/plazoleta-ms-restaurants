package com.pragma.powerup.infrastructure.out.jpa.mapper;

import com.pragma.powerup.infrastructure.out.jpa.entity.OrderCodeEntity;
import com.pragma.powerup.infrastructure.out.jpa.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IOrderCodeEntityMapper {

    @Mapping(source = "securityCode", target = "securityCode")
    @Mapping(source = "orderEntity", target = "order")
    OrderCodeEntity toOrderCodeEntity(int securityCode, OrderEntity orderEntity);
}
