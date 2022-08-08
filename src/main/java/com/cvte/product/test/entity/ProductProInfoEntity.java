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
 * 产品信息
 *
 * @author 聂裴涵
 * @since 2022-08-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="ProductProInfo对象", description="产品信息")
@TableName("product_pro_info")
public class ProductProInfoEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "产品代码")
    @TableId(value = "pro_id", type = IdType.ASSIGN_UUID)
    private String proId;

    @ApiModelProperty(value = "产品名称")
    private String proName;

    @ApiModelProperty(value = "产品型号")
    private String proModel;

    @ApiModelProperty(value = "销售型号")
    private String saleModel;

    @ApiModelProperty(value = "产品类型")
    private String proType;

    @ApiModelProperty(value = "生命周期")
    private String lifeCycle;

    @ApiModelProperty(value = "一级分类-大类")
    private String proOneCategory;

    @ApiModelProperty(value = "二级分类-中类")
    private String proTwoCategory;

    @ApiModelProperty(value = "三级分类-小类")
    private String proThreeCategory;


}
