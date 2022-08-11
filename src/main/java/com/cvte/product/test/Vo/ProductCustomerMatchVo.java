package com.cvte.product.test.Vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author 聂裴涵
 * @date 2022/8/9 3:02 PM
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(description="返回给前端的客户产品匹配模块信息数据")
public class ProductCustomerMatchVo extends BaseVo {
    @ApiModelProperty(value = "模块代码")
    private String moduleId;

    @ApiModelProperty(value = "模块名称")
    private String moduleName;

    @ApiModelProperty(value = "客户名称")
    private String customerName;

    @ApiModelProperty(value = "客户料号")
    private List<String> customerPartNums;

    @ApiModelProperty(value = "客户批次号")
    private String customerBatchNum;

    @ApiModelProperty(value = "客户品牌")
    private String customerBrand;

    @ApiModelProperty(value = "国家")
    private String country;
    @ApiModelProperty(value = "产品ID")
    private List<String> proIds;

}
