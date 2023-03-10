package com.works.configs;

import com.works.utils.REnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.*;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity methodArgumentNotValid( MethodArgumentNotValidException ex ) {
        Map<REnum, Object> hm = new LinkedHashMap<>();
        hm.put(REnum.status, false);
        hm.put(REnum.errors, parseError( ex.getFieldErrors() ));
        return new ResponseEntity(hm, HttpStatus.BAD_REQUEST);
    }

    public List parseError(List<FieldError> fieldErrors) {
        List ls = new ArrayList();
        for( FieldError item : fieldErrors ) {
            Map<String, String> hm = new HashMap<>();
            hm.put("field", item.getField() );
            hm.put("message", item.getDefaultMessage() );
            ls.add(hm);
        }
        return ls;
    }


}
