package com.pragma.powerup.infrastructure.out.jpa.repository;

import com.pragma.powerup.infrastructure.out.jpa.entity.OrderCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrderCodeRepository extends JpaRepository<OrderCodeEntity, Integer> {
}
