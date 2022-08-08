package com.cvte.product.test;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
class ProductTestApplicationTests {

    @Test
    void contextLoads() {
        System.out.println(UUID.randomUUID());
    }

}
