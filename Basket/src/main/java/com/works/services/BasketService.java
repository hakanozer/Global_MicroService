package com.works.services;

import com.works.entities.Basket;
import com.works.repositories.BasketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BasketService {

    final BasketRepository repository;

    public ResponseEntity add(Basket basket) {
        Map<String, Object> hm = new LinkedHashMap<>();
        repository.save(basket);
        hm.put("status", true);
        hm.put("result", basket);
        return new ResponseEntity(hm, HttpStatus.OK);
    }


    public ResponseEntity list(Long cid) {
        Map<String, Object> hm = new LinkedHashMap<>();
        hm.put("status", true);
        hm.put("result", repository.findByCidEquals(cid));
        return new ResponseEntity(hm, HttpStatus.OK);
    }

}
