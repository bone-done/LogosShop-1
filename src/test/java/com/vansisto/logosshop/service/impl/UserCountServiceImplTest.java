package com.vansisto.logosshop.service.impl;

import com.vansisto.logosshop.domain.UserCountDTO;
import com.vansisto.logosshop.entity.UserCount;
import com.vansisto.logosshop.exception.AlreadyExistsException;
import com.vansisto.logosshop.repository.UserCountRepository;
import com.vansisto.logosshop.util.ModelMapperUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserCountServiceImplTest {

    @InjectMocks
    private UserCountServiceImpl userCountService;
    @Mock
    private UserCountRepository userCountRepository;
    @Mock
    private ModelMapperUtil mapper;

    @Test
    void create_WithoutIdSuccess() {
        // given
        UserCountDTO userCountDTO = new UserCountDTO();
        userCountDTO.setAmount(new BigDecimal(2));
        UserCount userCount = new UserCount();
        userCount.setAmount(new BigDecimal(2));

        // when
        when(userCountRepository.save(any(UserCount.class))).thenReturn(userCount);
        when(mapper.map(any(UserCountDTO.class), any())).thenReturn(userCount);
        when(mapper.map(any(UserCount.class), any())).thenReturn(userCountDTO);

        // then
        UserCountDTO createdUserDTO = userCountService.create(userCountDTO);

        // verify
        verify(userCountRepository).save(any(UserCount.class));

        assertEquals(userCount.getAmount(), createdUserDTO.getAmount());
    }

    @Test
    void create_WithIdSuccess() {
        // given
        UserCountDTO userCountDTO = new UserCountDTO();
        userCountDTO.setId(1l);
        userCountDTO.setAmount(new BigDecimal(2));
        UserCount userCount = new UserCount();
        userCount.setAmount(new BigDecimal(2));

        // when
        when(userCountRepository.save(any(UserCount.class))).thenReturn(userCount);
        when(userCountRepository.existsById(anyLong())).thenReturn(false);
        when(mapper.map(any(UserCountDTO.class), any())).thenReturn(userCount);
        when(mapper.map(any(UserCount.class), any())).thenReturn(userCountDTO);

        // then
        UserCountDTO createdUserDTO = userCountService.create(userCountDTO);

        // verify
        verify(userCountRepository).save(any(UserCount.class));

        assertEquals(userCount.getAmount(), createdUserDTO.getAmount());
    }

    @Test
    void create_ThrowsException() {
        // given
        UserCountDTO userCountDTO = new UserCountDTO();
        userCountDTO.setId(1l);

        // when
        when(userCountRepository.existsById(anyLong())).thenReturn(true);

        // then
        assertThrows(AlreadyExistsException.class, () -> {
            userCountService.create(userCountDTO);
        });

        // verify
        verify(userCountRepository).existsById(anyLong());
    }

    @Test
    void update_Success() {
        // given
        UserCountDTO dto = new UserCountDTO();
        dto.setId(1l);
        UserCount count = new UserCount();
        count.setId(1l);

        // when
        when(userCountRepository.save(any(UserCount.class))).thenReturn(count);
        doReturn(count).when(mapper).map(dto, UserCount.class);
        doReturn(dto).when(mapper).map(count, UserCountDTO.class);

        //then
        UserCountDTO updated = userCountService.update(dto);

        //verify
        verify(userCountRepository).save(count);

        assertEquals(dto.getId(), updated.getId(), "Expected and updated id's don't match");
    }
//
//    @Test
//    void delete() {
//    }
//
//    @Test
//    void deleteById() {
//    }
//
//    @Test
//    void getEntity() {
//    }
//
//    @Test
//    void getAll() {
//    }
//
//    @Test
//    void getByUserId() {
//    }
}