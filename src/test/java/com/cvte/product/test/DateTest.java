package com.cvte.product.test;

import com.cvte.product.test.Vo.ProductProInfoVo;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.UUID;

/**
 * @author cvte
 * @date 2022/8/8 3:03 PM
 */
public class DateTest {
    @Test
    public void test1(){
        System.out.println(UUID.randomUUID());
        System.out.println(UUID.randomUUID());
    }
    @Test
    public void test2(){
        System.out.println(new Date().toString());
    }
}
