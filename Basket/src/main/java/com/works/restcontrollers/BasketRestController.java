package com.works.restcontrollers;

import com.works.entities.Basket;
import com.works.services.BasketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/basket")
@RequiredArgsConstructor
public class BasketRestController {

    final BasketService service;

    @PostMapping("/add")
    public ResponseEntity add(@RequestBody Basket basket) {
        return service.add(basket);
    }

    @GetMapping("/list/{cid}")
    public ResponseEntity list( @PathVariable Long cid ) {
        return service.list(cid);
    }

}
