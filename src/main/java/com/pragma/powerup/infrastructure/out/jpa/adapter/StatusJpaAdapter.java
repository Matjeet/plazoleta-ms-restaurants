package com.pragma.powerup.infrastructure.out.jpa.adapter;

import com.pragma.powerup.domain.model.Status;
import com.pragma.powerup.domain.spi.IStatusPersistencePort;
import com.pragma.powerup.infrastructure.out.jpa.entity.StatusEntity;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IStatusEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.IStatusRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StatusJpaAdapter implements IStatusPersistencePort {

    private final IStatusRepository statusRepository;
    private final IStatusEntityMapper statusEntityMapper;
    @Override
    public int getStatusId(String name) {
        StatusEntity statusEntity = statusRepository.findByName(name);
        return statusEntity.getId();
    }

    @Override
    public Status getStatus(int id) {
        return statusEntityMapper.toStatus(statusRepository.getReferenceById(id));
    }
}
