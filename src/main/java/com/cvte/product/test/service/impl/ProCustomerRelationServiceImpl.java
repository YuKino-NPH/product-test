package com.cvte.product.test.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cvte.product.test.Vo.ProductCustomerMatchVo;
import com.cvte.product.test.entity.PartModuleRelationEntity;
import com.cvte.product.test.entity.ProCustomerRelationEntity;
import com.cvte.product.test.enums.ProductCustomerMatchResponseErrorEnum;
import com.cvte.product.test.enums.ProductProInfoResponseErrorEnum;
import com.cvte.product.test.exception.CustomGlobalException;
import com.cvte.product.test.exception.VerificationException;
import com.cvte.product.test.mapper.ProCustomerRelationMapper;
import com.cvte.product.test.service.ProCustomerRelationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cvte.product.test.utils.BaseVoToBaseEntityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 产品和客户关系表 服务实现类
 * </p>
 *
 * @author 聂裴涵
 * @since 2022-08-09
 */
@Service
public class ProCustomerRelationServiceImpl extends ServiceImpl<ProCustomerRelationMapper, ProCustomerRelationEntity> implements ProCustomerRelationService {
    @Autowired
    ProCustomerRelationMapper proCustomerRelationMapper;

    @Override
    public void insert(ProductCustomerMatchVo productCustomerMatchVo, String moduleId) {
        boolean isVerification= StringUtils.hasLength(moduleId)&&productCustomerMatchVo.getProIds()!=null;
        if (!isVerification){
            throw new VerificationException(ProductCustomerMatchResponseErrorEnum.INSERT_ERROR.getCode(), ProductCustomerMatchResponseErrorEnum.INSERT_ERROR.getMsg());
        }

        List<ProCustomerRelationEntity> moduleRelationEntities = productCustomerMatchVo.getProIds().stream().map(item -> {
            ProCustomerRelationEntity proCustomerRelationEntity = new ProCustomerRelationEntity();
            proCustomerRelationEntity.setProId(item);
            proCustomerRelationEntity.setModuleId(moduleId);
            proCustomerRelationEntity.setIsDeleted(0);
            proCustomerRelationEntity.setVersion(1L);
            proCustomerRelationEntity.setCrtUser(productCustomerMatchVo.getUpdUser());
            proCustomerRelationEntity.setCrtHost(productCustomerMatchVo.getUpdHost());
            proCustomerRelationEntity.setCrtName(productCustomerMatchVo.getUpdName());
            proCustomerRelationEntity.setUpdUser(productCustomerMatchVo.getUpdUser());
            proCustomerRelationEntity.setUpdName(productCustomerMatchVo.getUpdName());
            proCustomerRelationEntity.setUpdHost(productCustomerMatchVo.getUpdHost());

            return proCustomerRelationEntity;
        }).collect(Collectors.toList());
        boolean isSaveBatch = this.saveBatch(moduleRelationEntities);
        if (!isSaveBatch) {
            throw new CustomGlobalException(ProductProInfoResponseErrorEnum.PART_BATCH_INSERT_ERROR.getCode(), ProductProInfoResponseErrorEnum.PART_BATCH_INSERT_ERROR.getMsg());
        }
    }

    @Override
    public void insertBatch(List<ProductCustomerMatchVo> productCustomerMatchVos) {
        ArrayList<ProCustomerRelationEntity> proCustomerRelationEntities = new ArrayList<>();
        productCustomerMatchVos.forEach(item -> {
            item.getProIds().forEach(temp -> {
                ProCustomerRelationEntity proCustomerRelationEntity = new ProCustomerRelationEntity();
                proCustomerRelationEntity.setProId(temp);
                proCustomerRelationEntity.setModuleId(item.getModuleId());
                proCustomerRelationEntity.setIsDeleted(0);
                proCustomerRelationEntity.setVersion(1L);
                proCustomerRelationEntity.setCrtUser(item.getUpdUser());
                proCustomerRelationEntity.setCrtHost(item.getUpdHost());
                proCustomerRelationEntity.setCrtName(item.getUpdName());
                proCustomerRelationEntity.setUpdUser(item.getUpdUser());
                proCustomerRelationEntity.setUpdName(item.getUpdName());
                proCustomerRelationEntity.setUpdHost(item.getUpdHost());
                proCustomerRelationEntities.add(proCustomerRelationEntity);
            });
        });
        boolean isSaveBatch = this.saveBatch(proCustomerRelationEntities);
        if (!isSaveBatch) {
            throw new CustomGlobalException(ProductProInfoResponseErrorEnum.PART_BATCH_INSERT_ERROR.getCode(), ProductProInfoResponseErrorEnum.PART_BATCH_INSERT_ERROR.getMsg());
        }
    }

    /**
     * @description: 更新产品和客户关联表
     * @author: 聂裴涵
     * @date: 2022/8/11 10:11 AM
     * @return: void
     **/
    @Override
    public void updateProIds(ProductCustomerMatchVo productCustomerMatchVo) {
        List<ProCustomerRelationEntity> proCustomerRelationEntities = proCustomerRelationMapper.selectList(new QueryWrapper<ProCustomerRelationEntity>().eq("module_id", productCustomerMatchVo.getModuleId()));
        //先删除所有不在产品ID集合中的数据
        HashSet<String> proIdsSet = new HashSet<>(productCustomerMatchVo.getProIds());
        List<String> deletedProCustomerEntity = proCustomerRelationEntities.stream().filter(item -> !proIdsSet.contains(item.getProId()))
                .map(ProCustomerRelationEntity::getProCustomerId)
                .collect(Collectors.toList());

        proCustomerRelationMapper.delete(new QueryWrapper<ProCustomerRelationEntity>().in("pro_customer_id", deletedProCustomerEntity));
        //插入新的产品ID数据
        for (ProCustomerRelationEntity item : proCustomerRelationEntities) {
            proIdsSet.remove(item.getProId());
        }
        List<ProCustomerRelationEntity> insertProCustomerRelationEntities = proIdsSet.stream().map(item -> {
            ProCustomerRelationEntity proCustomerRelationEntity = new ProCustomerRelationEntity();
            proCustomerRelationEntity.setProId(item);
            proCustomerRelationEntity.setVersion(1L);
            proCustomerRelationEntity.setIsDeleted(0);
            proCustomerRelationEntity.setModuleId(productCustomerMatchVo.getModuleId());
            BaseVoToBaseEntityUtil.baseVoToBaseEntityInsert(productCustomerMatchVo, proCustomerRelationEntity);
            return proCustomerRelationEntity;
        }).collect(Collectors.toList());
        this.saveBatch(insertProCustomerRelationEntities);
    }
}
