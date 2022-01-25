package com.vansisto.logosshop.repository;

import com.vansisto.logosshop.entity.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestRepository extends JpaRepository<TestEntity, Long> {

    TestEntity findByName(String name);

    @Query("SELECT t FROM TestEntity t")
    List<TestEntity> findAllCustom();

    @Query(value = "SELECT * FROM test_entity", nativeQuery = true)
    List<TestEntity> findAllCustomNative();
}
