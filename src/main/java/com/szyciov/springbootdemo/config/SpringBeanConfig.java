package com.szyciov.springbootdemo.config;

import com.szyciov.springbootdemo.util.CustomPropertyConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class SpringBeanConfig {

    /**
     * 读取配置文件
     * @param env
     * @return
     */
    @Bean
    public CustomPropertyConfigurer customPropertyConfigurer(Environment env) {
        CustomPropertyConfigurer configurer = new CustomPropertyConfigurer();
        configurer.setIgnoreResourceNotFound(true);
        configurer.setLocations(new ClassPathResource(env.getProperty("spring.profiles.active") + "/web.properties"));
        return configurer;
    }
}
