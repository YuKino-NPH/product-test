package com.cvte.product.test.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cvte.product.test.Vo.ProductCustomerMatchVo;
import com.cvte.product.test.Vo.ProductProInfoVo;
import com.cvte.product.test.common.MyPage;
import com.cvte.product.test.entity.PartModuleRelationEntity;
import com.cvte.product.test.entity.ProCustomerRelationEntity;
import com.cvte.product.test.entity.ProductCustomerMatchEntity;
import com.cvte.product.test.entity.ProductProInfoEntity;
import com.cvte.product.test.enums.ProductCustomerMatchResponseErrorEnum;
import com.cvte.product.test.enums.ProductProInfoResponseErrorEnum;
import com.cvte.product.test.enums.ProductProInfoVerificationErrorEnum;
import com.cvte.product.test.exception.CustomGlobalException;
import com.cvte.product.test.exception.VerificationException;
import com.cvte.product.test.mapper.ProductCustomerMatchMapper;
import com.cvte.product.test.service.PartModuleRelationService;
import com.cvte.product.test.service.ProCustomerRelationService;
import com.cvte.product.test.service.ProductCustomerMatchService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cvte.product.test.service.ProductProInfoService;
import com.cvte.product.test.utils.BaseVoToBaseEntityUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.print.DocFlavor;
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
    @Autowired
    PartModuleRelationService partModuleRelationService;
    @Autowired
    ProCustomerRelationService proCustomerRelationService;
    @Autowired
    ProductProInfoService productProInfoService;

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
        if (StringUtils.hasLength(keyword)) {
            queryWrapper.eq("module_name", keyword).or().eq("customer_name", keyword)
                    .or().eq("customer_batch_num", keyword)
                    .or().eq("customer_brand", keyword).or().eq("country", keyword);
        }
        if ("DESC".equals(order)) {
            queryWrapper.orderByDesc(orderBy);
        } else {
            queryWrapper.orderByAsc(orderBy);
        }

        // 完成查询以及分类的包装
        Page<ProductCustomerMatchEntity> pages = new Page<>(page, pageSize);
        IPage<ProductCustomerMatchEntity> iPage = this.page(pages, queryWrapper);

        MyPage<ProductCustomerMatchVo> productCustomerMatchVoMyPage = new MyPage<>();
        List<ProductCustomerMatchVo> productCustomerMatchVos = iPage.getRecords().stream().map(item -> {
            ProductCustomerMatchVo productCustomerMatchVo = new ProductCustomerMatchVo();
            BeanUtils.copyProperties(item, productCustomerMatchVo);
            // 封装proIds和PartNums
            List<String> proIds = proCustomerRelationService.list(new QueryWrapper<ProCustomerRelationEntity>().eq("module_id", item.getModuleId()))
                    .stream().map(ProCustomerRelationEntity::getProId).collect(Collectors.toList());
            productCustomerMatchVo.setProIds(proIds);

            List<String> partNums = partModuleRelationService.list(new QueryWrapper<PartModuleRelationEntity>().eq("customer_module_id", item.getModuleId()))
                    .stream().map(PartModuleRelationEntity::getPartNumId).collect(Collectors.toList());
            productCustomerMatchVo.setCustomerPartNums(partNums);

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
        List<String> proIds = proCustomerRelationService.list(new QueryWrapper<ProCustomerRelationEntity>().eq("module_id", moduleId))
                .stream().map(ProCustomerRelationEntity::getProId).collect(Collectors.toList());
        productCustomerMatchVo.setProIds(proIds);

        List<String> partNums = partModuleRelationService.list(new QueryWrapper<PartModuleRelationEntity>().eq("customer_module_id", moduleId))
                .stream().map(PartModuleRelationEntity::getPartNumId).collect(Collectors.toList());
        productCustomerMatchVo.setCustomerPartNums(partNums);
        return productCustomerMatchVo;
    }

    /**
     * @description: 插入模块信息
     * @author: 聂裴涵
     * @date: 2022/8/9 4:20 PM
     * @return: void
     **/
    @Override
    @Transactional(rollbackFor = {CustomGlobalException.class})
    public ProductCustomerMatchVo insertModule(ProductCustomerMatchVo productCustomerMatchVo ) {
        boolean isVerification=StringUtils.hasLength(productCustomerMatchVo.getCustomerBrand())&&StringUtils.hasLength(productCustomerMatchVo.getCustomerName())
                &&StringUtils.hasLength(productCustomerMatchVo.getUpdName())&&StringUtils.hasLength(productCustomerMatchVo.getUpdHost())
                &&StringUtils.hasLength(productCustomerMatchVo.getUpdUser())&&StringUtils.hasLength(productCustomerMatchVo.getCustomerBatchNum())
                &&StringUtils.hasLength(productCustomerMatchVo.getCountry())&&StringUtils.hasLength(productCustomerMatchVo.getModuleName())
                &&productCustomerMatchVo.getProIds()!=null&&productCustomerMatchVo.getCustomerPartNums()!=null;
        if (!isVerification){
            throw new VerificationException(ProductCustomerMatchResponseErrorEnum.INSERT_NOT_PARAMETER.getCode(), ProductCustomerMatchResponseErrorEnum.INSERT_NOT_PARAMETER.getMsg());
        }
        ProductCustomerMatchEntity productCustomerMatchEntity = new ProductCustomerMatchEntity();
        BeanUtils.copyProperties(productCustomerMatchVo, productCustomerMatchEntity);
        productCustomerMatchEntity.setIsDeleted(0);
        productCustomerMatchEntity.setVersion(1L);
        BaseVoToBaseEntityUtil.baseVoToBaseEntityInsert(productCustomerMatchVo,productCustomerMatchEntity);

        int changedRows = productCustomerMatchMapper.insert(productCustomerMatchEntity);
        if (changedRows < 1) {
            throw new CustomGlobalException(ProductCustomerMatchResponseErrorEnum.INSERT_ERROR.getCode(), ProductCustomerMatchResponseErrorEnum.INSERT_ERROR.getMsg());
        }
        //获取插入的ID
        //更改产品关联表
        proCustomerRelationService.insert(productCustomerMatchVo, productCustomerMatchEntity.getModuleId());
        //更新料号表
        partModuleRelationService.insert(productCustomerMatchVo, productCustomerMatchEntity.getModuleId());

        productCustomerMatchVo.setModuleId(productCustomerMatchEntity.getModuleId());
        return productCustomerMatchVo;
    }

    /**
     * @description: 根据模块ID删除模块信息
     * @author: 聂裴涵
     * @date: 2022/8/9 4:20 PM
     * @return: void
     **/
    @Override
    @Transactional(rollbackFor = {CustomGlobalException.class})
    public void deleteModuleById(String moduleId) {
        if (!StringUtils.hasLength(moduleId)) {
            throw new VerificationException(ProductProInfoVerificationErrorEnum.PRODUCT_ID_NULL_ERROR.getCode(), ProductProInfoVerificationErrorEnum.PRODUCT_ID_NULL_ERROR.getMsg());
        }
        int changedRows = productCustomerMatchMapper.deleteById(moduleId);
        if (changedRows < 1) {
            throw new CustomGlobalException(ProductCustomerMatchResponseErrorEnum.DELETE_ERROR.getCode(), ProductCustomerMatchResponseErrorEnum.DELETE_ERROR.getMsg());
        }
        //删除moduleID下对应的产品关联表
        proCustomerRelationService.remove(new QueryWrapper<ProCustomerRelationEntity>().eq("module_id",moduleId));
        //删除moduleID下对应的料号关联表
        partModuleRelationService.remove(new QueryWrapper<PartModuleRelationEntity>().eq("customer_module_id",moduleId));
    }

    /**
     * @description: 根据模块ID更新模块信息
     * @author: 聂裴涵
     * @date: 2022/8/9 4:20 PM
     * @return: com.cvte.product.test.Vo.ProductCustomerMatchVo
     **/
    @Override
    @Transactional(rollbackFor = {CustomGlobalException.class})
    public ProductCustomerMatchVo updateModuleById(String moduleId, ProductCustomerMatchVo productCustomerMatchVo) {
        if (!StringUtils.hasLength(moduleId)) {
            throw new VerificationException(ProductProInfoVerificationErrorEnum.PRODUCT_ID_NULL_ERROR.getCode(), ProductProInfoVerificationErrorEnum.PRODUCT_ID_NULL_ERROR.getMsg());
        }
        ProductCustomerMatchEntity productCustomerMatchEntity = new ProductCustomerMatchEntity();
        productCustomerMatchVo.setModuleId(moduleId);
        BeanUtils.copyProperties(productCustomerMatchVo, productCustomerMatchEntity);
        int changedRows = productCustomerMatchMapper.updateById(productCustomerMatchEntity);
        if (changedRows < 1) {
            throw new CustomGlobalException(ProductCustomerMatchResponseErrorEnum.UPDATE_ERROR.getCode(), ProductCustomerMatchResponseErrorEnum.UPDATE_ERROR.getMsg());
        }
        ProductCustomerMatchEntity productCustomerMatch = productCustomerMatchMapper.selectById(moduleId);
        BeanUtils.copyProperties(productCustomerMatch, productCustomerMatchVo);
        //关联的产品ID更改
        if (productCustomerMatchVo.getProIds()!=null&&productCustomerMatchVo.getProIds().size()>0){
            proCustomerRelationService.updateProIds(productCustomerMatchVo);
        }
        //关联的料号更改
        if (productCustomerMatchVo.getCustomerPartNums()!=null&&productCustomerMatchVo.getCustomerPartNums().size()>0){
            partModuleRelationService.updatePartNums(productCustomerMatchVo);
        }
        return productCustomerMatchVo;
    }

    /**
     * @description: 根据传入不固定的参数，匹配最精确的信息
     * @author: 聂裴涵
     * @date: 2022/8/10 9:15 AM
     * @return: java.util.List<com.cvte.product.test.Vo.ProductProInfoVo>
     **/
    @Override
    @Transactional(rollbackFor = {CustomGlobalException.class})
    public List<ProductProInfoVo> getProductFuzzyMatch(String customerName, String customerPartNum, String customerBatchNum, String country, String moduleName) {
        //解析customerBatchNum  解析
        String[] customerBatchNums = customerBatchNum==null?null:customerBatchNum.split("-");

        //参数检查 不允许传入无参数的情况
        boolean isVerification=!StringUtils.hasLength(customerBatchNum)&&!StringUtils.hasLength(customerName)&&!StringUtils.hasLength(customerPartNum)&&!StringUtils.hasLength(moduleName);
        if (isVerification){
            throw new VerificationException(ProductCustomerMatchResponseErrorEnum.SELECT_FUZZY_NOT_PARAMETER.getCode(), ProductCustomerMatchResponseErrorEnum.SELECT_FUZZY_NOT_PARAMETER.getMsg());
        }

        // 动态拼接参数，查出产品ID
        List<String> proIdList;

        if (!StringUtils.hasLength(customerPartNum)) {
            proIdList=productCustomerMatchMapper.selectProIdsByFuzzyMatchNotPartNum(moduleName, customerName, customerBatchNums, country);
        } else {
            // 动态拼接参数，查出产品ID
            proIdList = productCustomerMatchMapper.selectProIdsByFuzzyMatch(moduleName, customerName, customerPartNum, customerBatchNums, country);
        }
        if (proIdList.size()<1){
            throw new CustomGlobalException(ProductCustomerMatchResponseErrorEnum.SELECT_FUZZY_MATCH_ERROR.getCode(), ProductCustomerMatchResponseErrorEnum.SELECT_FUZZY_MATCH_ERROR.getMsg());
        }
        //根据产品ID，查出所有产品信息，并完成封装
        List<ProductProInfoEntity> productProInfoEntities = productProInfoService.list(new QueryWrapper<ProductProInfoEntity>().in("pro_id", proIdList));

        List<ProductProInfoVo> productProInfoVos = productProInfoEntities.stream().map(item -> {
            ProductProInfoVo productProInfoVo = new ProductProInfoVo();
            BeanUtils.copyProperties(item, productProInfoVo);
            return productProInfoVo;
        }).collect(Collectors.toList());
        return productProInfoVos;
    }

    /**
     * @description: 批量插入数据
     * @author: 聂裴涵
     * @date: 2022/8/11 2:08 PM
     * @return: void
     **/
    @Override
    @Transactional(rollbackFor = {CustomGlobalException.class})
    public void insertModuleBatch(List<ProductCustomerMatchVo> productCustomerMatchVos, String s) {

        List<ProductCustomerMatchEntity> customerMatchEntities = productCustomerMatchVos.stream().map(item -> {
            ProductCustomerMatchEntity productCustomerMatchEntity = new ProductCustomerMatchEntity();
            BeanUtils.copyProperties(item, productCustomerMatchEntity);
            productCustomerMatchEntity.setIsDeleted(0);
            productCustomerMatchEntity.setVersion(1L);
            productCustomerMatchEntity.setCrtHost(s);
            productCustomerMatchEntity.setCrtName(productCustomerMatchEntity.getUpdName());
            productCustomerMatchEntity.setCrtUser(productCustomerMatchEntity.getUpdUser());
            return productCustomerMatchEntity;
        }).collect(Collectors.toList());


        boolean isSaveBatch = this.saveBatch(customerMatchEntities);
        if (!isSaveBatch) {
            throw new CustomGlobalException(ProductCustomerMatchResponseErrorEnum.INSERT_ERROR.getCode(), ProductCustomerMatchResponseErrorEnum.INSERT_ERROR.getMsg());
        }
        //获取插入的ID
        //更改产品关联表
        proCustomerRelationService.insertBatch(productCustomerMatchVos);
        //更新料号表
        partModuleRelationService.insertBatch(productCustomerMatchVos);
    }


}
