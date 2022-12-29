package com.works.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.works.entities.Customer;
import com.works.props.Basket;
import com.works.props.ProductData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class BasketService {

    final DiscoveryClient client;
    final HttpServletRequest request;
    final RestTemplate restTemplate;
    final ObjectMapper objectMapper;

    public ResponseEntity addBasket( Long pid ) {

        /*
        int i = 1;
        int x = 0;
        int y = i / x;
         */

        Map<String, Object> hm = new LinkedHashMap<>();
        Customer customer = (Customer) request.getSession().getAttribute("customer");
        List<ServiceInstance> list = client.getInstances("product");
        ServiceInstance instance = list.get(0);
        String url = instance.getUri().toString() + "/product/single/" + pid;
        try {
            ProductData data = restTemplate.getForObject(url, ProductData.class);
            addBasket(customer.getCid(), data.getResult().getPid() );
            return new ResponseEntity(data, HttpStatus.OK);
        }catch (Exception ex) {
            hm.put("status", false);
            hm.put("message", ex.getMessage());
        }
        return new ResponseEntity(hm, HttpStatus.OK);
    }

    public void addBasket( Long cid, Integer pid ) {
        List<ServiceInstance> list = client.getInstances("basket");
        ServiceInstance instance = list.get(0);
        String url = instance.getUri().toString() + "/basket/add";

        String data = "{ \"cid\": "+cid+", \"pid\": "+pid+" }";
        Basket basket = new Basket(cid, pid);
        try {
            String stBasket = objectMapper.writeValueAsString(basket);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity httpEntity = new HttpEntity(stBasket, headers);

            ResponseEntity<String> entityData = restTemplate.postForEntity(url, httpEntity, String.class);
            System.out.println( entityData.getBody() );
        }catch (Exception ex) { }

    }

}
