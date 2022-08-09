package com.cvte.product.test.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cvte.product.test.Vo.ProductCustomerMatchVo;
import com.cvte.product.test.Vo.ProductProInfoVo;
import com.cvte.product.test.common.MyPage;
import com.cvte.product.test.entity.ProductCustomerMatchEntity;
import com.cvte.product.test.entity.ProductProInfoEntity;
import com.cvte.product.test.enums.ProductCustomerMatchResponseErrorEnum;
import com.cvte.product.test.enums.ProductProInfoResponseErrorEnum;
import com.cvte.product.test.enums.ProductProInfoVerificationErrorEnum;
import com.cvte.product.test.exception.CustomGlobalException;
import com.cvte.product.test.exception.VerificationException;
import com.cvte.product.test.mapper.ProductCustomerMatchMapper;
import com.cvte.product.test.service.ProductCustomerMatchService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 聂裴涵
 * @since 2022-08-08
 */
@Service
public class ProductCustomerMatchServiceImpl extends ServiceImpl<ProductCustomerMatchMapper, ProductCustomerMatchEntity> implements ProductCustomerMatchService {
    @Autowired
    ProductCustomerMatchMapper productCustomerMatchMapper;
    /**
     * @description: 根据分页信息和模糊搜索keyword，获取模块并完成分页
     * @author: 聂裴涵
     * @date: 2022/8/9 4:18 PM
     * @return: com.cvte.product.test.common.MyPage<com.cvte.product.test.Vo.ProductCustomerMatchVo>
     **/
    @Override
    public MyPage<ProductCustomerMatchVo> getModuleAll(Integer page, Integer pageSize, String keyword, String orderBy, String order) {
        // 进行分页数据合法性的校验
        boolean isLegal = page < 1 || pageSize < 1 || (!"ASC".equals(order) && !"DESC".equals(order));
        if (isLegal) {
            throw new VerificationException(ProductProInfoVerificationErrorEnum.PAGE_DATA_ERROR.getCode(), ProductProInfoVerificationErrorEnum.PAGE_DATA_ERROR.getMsg());
        }
        // 进行模糊查询字符串的拼接工作
        QueryWrapper<ProductCustomerMatchEntity> queryWrapper = new QueryWrapper<>();
        if (StringUtils.hasLength(keyword)){
            queryWrapper.like("module_name",keyword).or().like("customer_name",keyword)
                    .or().like("customer_part_num",keyword).or().like("customer_batch_num",keyword)
                    .or().like("customer_brand",keyword).or().like("country",keyword);
        }
        if ("DESC".equals(order)){
            queryWrapper.orderByDesc(orderBy);
        }else{
            queryWrapper.orderByAsc(orderBy);
        }

        // 完成查询以及分类的包装
        Page<ProductCustomerMatchEntity> pages = new Page<>(page, pageSize);
        IPage<ProductCustomerMatchEntity> iPage = this.page(pages, queryWrapper);

        MyPage<ProductCustomerMatchVo> productCustomerMatchVoMyPage = new MyPage<>();
        List<ProductCustomerMatchVo> productCustomerMatchVos = iPage.getRecords().stream().map(item -> {
            ProductCustomerMatchVo productCustomerMatchVo = new ProductCustomerMatchVo();
            BeanUtils.copyProperties(item, productCustomerMatchVo);
            return productCustomerMatchVo;
        }).collect(Collectors.toList());
        productCustomerMatchVoMyPage.setList(productCustomerMatchVos);
        MyPage.Pagination pagination = productCustomerMatchVoMyPage.getPagination();
        pagination.setPageNum(iPage.getCurrent());
        pagination.setTotal(iPage.getTotal());
        pagination.setPages(iPage.getPages());
        pagination.setPageSize(iPage.getSize());
        return productCustomerMatchVoMyPage;
    }

    /**
     * @description: 根据ID查询模块信息
     * @author: 聂裴涵
     * @date: 2022/8/9 4:19 PM
     * @return: com.cvte.product.test.Vo.ProductCustomerMatchVo
     **/
    @Override
    public ProductCustomerMatchVo getModuleById(String moduleId) {
        if (!StringUtils.hasLength(moduleId)) {
            throw new VerificationException(ProductProInfoVerificationErrorEnum.PRODUCT_ID_NULL_ERROR.getCode(), ProductProInfoVerificationErrorEnum.PRODUCT_ID_NULL_ERROR.getMsg());
        }
        ProductCustomerMatchEntity productCustomerMatchEntity = productCustomerMatchMapper.selectById(moduleId);
        if (productCustomerMatchEntity == null) {
            throw new CustomGlobalException(ProductCustomerMatchResponseErrorEnum.SELECT_ERROR.getCode(), ProductProInfoResponseErrorEnum.SELECT_ERROR.getMsg());
        }
        ProductCustomerMatchVo productCustomerMatchVo = new ProductCustomerMatchVo();
        BeanUtils.copyProperties(productCustomerMatchEntity, productCustomerMatchVo);
        return productCustomerMatchVo;
    }

    /**
     * @description: 插入模块信息
     * @author: 聂裴涵
     * @date: 2022/8/9 4:20 PM
     * @return: void
     **/
    @Override
    public void insertModule(ProductCustomerMatchVo productCustomerMatchVo, String updHost) {
        //判断产品是否存在
        if (productCustomerMatchMapper.selectCount(new QueryWrapper<ProductCustomerMatchEntity>().eq("moduleName", productCustomerMatchVo.getModuleName()))> 0) {
            throw new CustomGlobalException(ProductCustomerMatchResponseErrorEnum.INSERT_EXIST_ERROR.getCode(), ProductCustomerMatchResponseErrorEnum.INSERT_EXIST_ERROR.getMsg());
        }

        productCustomerMatchVo.setUpdHost(updHost);
        ProductCustomerMatchEntity productCustomerMatchEntity = new ProductCustomerMatchEntity();
        productCustomerMatchEntity.setVersion(1);
        BeanUtils.copyProperties(productCustomerMatchVo, productCustomerMatchEntity);
        productCustomerMatchEntity.setCrtHost(updHost);
        productCustomerMatchEntity.setCrtName(productCustomerMatchEntity.getUpdName());
        productCustomerMatchEntity.setCrtUser(productCustomerMatchEntity.getUpdUser());

        int changedRows = productCustomerMatchMapper.insert(productCustomerMatchEntity);
        if (changedRows < 1) {
            throw new CustomGlobalException(ProductCustomerMatchResponseErrorEnum.INSERT_ERROR.getCode(), ProductCustomerMatchResponseErrorEnum.INSERT_ERROR.getMsg());
        }
    }
    /**
     * @description: 根据模块ID删除模块信息
     * @author: 聂裴涵
     * @date: 2022/8/9 4:20 PM
     * @return: void
     **/
    @Override
    public void deleteModuleById(String moduleId) {
        if (!StringUtils.hasLength(moduleId)) {
            throw new VerificationException(ProductProInfoVerificationErrorEnum.PRODUCT_ID_NULL_ERROR.getCode(), ProductProInfoVerificationErrorEnum.PRODUCT_ID_NULL_ERROR.getMsg());
        }
        int changedRows = productCustomerMatchMapper.deleteById(moduleId);
        if (changedRows < 1) {
            throw new CustomGlobalException(ProductCustomerMatchResponseErrorEnum.DELETE_ERROR.getCode(), ProductCustomerMatchResponseErrorEnum.DELETE_ERROR.getMsg());
        }

    }

    /**
     * @description: 根据模块ID更新模块信息
     * @author: 聂裴涵
     * @date: 2022/8/9 4:20 PM
     * @return: com.cvte.product.test.Vo.ProductCustomerMatchVo
     **/
    @Override
    public ProductCustomerMatchVo updateModuleById(String moduleId, ProductCustomerMatchVo productCustomerMatchVo) {
        if (!StringUtils.hasLength(moduleId)) {
            throw new VerificationException(ProductProInfoVerificationErrorEnum.PRODUCT_ID_NULL_ERROR.getCode(), ProductProInfoVerificationErrorEnum.PRODUCT_ID_NULL_ERROR.getMsg());
        }
        ProductCustomerMatchEntity productCustomerMatchEntity = new ProductCustomerMatchEntity();
        productCustomerMatchEntity.setModuleId(moduleId);
        BeanUtils.copyProperties(productCustomerMatchVo, productCustomerMatchEntity);
        int changedRows = productCustomerMatchMapper.updateById(productCustomerMatchEntity);
        if (changedRows < 1) {
            throw new CustomGlobalException(ProductCustomerMatchResponseErrorEnum.UPDATE_ERROR.getCode(), ProductCustomerMatchResponseErrorEnum.UPDATE_ERROR.getMsg());
        }
        ProductCustomerMatchEntity  productCustomerMatch = productCustomerMatchMapper.selectById(moduleId);
        BeanUtils.copyProperties(productCustomerMatch, productCustomerMatchVo);
        return productCustomerMatchVo;
    }



}
