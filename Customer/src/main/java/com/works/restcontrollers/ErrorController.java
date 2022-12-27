package com.works.restcontrollers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class ErrorController {

    @GetMapping("/error")
    public ResponseEntity error() {
        Map<String, Object> hm = new LinkedHashMap<>();
        hm.put("status", false);
        hm.put("message", "Plase Login");
        return new ResponseEntity(hm, HttpStatus.UNAUTHORIZED);
    }

}
