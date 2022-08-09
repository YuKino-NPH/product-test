package com.cvte.product.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author 聂裴涵
 */
@ConfigurationPropertiesScan("com.cvte.product.test")
@EnableConfigurationProperties
@SpringBootApplication
public class ProductTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductTestApplication.class, args);
    }

}
