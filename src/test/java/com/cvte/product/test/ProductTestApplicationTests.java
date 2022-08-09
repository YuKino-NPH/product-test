package com.cvte.product.test;

import com.cvte.product.test.configure.CommonConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
class ProductTestApplicationTests {
    @Autowired
    CommonConfig commonConfig;
    @Test
    void contextLoads() {
        commonConfig.getStatus().forEach(System.out::println);
       //System.out.println(commonConfig.getStatus());
    }

}
