package com.cvte.product.test.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author cvte
 * @date 2022/8/8 2:27 PM
 */
@Data
@ApiModel(description = "Entity对象的公共属性的抽象类")
public abstract class BaseEntity {
    @ApiModelProperty(value = "创建人")
    private String crtUser;

    @ApiModelProperty(value = "创建人姓名")
    private String crtName;

    @ApiModelProperty(value = "创建人主机")
    private String crtHost;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date crtTime;

    @ApiModelProperty(value = "最后修改人")
    private String updUser;

    @ApiModelProperty(value = "最后修改人姓名")
    private String updName;

    @ApiModelProperty(value = "最后修改人主机")
    private String updHost;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "最后修改时间")
    private Date updTime;


    @TableLogic
    @ApiModelProperty(value = "是否删除")
    private Integer isDelete;

    @Version
    @ApiModelProperty(value = "版本号")
    private Integer version;
}
