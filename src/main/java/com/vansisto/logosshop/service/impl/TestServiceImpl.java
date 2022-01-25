package com.vansisto.logosshop.service.impl;

import com.vansisto.logosshop.dto.TestDTO;
import com.vansisto.logosshop.entity.TestEntity;
import com.vansisto.logosshop.repository.TestRepository;
import com.vansisto.logosshop.service.TestService;
import com.vansisto.logosshop.util.ModelMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private TestRepository repository;

    @Autowired
    private ModelMapperUtil modelMapperUtil;

    @Override
    @Transactional
    public TestDTO create(TestDTO dto) {
        TestEntity entityToSave = modelMapperUtil.map(dto, TestEntity.class);
        TestEntity createdEntity = repository.save(entityToSave);
        return modelMapperUtil.map(createdEntity, TestDTO.class);
    }

    @Override
    @Transactional
    public TestDTO update(TestDTO dto) {
        TestEntity entityToUpdate = modelMapperUtil.map(dto, TestEntity.class);
        TestEntity updatedEntity = repository.save(entityToUpdate);

        return modelMapperUtil.map(updatedEntity, TestDTO.class);
    }

    @Override
    @Transactional
    public TestDTO delete(TestDTO dto) {
        TestDTO toDelete = getEntity(dto.getId());
        TestEntity entityToDelete = modelMapperUtil.map(toDelete, TestEntity.class);
        repository.delete(entityToDelete);

        return toDelete;
    }

    @Override
    @Transactional
    public TestDTO deleteById(Long id) {
        TestDTO toDelete = getEntity(id);
        repository.deleteById(id);

        return toDelete;
    }

    @Override
    public TestDTO getEntity(Long id) {
        return modelMapperUtil.map(repository.getById(id), TestDTO.class);
    }

    @Override
    public List<TestDTO> getAll() {
        return modelMapperUtil.mapAll(repository.findAll(), TestDTO.class);
    }

    @Override
    public TestDTO findByName(String name) {
        TestEntity entity = repository.findByName(name);
        return modelMapperUtil.map(entity, TestDTO.class);
    }

    @Override
    public List<TestDTO> findAllCustom() {
        return modelMapperUtil.mapAll(repository.findAllCustom(), TestDTO.class);
    }

    @Override
    public List<TestDTO> findAllCustomNative() {
        return modelMapperUtil.mapAll(repository.findAllCustomNative(), TestDTO.class);
    }
}
