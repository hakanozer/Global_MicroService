package com.works.profile;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Profile("dev")
@PropertySource("classpath:dev-application.properties")
public class DevConfig implements IProfile {

    @Value("${app.key}")
    private String key;

    @Override
    public Map<EConfig, Object> config() {
        Map<EConfig, Object> hm = new HashMap<>();
        hm.put(EConfig.apiKey, "dev_123j12j312j3");
        hm.put(EConfig.key, key);
        hm.put(EConfig.rowCount, 50);
        hm.put(EConfig.timeOut, 15000);
        return hm;
    }

}
