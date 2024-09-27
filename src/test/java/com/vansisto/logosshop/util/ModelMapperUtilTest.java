package com.vansisto.logosshop.util;

import com.vansisto.logosshop.domain.UserDTO;
import com.vansisto.logosshop.entity.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ModelMapperUtilTest {

    private ModelMapperUtil mapper = new ModelMapperUtil();

    @Test
    void mapAll() {
        List<User> users = new ArrayList<>();
        users.add(new User());
        users.add(new User());
        List<UserDTO> userDTOS = mapper.mapAll(users, UserDTO.class);
        assertEquals(userDTOS.size(), 2);
    }
}