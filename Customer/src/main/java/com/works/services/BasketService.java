package com.works.services;

import com.works.entities.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BasketService {

    final DiscoveryClient client;
    final HttpServletRequest request;

    public ResponseEntity addBasket( Long pid ) {
        Map<String, Object> hm = new LinkedHashMap<>();
        Customer customer = (Customer) request.getSession().getAttribute("customer");
        List<ServiceInstance> list = client.getInstances("product");
        ServiceInstance instance = list.get(0);
        String url = instance.getUri().toString() + "/product/single/" + pid;
        System.out.println( url );
        return new ResponseEntity(hm, HttpStatus.OK);
    }

}
