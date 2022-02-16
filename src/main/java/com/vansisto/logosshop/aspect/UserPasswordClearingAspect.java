package com.vansisto.logosshop.aspect;

import com.vansisto.logosshop.domain.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Aspect
@Component
public class UserPasswordClearingAspect {

    @Pointcut("execution(* com.vansisto.logosshop.service.UserService.*(..))")
    public void eachMethod(){}

    @AfterReturning(pointcut = "eachMethod()", returning = "dto")
    public UserDTO clearPassword(UserDTO dto){
        dto.setPassword(null);
        return dto;
    }
    @AfterReturning(pointcut = "eachMethod()", returning = "dtos")
    public Page<UserDTO> clearPasswordForList(Page<UserDTO> dtos){
        dtos.map(user -> {
            user.setPassword(null);
            return user;
        });
        return dtos;
    }
}
