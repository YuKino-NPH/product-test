package com.cvte.product.test.configure;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;

import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

/**
 * @author cvte
 * @date 2022/8/8 2:47 PM
 * MybatisPlus配置类
 */
@Configuration
public class MybatisPlusConfig {
    /**
     * @description: 配置MybatisPlus分页插件
     * @author: 聂裴涵
     * @date: 2022/8/8 2:49 PM
     **/
    @Bean
    public  MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        OptimisticLockerInnerInterceptor optimisticLockerInnerInterceptor = new OptimisticLockerInnerInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.POSTGRE_SQL));
        interceptor.addInnerInterceptor(optimisticLockerInnerInterceptor);
        return interceptor;
    }

    /**
     * @description: 自定义自动插入数据的格式
     * @author: 聂裴涵
     * @date: 2022/8/8 3:13 PM
     * @return: com.baomidou.mybatisplus.core.handlers.MetaObjectHandler
     **/
    @Bean
    public MetaObjectHandler myMetaObjectHandler(){
        return new MetaObjectHandler() {
            @Override
            public void insertFill(MetaObject metaObject) {
                this.setFieldValByName("crtTime", new Date(),metaObject);
                this.setFieldValByName("updTime", new Date(),metaObject);
            }

            @Override
            public void updateFill(MetaObject metaObject) {
                this.setFieldValByName("updTime", new Date(),metaObject);
            }
        };
    }

}
