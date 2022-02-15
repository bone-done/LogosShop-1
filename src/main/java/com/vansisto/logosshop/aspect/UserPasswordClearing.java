package com.vansisto.logosshop.aspect;

import com.vansisto.logosshop.domain.UserDTO;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class UserPasswordClearing {

    @AfterReturning(pointcut = "execution(com.vansisto.logosshop.domain.UserDTO com.vansisto.logosshop.service.UserService.*(..))", returning = "returnValue")
    public UserDTO clearPassword(UserDTO returnValue){
        returnValue.setPassword(null);
        return returnValue;
    }
}
