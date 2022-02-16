package com.vansisto.logosshop.aspect;

import com.vansisto.logosshop.domain.UserDTO;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class UserPasswordClearingAspect {

    @AfterReturning(pointcut = "execution(* com.vansisto.logosshop.service.impl.UserServiceImpl.*(..))", returning = "dto")
    public UserDTO clearPassword(Object dto){
        UserDTO userDTO = null;
        if (dto instanceof UserDTO) userDTO = (UserDTO) dto;
        userDTO.setPassword(null);
        return userDTO;
    }
}
