package com.ms.framework.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@SpringBootConfiguration
@PropertySources({
        @PropertySource("classpath:default.properties"),
        @PropertySource(value="classpath:${spring.profiles.active}/test.properties",ignoreResourceNotFound = false)
})
@ComponentScan("com.ms.framework")
public class ServiceConfig {
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceHolderConfigurer(){
        PropertySourcesPlaceholderConfigurer test = new PropertySourcesPlaceholderConfigurer();
        test.setIgnoreUnresolvablePlaceholders(false);
        return test;
    }
}
