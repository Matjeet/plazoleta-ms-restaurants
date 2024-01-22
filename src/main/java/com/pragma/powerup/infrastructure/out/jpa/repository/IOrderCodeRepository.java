package com.pragma.powerup.infrastructure.out.jpa.repository;

import com.pragma.powerup.infrastructure.out.jpa.entity.OrderCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IOrderCodeRepository extends JpaRepository<OrderCodeEntity, Integer> {

    Optional<OrderCodeEntity> findBySecurityCode(int securityCode);
}
