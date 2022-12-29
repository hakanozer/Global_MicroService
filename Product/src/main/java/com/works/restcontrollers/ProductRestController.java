package com.works.restcontrollers;

import com.works.entities.Product;
import com.works.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
@Validated
public class ProductRestController {

    final ProductService service;

    @GetMapping("/list")
    // public ResponseEntity list( @NotNull @Min(0) @Max(10000) @RequestParam int pageCount ) {
    public ResponseEntity list( @RequestParam(defaultValue = "0") int pageCount ) {
        return service.list(pageCount);
    }

    @PostMapping("/save")
    public ResponseEntity save( @Valid @RequestBody Product product) {
        return service.save(product);
    }

    @GetMapping("/single/{pid}")
    public ResponseEntity single( @PathVariable Long pid ) {
        return service.single(pid);
    }

    @GetMapping("/catID/{cid}")
    public ResponseEntity catID( @PathVariable Long cid ) {
        return service.catID(cid);
    }




}
