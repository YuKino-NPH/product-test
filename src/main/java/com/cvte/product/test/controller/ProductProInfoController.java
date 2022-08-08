package com.cvte.product.test.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cvte.product.test.Vo.ProductProInfoVo;
import com.cvte.product.test.Vo.ResultVo;
import com.cvte.product.test.entity.ProductProInfoEntity;
import com.cvte.product.test.service.ProductProInfoService;
import com.cvte.product.test.utils.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 产品信息 前端控制器
 * </p>
 *
 * @author 聂裴涵
 * @since 2022-08-08
 */
@RestController
@RequestMapping("/product-pro-info")
public class ProductProInfoController {
    @Autowired
    ProductProInfoService productProInfoService;
    @GetMapping("/product")
    public ResultVo getProductAll(){
        IPage<ProductProInfoEntity> proInfoEntityIPage = productProInfoService.getProductAll();
        return ResultVo.ok().put(proInfoEntityIPage);
    }

    @PostMapping("/product")
    public ResultVo insertProduct(@RequestBody ProductProInfoVo productProInfoVo, HttpServletRequest request){
        productProInfoService.insertProduct(productProInfoVo, productProInfoVo.getUpdHost());
        return ResultVo.ok();
    }


}
