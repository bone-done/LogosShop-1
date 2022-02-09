package com.vansisto.logosshop.controller;

import com.vansisto.logosshop.domain.UserCountDTO;
import com.vansisto.logosshop.domain.UserDTO;
import com.vansisto.logosshop.exception.ApiError;
import com.vansisto.logosshop.service.UserCountService;
import com.vansisto.logosshop.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.security.Principal;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Slf4j
@CrossOrigin
@Validated
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;
    @Autowired
    private UserCountService userCountService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO dto,
                                              Locale locale) {
        log.info(locale.getLanguage());
        return new ResponseEntity(service.create(dto), HttpStatus.CREATED);
    }

    @GetMapping("{userId}")
    public ResponseEntity<?> getById(
            @Positive @PathVariable Long userId,
            Principal principal,
            HttpServletRequest request){
        UserDTO accessUser = service.getByEmail(principal.getName());
        if (service.hasRole(accessUser.getId(), "ADMIN") || accessUser.getId() == userId)
            return ResponseEntity.ok(service.getEntity(userId));
        else {
            ApiError apiError = new ApiError(403, "Forbidden", request.getServletPath());
            return new ResponseEntity<>(apiError, HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<UserDTO>> getAll(
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(value = "page", defaultValue = "0") int page){
        PageRequest pageRequest = PageRequest.of(page, pageSize);

        return ResponseEntity.ok(service.getAll(pageRequest));
    }

    @GetMapping("count/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserCountDTO> getCountByUserId(@PathVariable Long userId){
        return ResponseEntity.ok(userCountService.getByUserId(userId));
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ApiError> validationExceptionsHandler(
            MethodArgumentNotValidException methodArgumentNotValidException,
            HttpServletRequest request){
        ApiError apiError = new ApiError(400, methodArgumentNotValidException.getMessage(), request.getServletPath());
        BindingResult bindingResult = methodArgumentNotValidException.getBindingResult();
        Map<String, String> validationErrors = new HashMap<>();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        apiError.setValidationErrors(validationErrors);

        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({SQLIntegrityConstraintViolationException.class, IllegalArgumentException.class})
    public ResponseEntity<ApiError> validationExceptionsHandler(
            Exception exception,
            HttpServletRequest request){
        ApiError apiError = new ApiError(400, exception.getMessage(), request.getServletPath());

        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }
}
