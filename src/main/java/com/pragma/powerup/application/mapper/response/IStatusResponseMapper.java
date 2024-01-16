package com.pragma.powerup.application.mapper.response;

import com.pragma.powerup.application.dto.response.StatusOrderResponseDto;
import com.pragma.powerup.domain.model.Status;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IStatusResponseMapper {

    StatusOrderResponseDto toStatusOrderDto(Status status);
}
