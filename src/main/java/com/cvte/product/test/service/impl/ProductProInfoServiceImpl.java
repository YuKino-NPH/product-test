package com.cvte.product.test.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cvte.product.test.Vo.ProductProInfoVo;
import com.cvte.product.test.common.MyPage;
import com.cvte.product.test.configure.CommonConfig;
import com.cvte.product.test.entity.ProductProInfoEntity;
import com.cvte.product.test.enums.ProductProInfoResponseErrorEnum;
import com.cvte.product.test.enums.ProductProInfoVerificationErrorEnum;
import com.cvte.product.test.exception.CustomGlobalException;
import com.cvte.product.test.exception.VerificationException;
import com.cvte.product.test.mapper.ProductProInfoMapper;
import com.cvte.product.test.service.ProductProInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

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
    @Autowired
    CommonConfig commonConfig;

    /**
     * @description: 获取产品信息的分页数据
     * @author: 聂裴涵
     * @date: 2022/8/8 3:37 PM
     * @return: com.baomidou.mybatisplus.core.metadata.IPage<com.cvte.product.test.entity.ProductProInfoEntity>
     **/
    @Override
    public MyPage<ProductProInfoVo> getProductAll(Integer page, Integer pageSize, String keyword, String orderBy, String order) {
        // 进行分页数据合法性的校验
        boolean isLegal = page < 1 || pageSize < 1 || (!"ASC".equals(order) && !"DESC".equals(order));
        if (isLegal) {
            throw new VerificationException(ProductProInfoVerificationErrorEnum.PAGE_DATA_ERROR.getCode(), ProductProInfoVerificationErrorEnum.PAGE_DATA_ERROR.getMsg());
        }
        // 进行模糊查询字符串的拼接工作
        QueryWrapper<ProductProInfoEntity> queryWrapper = new QueryWrapper<>();
        if (StringUtils.hasLength(keyword)){
            queryWrapper.like("pro_name",keyword).or().like("pro_model",keyword)
                    .or().like("sale_model",keyword).or().like("pro_type",keyword)
                    .or().like("life_cycle",keyword).or().like("pro_one_category",keyword)
                    .or().like("pro_two_category",keyword).or().like("pro_three_category",keyword);
        }
        if ("DESC".equals(order)){
            queryWrapper.orderByDesc(orderBy);
        }else{
            queryWrapper.orderByAsc(orderBy);
        }

        // 完成查询以及分类的包装
        Page<ProductProInfoEntity> pages = new Page<>(page, pageSize);
        IPage<ProductProInfoEntity> iPage = this.page(pages, queryWrapper);

        MyPage<ProductProInfoVo> proInfoVoMyPage = new MyPage<>();
        List<ProductProInfoVo> proInfoVos = iPage.getRecords().stream().map(item -> {
            ProductProInfoVo productProInfoVo = new ProductProInfoVo();
            BeanUtils.copyProperties(item, productProInfoVo);
            return productProInfoVo;
        }).collect(Collectors.toList());
        proInfoVoMyPage.setList(proInfoVos);
        MyPage.Pagination pagination = proInfoVoMyPage.getPagination();
        pagination.setPageNum(iPage.getCurrent());
        pagination.setTotal(iPage.getTotal());
        pagination.setPages(iPage.getPages());
        pagination.setPageSize(iPage.getSize());

        return proInfoVoMyPage;
    }

    /**
     * @description: 根据产品ID查询产品信息
     * @author: 聂裴涵
     * @date: 2022/8/9 9:52 AM
     * @param:
     * @return: com.cvte.product.test.Vo.ProductProInfoVo
     **/
    @Override
    public ProductProInfoVo getProductById(String proId) {
        if (!StringUtils.hasLength(proId)) {
            throw new VerificationException(ProductProInfoVerificationErrorEnum.PRODUCT_ID_NULL_ERROR.getCode(), ProductProInfoVerificationErrorEnum.PRODUCT_ID_NULL_ERROR.getMsg());
        }
        ProductProInfoEntity productProInfoEntity = productProInfoMapper.selectById(proId);
        if (productProInfoEntity == null) {
            throw new CustomGlobalException(ProductProInfoResponseErrorEnum.SELECT_ERROR.getCode(), ProductProInfoResponseErrorEnum.SELECT_ERROR.getMsg());
        }
        ProductProInfoVo productProInfoVo = new ProductProInfoVo();
        BeanUtils.copyProperties(productProInfoEntity, productProInfoVo);
        return productProInfoVo;
    }


    /**
     * @description: 插入产品数据
     * @author: 聂裴涵
     * @date: 2022/8/8 3:37 PM
     * @return: void
     **/
    @Override
    public void insertProduct(ProductProInfoVo productProInfoVo, String host) {
        //判断产品是否存在
        if (productProInfoMapper.selectCount(new QueryWrapper<ProductProInfoEntity>().eq("pro_name", productProInfoVo.getProName())) > 0) {
            throw new CustomGlobalException(ProductProInfoResponseErrorEnum.INSERT_EXIST_ERROR.getCode(), ProductProInfoResponseErrorEnum.INSERT_EXIST_ERROR.getMsg());
        }
        //判断传入的产品生命周期是否符合标准
        if (!commonConfig.getStatus().contains(productProInfoVo.getLifeCycle())) {
            throw new CustomGlobalException(ProductProInfoResponseErrorEnum.INSERT_LIFECYCLE_ERROR.getCode(), ProductProInfoResponseErrorEnum.INSERT_LIFECYCLE_ERROR.getMsg());
        }
        productProInfoVo.setUpdHost(host);
        ProductProInfoEntity productProInfoEntity = new ProductProInfoEntity();
        productProInfoEntity.setVersion(1);
        productProInfoEntity.setIsDelete(0);
        BeanUtils.copyProperties(productProInfoVo, productProInfoEntity);
        productProInfoEntity.setCrtHost(host);
        productProInfoEntity.setCrtName(productProInfoEntity.getUpdName());
        productProInfoEntity.setCrtUser(productProInfoEntity.getUpdUser());

        int changedRows = productProInfoMapper.insert(productProInfoEntity);
        if (changedRows < 1) {
            throw new CustomGlobalException(ProductProInfoResponseErrorEnum.INSERT_ERROR.getCode(), ProductProInfoResponseErrorEnum.INSERT_ERROR.getMsg());
        }

    }

    /**
     * @description: 根据产品ID删除产品信息
     * @author: 聂裴涵
     * @date: 2022/8/9 9:30 AM
     * @return: void
     **/
    @Override
    public void deleteProductByProId(String proId) {
        if (!StringUtils.hasLength(proId)) {
            throw new VerificationException(ProductProInfoVerificationErrorEnum.PRODUCT_ID_NULL_ERROR.getCode(), ProductProInfoVerificationErrorEnum.PRODUCT_ID_NULL_ERROR.getMsg());
        }
        int changedRows = productProInfoMapper.deleteById(proId);
        if (changedRows < 1) {
            throw new CustomGlobalException(ProductProInfoResponseErrorEnum.DELETE_ERROR.getCode(), ProductProInfoResponseErrorEnum.DELETE_ERROR.getMsg());
        }
    }

    /**
     * @description: 根据产品ID修改产品信息，并将产品信息回显
     * @author: 聂裴涵
     * @date: 2022/8/9 9:48 AM
     * @return: com.cvte.product.test.Vo.ProductProInfoVo
     **/
    @Override
    public ProductProInfoVo updateProductByProId(String proId, ProductProInfoVo productProInfoVo) {
        if (!StringUtils.hasLength(proId)) {
            throw new VerificationException(ProductProInfoVerificationErrorEnum.PRODUCT_ID_NULL_ERROR.getCode(), ProductProInfoVerificationErrorEnum.PRODUCT_ID_NULL_ERROR.getMsg());
        }
        if (StringUtils.hasLength(productProInfoVo.getLifeCycle())&&!commonConfig.getStatus().contains(productProInfoVo.getLifeCycle())){
            throw new VerificationException(ProductProInfoVerificationErrorEnum.LIFE_CYCLE_ERROR.getCode(), ProductProInfoVerificationErrorEnum.LIFE_CYCLE_ERROR.getMsg());
        }
        ProductProInfoEntity productProInfoEntity = productProInfoMapper.selectById(proId);
        BeanUtils.copyProperties(productProInfoVo, productProInfoEntity);
        int changedRows = productProInfoMapper.updateById(productProInfoEntity);
        if (changedRows < 1) {
            throw new CustomGlobalException(ProductProInfoResponseErrorEnum.UPDATE_ERROR.getCode(), ProductProInfoResponseErrorEnum.UPDATE_ERROR.getMsg());
        }
        ProductProInfoEntity  proInfoEntity = productProInfoMapper.selectById(proId);
        BeanUtils.copyProperties(proInfoEntity, productProInfoVo);
        return productProInfoVo;
    }


}
