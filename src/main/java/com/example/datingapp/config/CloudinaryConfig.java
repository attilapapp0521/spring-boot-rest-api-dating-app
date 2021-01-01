package com.example.datingapp.config;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CloudinaryConfig {
    @Value("${cloud.cloud_name}")
    String cloudName;
    @Value("${cloud.api_key}")
    String apiKey;
    @Value("${cloud.api_secret}")
    String apiSecret;

    @Bean
    public Cloudinary cloudConfig(){
        Map<String,String> config = new HashMap<>();
        config.put("cloud_name",cloudName);
        config.put("api_key",apiKey);
        config.put("api_secret", apiSecret);
        return new Cloudinary(config);
    }
}

