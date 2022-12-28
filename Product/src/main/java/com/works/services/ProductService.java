package com.works.services;

import com.works.entities.Product;
import com.works.repositories.ProductRepository;
import com.works.utils.REnum;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    final ProductRepository repository;
    final CacheManager cacheManager;

    public ResponseEntity save(Product product) {
        Map<REnum, Object> hm = new LinkedHashMap<>();
        try {
            repository.save(product);
            hm.put(REnum.status, true);
            hm.put(REnum.result, product);
            cacheManager.getCache("product").clear();
            return new ResponseEntity(hm, HttpStatus.OK);
        }catch (Exception ex) {
            hm.put(REnum.status, false);
            hm.put(REnum.message, "Insert Fail, unique Title");
            return new ResponseEntity(hm, HttpStatus.BAD_REQUEST);
        }
    }

    @Cacheable("product")
    public ResponseEntity list( int pageCount ) {
        Map<REnum, Object> hm = new LinkedHashMap<>();

        // Sort
        Sort sort = Sort.by(Sort.Direction.DESC, "price" );
        Pageable page = PageRequest.of(pageCount, 5, sort);
        Page<Product> list = repository.findAll(page);


        hm.put(REnum.status, true);
        hm.put(REnum.result, list );
        return new ResponseEntity(hm, HttpStatus.OK);
    }

    public ResponseEntity single( Long pid ) {
        Map<REnum, Object> hm = new LinkedHashMap<>();
        Optional<Product> optionalProduct = repository.findById(pid);
        if (optionalProduct.isPresent()) {
            hm.put(REnum.status, true);
            hm.put(REnum.result, optionalProduct.get() );
            return new ResponseEntity(hm, HttpStatus.OK);
        }
        hm.put(REnum.status, false);
        hm.put(REnum.message, "Product Not Found ID");
        return new ResponseEntity(hm, HttpStatus.BAD_REQUEST);
    }

}
