package com.cvte.product.test.mapper;

import com.cvte.product.test.entity.ProCustomerRelationEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.Set;

/**
 * <p>
 * 产品和客户关系表 Mapper 接口
 * </p>
 *
 * @author 聂裴涵
 * @since 2022-08-09
 */
@Mapper
public interface ProCustomerRelationMapper extends BaseMapper<ProCustomerRelationEntity> {
}
