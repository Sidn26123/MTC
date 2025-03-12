package com.sidn.metruyenchu.fileservice.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.cloudinary.*;
import com.cloudinary.utils.ObjectUtils;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.HashMap;
import java.util.Map;
@Configuration
public class ConfigCloudinary {
    @Bean
    public Cloudinary cloudinary(){ {

            final Map<String, String> config = new HashMap<>();
            config.put("cloud_name", "dcjkzhzmt");
            config.put("api_key", "445937817739151");
            config.put("api_secret", "IJYYRTQWZNwG7ekJveY6Z20LIyU");

            return new Cloudinary(config);
        }

    }
}
