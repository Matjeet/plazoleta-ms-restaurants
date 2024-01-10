package com.pragma.powerup.application.mapper.request;

import com.pragma.powerup.application.dto.request.RegisterDishDto;
import com.pragma.powerup.domain.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ICategoryRequestMapper {

    @Mapping(source = "registerDishDto.category.name", target = "name")
    Category toCategory(RegisterDishDto registerDishDto);
}
