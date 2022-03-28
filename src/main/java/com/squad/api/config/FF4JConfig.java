package com.squad.api.config;

import org.ff4j.FF4j;
import org.ff4j.springjdbc.store.EventRepositorySpringJdbc;
import org.ff4j.springjdbc.store.FeatureStoreSpringJdbc;
import org.ff4j.springjdbc.store.PropertyStoreSpringJdbc;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class FF4JConfig {

    @Value("${ff4j.spring.datasource.url}")
    private String jdbcUrl;
    
    @Value("${ff4j.spring.datasource.username}")
    private String jdbcUserName;
    
    @Value("${ff4j.spring.datasource.password}")
    private String jdbcPassword;
    
    @Value("${ff4j.spring.datasource.driver-class-name}")
    private String jdbcDriver;

    @Bean
    @Qualifier("getFF4j")
    public FF4j getFF4j() { 
    	
        DriverManagerDataSource jdbc = new DriverManagerDataSource();
        jdbc.setDriverClassName(jdbcDriver);
        jdbc.setUrl(jdbcUrl);
        jdbc.setPassword(jdbcPassword);
        jdbc.setUsername(jdbcUserName);

        FF4j ff4j = new FF4j();
        ff4j.setFeatureStore(new FeatureStoreSpringJdbc(jdbc));
        ff4j.setPropertiesStore(new PropertyStoreSpringJdbc(jdbc));
        ff4j.setEventRepository(new EventRepositorySpringJdbc(jdbc));
        ff4j.audit(true);
        
        return ff4j;
    }
    
}
