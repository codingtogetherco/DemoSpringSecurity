package com.co.coding.toguether.demo.spring.security.repository;

import com.co.coding.toguether.demo.spring.security.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);
}
