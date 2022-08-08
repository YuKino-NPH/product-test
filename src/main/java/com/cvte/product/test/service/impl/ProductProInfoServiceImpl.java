package com.cvte.product.test.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cvte.product.test.Vo.ProductProInfoVo;
import com.cvte.product.test.entity.ProductProInfoEntity;
import com.cvte.product.test.enums.ProductProInfoResponseErrorEnum;
import com.cvte.product.test.exception.CustomGlobalException;
import com.cvte.product.test.mapper.ProductProInfoMapper;
import com.cvte.product.test.service.ProductProInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 产品信息 服务实现类
 *
 * @author 聂裴涵
 * @since 2022-08-08
 */
@Service
public class ProductProInfoServiceImpl extends ServiceImpl<ProductProInfoMapper, ProductProInfoEntity> implements ProductProInfoService {

    @Autowired
    ProductProInfoMapper productProInfoMapper;
    /**
     * @description: 获取产品信息的分页数据
     * @author: 聂裴涵
     * @date: 2022/8/8 3:37 PM
     * @return: com.baomidou.mybatisplus.core.metadata.IPage<com.cvte.product.test.entity.ProductProInfoEntity>
     **/
    @Override
    public IPage<ProductProInfoEntity> getProductAll() {
        QueryWrapper<ProductProInfoEntity> queryWrapper = new QueryWrapper<>();
        Page<ProductProInfoEntity> page = new Page<>(1, 10);
        IPage<ProductProInfoEntity> iPage=productProInfoMapper.selectPage(page,queryWrapper);
        return iPage;
    }

    /**
     * @description: 插入产品数据
     * @author: 聂裴涵
     * @date: 2022/8/8 3:37 PM
     * @return: void
     **/
    @Override
    public void insertProduct(ProductProInfoVo productProInfoVo,String host) {
        //判断用户是否存在
        if(productProInfoMapper.selectCount(new QueryWrapper<ProductProInfoEntity>().eq("pro_name",productProInfoVo.getProName()))>0){
            throw new CustomGlobalException(ProductProInfoResponseErrorEnum.INSERT_EXIST_ERROR.getCode(),ProductProInfoResponseErrorEnum.INSERT_EXIST_ERROR.getMsg());
        }
        productProInfoVo.setUpdHost(host);
        ProductProInfoEntity productProInfoEntity = new ProductProInfoEntity();
        productProInfoEntity.setVersion(1);
        productProInfoEntity.setIsDelete(0);
        BeanUtils.copyProperties(productProInfoVo,productProInfoEntity);
        productProInfoEntity.setCrtHost(host);
        productProInfoEntity.setCrtName(productProInfoEntity.getUpdName());
        productProInfoEntity.setCrtUser(productProInfoEntity.getUpdUser());

        int changeRows = productProInfoMapper.insert(productProInfoEntity);
        if (changeRows<1){
            throw new CustomGlobalException(ProductProInfoResponseErrorEnum.INSERT_ERROR.getCode(),ProductProInfoResponseErrorEnum.INSERT_ERROR.getMsg());
        }

    }
}
