package com.cvte.product.test.service;

import com.cvte.product.test.Vo.ProductCustomerMatchVo;
import com.cvte.product.test.entity.PartModuleRelationEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 客户产品模版与客户料号关系表 服务类
 * </p>
 *
 * @author 聂裴涵
 * @since 2022-08-09
 */
public interface PartModuleRelationService extends IService<PartModuleRelationEntity> {

    void insert(ProductCustomerMatchVo productCustomerMatchVo, String moduleId);

    void insertBatch(List<ProductCustomerMatchVo> productCustomerMatchVos);

    void updatePartNums(ProductCustomerMatchVo productCustomerMatchVo);
}
