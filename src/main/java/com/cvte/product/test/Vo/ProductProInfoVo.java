package com.cvte.product.test.Vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author cvte
 * @date 2022/8/8 3:26 PM
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(description="返回给前端的产品信息数据")
public class ProductProInfoVo extends BaseVo{
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
