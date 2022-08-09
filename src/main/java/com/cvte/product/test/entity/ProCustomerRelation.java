package com.cvte.product.test.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 产品和客户关系表
 * </p>
 *
 * @author 聂裴涵
 * @since 2022-08-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="ProCustomerRelation对象", description="产品和客户关系表")
public class ProCustomerRelation extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "pro_customer_id", type = IdType.ASSIGN_UUID)
    private String proCustomerId;

    @ApiModelProperty(value = "产品ID")
    private String proId;

    @ApiModelProperty(value = "用户产品匹配模版ID")
    private String moduleId;


}
