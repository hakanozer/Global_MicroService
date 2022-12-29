package com.works.profile;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Profile("test")
@PropertySource("classpath:test-application.properties")
public class TestConfig implements IProfile {

    @Value("${app.key}")
    private String key;

    @Override
    public Map<EConfig, Object> config() {
        Map<EConfig, Object> hm = new HashMap<>();
        hm.put(EConfig.apiKey, "test_123j12j312j3");
        hm.put(EConfig.rowCount, 25);
        hm.put(EConfig.key, key);
        hm.put(EConfig.timeOut, 25000);
        return hm;
    }

}