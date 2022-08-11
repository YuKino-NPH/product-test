package com.cvte.product.test.service;

import com.cvte.product.test.Vo.ProductCustomerMatchVo;
import com.cvte.product.test.common.MyPage;
import com.cvte.product.test.entity.ProductProInfoEntity;
import com.cvte.product.test.exception.CustomGlobalException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author cvte
 * @date 2022/8/10 2:49 PM
 */
@SpringBootTest
public class ProductCustomerMatchServiceTest {
    @Autowired
    ProductCustomerMatchService productCustomerMatchService;
    @Autowired
    ProductProInfoService productProInfoService;
    /**
     * @description: 批量插入测试数据
     * @author: 聂裴涵
     * @date: 2022/8/10 7:44 PM
     * @param:
     * @return: void
     **/
    @Test
    public void insertTest(){
        List<ProductProInfoEntity> proList = productProInfoService.list();
        boolean isFlag=true;
        List<ProductCustomerMatchVo> productCustomerMatchVos=new ArrayList<>();
        for (int i = 0; i <2000000; i++) {
            int random = (int) (Math.random() * 4)+1;
            ProductCustomerMatchVo productCustomerMatchVo = new ProductCustomerMatchVo();
            productCustomerMatchVo.setModuleName("CVTE-0000"+i);
            productCustomerMatchVo.setCustomerBrand("CVTE");

            ArrayList<String> customerPartNums = new ArrayList<>();
            ArrayList<String> proIds = new ArrayList<>();
            String uuid=UUID.randomUUID().toString().toUpperCase();
            uuid= uuid.replaceAll("-", random+"");
            for (int j = 0; j <random ; j++) {
                proIds.add(proList.get((int) (Math.random()*proList.size())).getProId());
            }
            customerPartNums.add(uuid.substring(0,8));
            if (isFlag){
                customerPartNums.add(uuid.substring(8,16));
            }
            productCustomerMatchVo.setProIds(proIds);
            productCustomerMatchVo.setCustomerPartNums(customerPartNums);
            productCustomerMatchVo.setModuleId(UUID.randomUUID().toString().replaceAll("-",random+""));
            productCustomerMatchVo.setCountry("中国");
            productCustomerMatchVo.setCustomerName("TCL+000"+i);
            productCustomerMatchVo.setCustomerBatchNum(uuid.substring(16,24));

            productCustomerMatchVo.setUpdUser(UUID.randomUUID().toString());
            productCustomerMatchVo.setUpdName("niepeihan");
            productCustomerMatchVo.setUpdHost("127.0.0.1");

            productCustomerMatchVos.add(productCustomerMatchVo);



            isFlag=!isFlag;
        }
        productCustomerMatchService.insertModuleBatch(productCustomerMatchVos,"127.0.0.1");

    }
    /**
     * @description: 对根据keyword获取到分页数据
     * @author: 聂裴涵
     * @date: 2022/8/11 2:13 PM
     * @return: void
     **/
    @Test
    public void getModuleAllTest(){
        MyPage<ProductCustomerMatchVo> moduleAll = productCustomerMatchService.getModuleAll(1, 10, "", "module_id", "ASC");
        System.out.println(moduleAll.toString());
        // 测试模糊匹配功能
        MyPage<ProductCustomerMatchVo> moduleAllTest = productCustomerMatchService.getModuleAll(1, 10, "59292D82", "module_id", "ASC");
        System.out.println(moduleAllTest);
    }
    /**
     * @description: 对getModuleById进行测试
     * @author: 聂裴涵
     * @date: 2022/8/11 2:28 PM
     * @return: void
     **/
    @Test
    public void getModuleByIdTest(){
        //传入存在的ID
        ProductCustomerMatchVo moduleById = productCustomerMatchService.getModuleById("000006ce2e03a24b602ba212cdca8140732c");
        System.out.println(moduleById);
        //传入不存在ID
        try{
            productCustomerMatchService.getModuleById("dasdq112e12easdasca21e12");
        }catch (CustomGlobalException e){
            System.out.println(e.getCode());
            System.out.println(e.getMsg());
        }
    }
    /**
     * @description: 测试插入数据
     * @author: 聂裴涵
     * @date: 2022/8/11 2:29 PM
     * @return: void
     **/
    @Test
    public void insertModuleTest(){
        ProductCustomerMatchVo productCustomerMatchVo = new ProductCustomerMatchVo();
        productCustomerMatchVo.setModuleName("AAAAA");
        productCustomerMatchVo.setCustomerName("HHHHH");
        productCustomerMatchVo.setCustomerBrand("BBBB");
        productCustomerMatchVo.setCustomerBatchNum("CCCC");
        productCustomerMatchVo.setCountry("中国");
        productCustomerMatchVo.setUpdUser("7250542e-86cb-4acf-93c8-55b053d2888d");
        productCustomerMatchVo.setUpdName("niepeihan");
        productCustomerMatchVo.setUpdHost("192.168.0.1");
        // 客户料号
        ArrayList<String> partNums = new ArrayList<>();
        partNums.add("A1");
        partNums.add("A2");
        productCustomerMatchVo.setCustomerPartNums(partNums);
        // 产品ID
        ArrayList<String> proIds = new ArrayList<>();
        proIds.add("d72d5c65d6c7c2432d1048d18f641fde");
        proIds.add("9c3b9465fadf0534ea6b5d88bbb6a30c");
        productCustomerMatchVo.setProIds(proIds);
        ProductCustomerMatchVo customerMatchVo = productCustomerMatchService.insertModule(productCustomerMatchVo);
        System.out.println(customerMatchVo);
    }
    /**
     * @description: 测试更新客户数据
     * @author: 聂裴涵
     * @date: 2022/8/11 3:07 PM
     * @return: void
     **/
    @Test
    public void updateModuleByIdTest(){
        String moduleId="0000109f388df345ea3862931e5f3a176323";
        ProductCustomerMatchVo productCustomerMatchVo = new ProductCustomerMatchVo();
        productCustomerMatchVo.setCountry("美国");
        productCustomerMatchVo.setModuleName("BBB");
        productCustomerMatchVo.setUpdHost("127.0.0.1");
        productCustomerMatchVo.setUpdUser("7250542e-86cb-4acf-93c8-55b053d2888d");
        productCustomerMatchVo.setUpdName("niepeihan");
        productCustomerMatchService.updateModuleById(moduleId,productCustomerMatchVo);

        // 缺失必要参数
        productCustomerMatchVo.setUpdUser("");
        try {
            productCustomerMatchService.updateModuleById(moduleId,productCustomerMatchVo);
        }catch (CustomGlobalException e){
            System.out.println(e.getCode());
            System.out.println(e.getMsg());
        }
        // ID不存在
        moduleId="3123asdfdqwd";
        try {
            productCustomerMatchService.updateModuleById(moduleId,productCustomerMatchVo);
        }catch (CustomGlobalException e){
            System.out.println(e.getCode());
            System.out.println(e.getMsg());
        }

    }
    /**
     * @description: 测试根据moduleID删除数据
     * @author: 聂裴涵
     * @date: 2022/8/11 3:02 PM
     * @return: void
     **/
    @Test
    public void deleteModuleById(){
        String moduleById="0000109f388df345ea3862931e5f3a176323";
        productCustomerMatchService.deleteModuleById(moduleById);

        // moduleId不存在时
        try{
            productCustomerMatchService.deleteModuleById(moduleById);
        }catch (CustomGlobalException e){
            System.out.println(e.getCode());
            System.out.println(e.getMsg());
        }
    }
    /**
     * @description: 测试根据不固定参数查询数据
     * @author: 聂裴涵
     * @date: 2022/8/11 3:08 PM
     * @return: void
     **/
    @Test
    public void getProductFuzzyMatchTest(){
        String customerName="TCL+000247612";
        String customerPartNum="38B25341";
        String customerBatchNum="7738DE63";
        String country="中国";
        String moduleName="CVTE-0000952588";
        long time1 = System.currentTimeMillis();
        // 根据customerName 来查询
        productCustomerMatchService.getProductFuzzyMatch(customerName,null,null,null,null);
        long time2 = System.currentTimeMillis();
        System.out.println("根据customerName查询耗时"+(time2-time1));
        // 根据 customerPartNum 来查询数据
        productCustomerMatchService.getProductFuzzyMatch(null,customerPartNum,null,null,null);
        long time3 = System.currentTimeMillis();
        System.out.println("根据customerPartNum查询耗时"+(time3-time2));
        // 根据customerBatchNum来查询数据
        productCustomerMatchService.getProductFuzzyMatch(null,null,customerBatchNum,null,null);
        long time4 = System.currentTimeMillis();
        System.out.println("根据customerBatchNum查询耗时"+(time4-time3));
        // 根据模块名称查询
        productCustomerMatchService.getProductFuzzyMatch(null,null,null,null,moduleName);
        long time5 = System.currentTimeMillis();
        System.out.println("根据moduleName查询耗时"+(time5-time4));

        // 根据customerPartNum和模块名称查询
        productCustomerMatchService.getProductFuzzyMatch(null,customerPartNum,null,null,moduleName);
        long time6 = System.currentTimeMillis();
        System.out.println("根据customerPartNum和moduleName查询耗时"+(time5-time4));

        // 只根据国家查询数据
        try {
            productCustomerMatchService.getProductFuzzyMatch(null,null,null,country,null);
        }catch (CustomGlobalException e){
            System.out.println(e.getCode());
            System.out.println(e.getMsg());
        }


    }
}
