package com.cvte.product.test.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cvte.product.test.Vo.ProductProInfoVo;
import com.cvte.product.test.common.MyPage;
import com.cvte.product.test.entity.ProductProInfoEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cvte.product.test.exception.VerificationException;

/**
 * <p>
 * 产品信息 服务类
 * </p>
 *
 * @author 聂裴涵
 * @since 2022-08-08
 */
public interface ProductProInfoService extends IService<ProductProInfoEntity> {
    /**
     * @description: 根据分页要求，获取所有的商品信息
     * @author: 聂裴涵
     * @date: 2022/8/8 2:54 PM
     * @return: com.baomidou.mybatisplus.core.metadata.IPage<com.cvte.product.test.entity.ProductProInfoEntity>
     **/
    MyPage<ProductProInfoVo> getProductAll(Integer page, Integer pageSize, String keyword, String orderBy, String order);

    /**
     * @description: 插入新的产品信息数据
     * @author: 聂裴涵
     * @date: 2022/8/8 3:36 PM
     * @return: void
     **/
    void insertProduct(ProductProInfoVo productProInfoVo,String host);

    void deleteProductByProId(String proId) throws VerificationException;

    ProductProInfoVo updateProductByProId(String proId, ProductProInfoVo productProInfoVo);

    ProductProInfoVo getProductById(String proId);
}
