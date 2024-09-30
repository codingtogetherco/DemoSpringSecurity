package com.co.coding.toguether.demo.spring.security.repository;

import com.co.coding.toguether.demo.spring.security.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByName(String name);
}