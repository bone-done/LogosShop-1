package com.vansisto.logosshop.repository;

import com.vansisto.logosshop.entity.UserCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCountRepository extends JpaRepository<UserCount, Long> {
    Optional<UserCount> findByUserId(Long userId);
}
