package com.cvte.product.test.service;

import com.cvte.product.test.Vo.ProductCustomerMatchVo;
import com.cvte.product.test.entity.ProCustomerRelationEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 产品和客户关系表 服务类
 * </p>
 *
 * @author 聂裴涵
 * @since 2022-08-09
 */
public interface ProCustomerRelationService extends IService<ProCustomerRelationEntity> {

    void insert(ProductCustomerMatchVo productCustomerMatchVo, String moduleId);

    void insertBatch(List<ProductCustomerMatchVo> productCustomerMatchVos);

    void updateProIds(ProductCustomerMatchVo productCustomerMatchVo);
}
