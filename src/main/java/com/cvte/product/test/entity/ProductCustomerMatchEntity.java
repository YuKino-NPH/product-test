package com.cvte.product.test.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author 聂裴涵
 * @since 2022-08-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="ProductCustomerMatch对象", description="")
public class ProductCustomerMatchEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "模块代码")
    @TableId(value = "module_id", type = IdType.ASSIGN_UUID)
    private String moduleId;

    @ApiModelProperty(value = "模块名称")
    private String moduleName;

    @ApiModelProperty(value = "客户名称")
    private String customerName;

    @ApiModelProperty(value = "客户料号")
    private String customerPartNum;

    @ApiModelProperty(value = "客户批次号")
    private String customerBatchNum;

    @ApiModelProperty(value = "客户品牌")
    private String customerBrand;

    @ApiModelProperty(value = "国家")
    private String country;

    @ApiModelProperty(value = "与产品表关联的ID")
    private String proModuleId;




}
