package com.work.restcontrollers;

import com.google.gson.Gson;
import com.work.props.JmsData;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class JMSRestController {

    final JmsTemplate jmsTemplate;

    @PostMapping("/send")
    public Map send(@RequestBody JmsData jmsData) {
        Map<String, String> hm = new LinkedHashMap<>();
        Gson gson = new Gson();
        String sendData = gson.toJson(jmsData);
        jmsTemplate.convertAndSend(sendData);
        hm.put("status", "Send Message");
        return hm;
    }

}
