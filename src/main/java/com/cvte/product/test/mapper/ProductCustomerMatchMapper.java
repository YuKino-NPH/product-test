package com.cvte.product.test.mapper;

import com.cvte.product.test.entity.ProductCustomerMatchEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 聂裴涵
 * @since 2022-08-08
 */
@Mapper
public interface ProductCustomerMatchMapper extends BaseMapper<ProductCustomerMatchEntity> {

    List<String> selectProIdsByFuzzyMatch(@Param("moduleName") String moduleName, @Param("customerName") String customerName,@Param("partNumId") String partNumId, @Param("customerBatchNums") String[] customerBatchNums,@Param("country") String country);

    List<String> selectProIdsByFuzzyMatchNotPartNum(@Param("moduleName") String moduleName,@Param("customerName") String customerName, @Param("customerBatchNums") String[] customerBatchNums,@Param("country") String country);
}
