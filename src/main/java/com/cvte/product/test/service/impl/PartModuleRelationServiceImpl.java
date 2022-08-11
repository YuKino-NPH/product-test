package com.cvte.product.test.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cvte.product.test.Vo.ProductCustomerMatchVo;
import com.cvte.product.test.entity.PartModuleRelationEntity;
import com.cvte.product.test.entity.ProCustomerRelationEntity;
import com.cvte.product.test.enums.ProductCustomerMatchResponseErrorEnum;
import com.cvte.product.test.enums.ProductProInfoResponseErrorEnum;
import com.cvte.product.test.exception.CustomGlobalException;
import com.cvte.product.test.exception.VerificationException;
import com.cvte.product.test.mapper.PartModuleRelationMapper;
import com.cvte.product.test.service.PartModuleRelationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cvte.product.test.utils.BaseVoToBaseEntityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 客户产品模版与客户料号关系表 服务实现类
 * </p>
 *
 * @author 聂裴涵
 * @since 2022-08-09
 */
@Service
public class PartModuleRelationServiceImpl extends ServiceImpl<PartModuleRelationMapper, PartModuleRelationEntity> implements PartModuleRelationService {

    @Autowired
    PartModuleRelationMapper partModuleRelationMapper;

    /**
     * @description: 插入产品客户关联表数据
     * @author: 聂裴涵
     * @date: 2022/8/11 2:44 PM
     * @return: void
     **/
    @Override
    public void insert(ProductCustomerMatchVo productCustomerMatchVo, String moduleId) {
        boolean isVerification= StringUtils.hasLength(moduleId)&&productCustomerMatchVo.getCustomerPartNums()!=null;
        if (!isVerification){
            throw new VerificationException(ProductCustomerMatchResponseErrorEnum.INSERT_ERROR.getCode(), ProductCustomerMatchResponseErrorEnum.INSERT_ERROR.getMsg());
        }
        ArrayList<PartModuleRelationEntity> partModuleRelationEntities = new ArrayList<>();
        List<PartModuleRelationEntity> moduleRelationEntities = productCustomerMatchVo.getCustomerPartNums().stream().map(item -> {
            PartModuleRelationEntity partModuleRelationEntity = new PartModuleRelationEntity();
            partModuleRelationEntity.setPartNumId(item);
            partModuleRelationEntity.setCustomerModuleId(moduleId);

            partModuleRelationEntity.setVersion(1L);
            partModuleRelationEntity.setIsDeleted(0);
            partModuleRelationEntity.setCrtUser(productCustomerMatchVo.getUpdUser());
            partModuleRelationEntity.setCrtHost(productCustomerMatchVo.getUpdHost());
            partModuleRelationEntity.setCrtName(productCustomerMatchVo.getUpdName());
            partModuleRelationEntity.setUpdUser(productCustomerMatchVo.getUpdUser());
            partModuleRelationEntity.setUpdName(productCustomerMatchVo.getUpdName());
            partModuleRelationEntity.setUpdHost(productCustomerMatchVo.getUpdHost());

            return partModuleRelationEntity;
        }).collect(Collectors.toList());
        boolean isSaveBatch = this.saveBatch(moduleRelationEntities);
        if (!isSaveBatch) {
            throw new CustomGlobalException(ProductProInfoResponseErrorEnum.PART_BATCH_INSERT_ERROR.getCode(), ProductProInfoResponseErrorEnum.PART_BATCH_INSERT_ERROR.getMsg());
        }
    }

    @Override
    public void insertBatch(List<ProductCustomerMatchVo> productCustomerMatchVos) {
        ArrayList<PartModuleRelationEntity> partModuleRelationEntities = new ArrayList<>();
        productCustomerMatchVos.forEach(item->{
            item.getCustomerPartNums().forEach(temp->{
                PartModuleRelationEntity partModuleRelationEntity = new PartModuleRelationEntity();
                partModuleRelationEntity.setPartNumId(temp);
                partModuleRelationEntity.setCustomerModuleId(item.getModuleId());

                partModuleRelationEntity.setVersion(1L);
                partModuleRelationEntity.setIsDeleted(0);
                BaseVoToBaseEntityUtil.baseVoToBaseEntityInsert(item,partModuleRelationEntity);
                partModuleRelationEntities.add(partModuleRelationEntity);
            });
        });
        boolean isSaveBatch = this.saveBatch(partModuleRelationEntities);
        if (!isSaveBatch) {
            throw new CustomGlobalException(ProductProInfoResponseErrorEnum.PART_BATCH_INSERT_ERROR.getCode(), ProductProInfoResponseErrorEnum.PART_BATCH_INSERT_ERROR.getMsg());
        }
    }

    @Override
    public void updatePartNums(ProductCustomerMatchVo productCustomerMatchVo) {
        List<PartModuleRelationEntity> partModuleRelationEntities = partModuleRelationMapper.selectList(new QueryWrapper<PartModuleRelationEntity>().eq("customer_module_id", productCustomerMatchVo.getModuleId()));
        //先删除所有不在产品ID集合中的数据
        HashSet<String> partNums = new HashSet<>(productCustomerMatchVo.getCustomerPartNums());
        List<String> deletedPartEntity = partModuleRelationEntities.stream().filter(item -> !partNums.contains(item.getPartNumId()))
                .map(PartModuleRelationEntity::getPartModuleId)
                .collect(Collectors.toList());

        partModuleRelationMapper.delete(new QueryWrapper<PartModuleRelationEntity>().in("part_module_id", deletedPartEntity));
        //插入新的产品ID数据
        for (PartModuleRelationEntity item : partModuleRelationEntities) {
            partNums.remove(item.getPartNumId());
        }
        List<PartModuleRelationEntity> insertPartCustomerRelationEntities = partNums.stream().map(item -> {
            PartModuleRelationEntity partModuleRelationEntity = new PartModuleRelationEntity();
            partModuleRelationEntity.setPartNumId(item);
            partModuleRelationEntity.setVersion(1L);
            partModuleRelationEntity.setIsDeleted(0);
            partModuleRelationEntity.setCustomerModuleId(productCustomerMatchVo.getModuleId());
            BaseVoToBaseEntityUtil.baseVoToBaseEntityInsert(productCustomerMatchVo, partModuleRelationEntity);
            return partModuleRelationEntity;
        }).collect(Collectors.toList());
        this.saveBatch(insertPartCustomerRelationEntities);
    }
}
