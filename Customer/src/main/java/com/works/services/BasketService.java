package com.works.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.works.entities.Customer;
import com.works.props.Basket;
import com.works.props.JmsData;
import com.works.props.ProductData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.*;
import org.springframework.jms.core.JmsTemplate;
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
    final JmsTemplate jmsTemplate;

    //@HystrixCommand(fallbackMethod = "defaultAddBasket", commandProperties = {
      //      @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")
    //})
    public ResponseEntity addBasket( Long pid ) {

        Map<String, Object> hm = new LinkedHashMap<>();
        Customer customer = (Customer) request.getSession().getAttribute("customer");
        List<ServiceInstance> list = client.getInstances("product");
        ServiceInstance instance = list.get(0);
        String url = instance.getUri().toString() + "/product/single/" + pid;
        try {
            ProductData data = restTemplate.getForObject(url, ProductData.class);
            addBasket(customer.getCid(), data.getResult().getPid() );

            // JMS Send Message
            Gson gson = new Gson();
            JmsData jmsData = new JmsData();
            jmsData.setId( customer.getCid() );
            jmsData.setName(customer.getName());
            jmsData.setMessage(data.getResult().getTitle());
            String sendData = gson.toJson(jmsData);
            jmsTemplate.convertAndSend(sendData);
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

    public ResponseEntity defaultAddBasket( Long pid ) {
        Map<String, Object> hm = new LinkedHashMap<>();
        hm.put("status", false);
        hm.put("message", "Product Service Server Error");
        hm.put("pid", pid);
        return new ResponseEntity(hm, HttpStatus.OK);
    }

}
