package com.cvte.product.test.service;

import com.cvte.product.test.Vo.ProductProInfoVo;
import com.cvte.product.test.common.MyPage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.UUID;

/**
 * @author cvte
 * @date 2022/8/10 2:04 PM
 */
@SpringBootTest
public class ProductProInfoServiceTest {
    @Autowired
    ProductProInfoService productProInfoService;
    @Test
    public void productProInfoBatchInsert(){
        ArrayList<ProductProInfoVo> productProInfoVos = new ArrayList<>();
        for (int i = 147602; i <1000000; i++) {
            UUID uuid = UUID.randomUUID();
            ProductProInfoVo productProInfoVo = new ProductProInfoVo();
            productProInfoVo.setUpdUser(uuid.toString());
            productProInfoVo.setUpdName("niepeihan");
            productProInfoVo.setUpdHost("192.168.1.1");
            productProInfoVo.setProModel("MAC-"+i);
            productProInfoVo.setProName("MAC--"+i);
            productProInfoVo.setSaleModel("MAC---"+i);
            productProInfoVo.setProType("笔记本电脑");
            productProInfoVo.setLifeCycle("试产");
            productProInfoVo.setProOneCategory("电子产品");
            productProInfoVo.setProTwoCategory("电脑");
            productProInfoVo.setProThreeCategory("APPLE");
            productProInfoVos.add(productProInfoVo);
            //productProInfoService.insertProduct(productProInfoVo,"192.168.0.1");
        }
        productProInfoService.insertBatch(productProInfoVos);
    }
    /**
     * @description: 测试获取分页数据
     * @author: 聂裴涵
     * @date: 2022/8/11 3:32 PM
     * @return: void
     **/
    @Test
    public void getProductAllTest(){
        MyPage<ProductProInfoVo> productAll = productProInfoService.getProductAll(1, 10, "", "pro_id", "ASC");
        System.out.println(productAll);
    }
    /**
     * @description: 测试根据产品Id获取产品数据
     * @author: 聂裴涵
     * @date: 2022/8/11 3:33 PM
     * @return: void
     **/
    @Test
    public void getProductById(){
        productProInfoService.getProductById("");
    }
    /**
     * @description: 测试插入产品数据
     * @author: 聂裴涵
     * @date: 2022/8/11 3:34 PM
     * @return: void
     **/
    @Test
    public void insertProductTest(){
        ProductProInfoVo productProInfoVo = new ProductProInfoVo();
        productProInfoVo.setProName("ZZZZ");
        productProInfoVo.setProModel("BBBB");
        productProInfoVo.setProType("CCCC");
        productProInfoVo.setSaleModel("DDDD");
        productProInfoVo.setLifeCycle("初始");
        productProInfoVo.setProOneCategory("A1");
        productProInfoVo.setProTwoCategory("A2");
        productProInfoVo.setProThreeCategory("A3");
        productProInfoVo.setUpdUser("f74a1eff-edfd-427b-8f26-55e37e0386e9");
        productProInfoVo.setUpdName("niepeihan");
        productProInfoVo.setUpdHost("127.0.0.1");

        ProductProInfoVo proInfoVo = productProInfoService.insertProduct(productProInfoVo);
        System.out.println(proInfoVo);

    }
    @Test
    public void productProInfoUpdateTest(){
        String proId="8ae79b7c8ed901bd96ef0443c0825282";
        ProductProInfoVo productProInfoVo = new ProductProInfoVo();
        productProInfoVo.setUpdUser("f74a1eff-edfd-427b-8f26-55e37e0386e9");
        productProInfoVo.setUpdName("niepeihan");
        productProInfoVo.setUpdHost("127.0.0.1");
        productProInfoVo.setProModel("FFFF");
        productProInfoService.updateProductByProId(proId,productProInfoVo);

    }
    @Test
    public void deleteProductByProIdTest(){
        String proId="8ae79b7c8ed901bd96ef0443c0825282";
        productProInfoService.deleteProductByProId(proId);
    }
}
