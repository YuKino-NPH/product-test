package com.cvte.product.test.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 客户产品模版与客户料号关系表
 * </p>
 *
 * @author 聂裴涵
 * @since 2022-08-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName(value = "part_module_relation")
@ApiModel(value="PartModuleRelation对象", description="客户产品模版与客户料号关系表")
public class PartModuleRelationEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "part_module_id", type = IdType.ASSIGN_UUID)
    private String partModuleId;

    @ApiModelProperty(value = "客户产品模版ID")
    private String customerModuleId;

    @ApiModelProperty(value = "客户料号ID")
    private String partNumId;



}
