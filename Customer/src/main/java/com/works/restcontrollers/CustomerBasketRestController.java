package com.works.restcontrollers;

import com.works.services.BasketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/customerBasket")
@RequiredArgsConstructor
public class CustomerBasketRestController {

    final BasketService basketService;

    @GetMapping("/add/{pid}")
    public ResponseEntity addBasket(@PathVariable Long pid) {
        return basketService.addBasket(pid);
    }

}
