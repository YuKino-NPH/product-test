package com.cvte.product.test.utils;

import com.cvte.product.test.Vo.BaseVo;
import com.cvte.product.test.entity.BaseEntity;

/**
 * @author cvte
 * @date 2022/8/11 10:41 AM
 */
public class BaseVoToBaseEntityUtil {
    public static void baseVoToBaseEntityInsert(BaseVo baseVo,BaseEntity baseEntity){
        baseEntity.setCrtUser(baseVo.getUpdUser());
        baseEntity.setCrtName(baseVo.getUpdName());
        baseEntity.setCrtHost(baseVo.getUpdHost());
        baseEntity.setUpdUser(baseVo.getUpdUser());
        baseEntity.setUpdName(baseVo.getUpdName());
        baseEntity.setUpdHost(baseVo.getUpdHost());
    }
}
