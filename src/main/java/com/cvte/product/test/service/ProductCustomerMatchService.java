package com.cvte.product.test.service;

import com.cvte.product.test.Vo.ProductCustomerMatchVo;
import com.cvte.product.test.common.MyPage;
import com.cvte.product.test.entity.ProductCustomerMatchEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 聂裴涵
 * @since 2022-08-08
 */
public interface ProductCustomerMatchService extends IService<ProductCustomerMatchEntity> {

    MyPage<ProductCustomerMatchVo> getModuleAll(Integer page, Integer pageSize, String keyword, String orderBy, String order);

    ProductCustomerMatchVo getModuleById(String moduleId);

    void insertModule(ProductCustomerMatchVo productCustomerMatchVo, String updHost);

    void deleteModuleById(String moduleId);

    ProductCustomerMatchVo updateModuleById(String moduleId, ProductCustomerMatchVo productCustomerMatchVo);
}
