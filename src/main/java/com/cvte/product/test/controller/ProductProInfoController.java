package com.cvte.product.test.controller;


import com.cvte.product.test.Vo.ProductProInfoVo;
import com.cvte.product.test.Vo.ResultVo;
import com.cvte.product.test.common.MyPage;

import com.cvte.product.test.exception.VerificationException;
import com.cvte.product.test.service.ProductProInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 产品信息 前端控制器
 * </p>
 *
 * @author 聂裴涵
 * @since 2022-08-08
 */
@RestController
@RequestMapping("/product-pro-info/product")
public class ProductProInfoController {
    @Autowired
    ProductProInfoService productProInfoService;
    /**
     * @description: 查询所有的产品信息，并完成分类
     * @author: 聂裴涵
     * @date: 2022/8/9 9:57 AM
     * @return: com.cvte.product.test.Vo.ResultVo
     **/
    @GetMapping()
    public ResultVo getProductAll(@RequestParam(value = "page",defaultValue = "1") Integer page,@RequestParam(value = "keyword",defaultValue = "")String keyword,
                                  @RequestParam(value="pageSize",defaultValue = "10") Integer pageSize,@RequestParam(value = "orderBy",defaultValue = "pro_id") String orderBy,
                                  @RequestParam(value="order",defaultValue = "ASC") String order){
        MyPage<ProductProInfoVo> proInfoVoPage = productProInfoService.getProductAll(page, pageSize, keyword, orderBy, order);
        return ResultVo.ok().put(proInfoVoPage);
    }
    /**
     * @description: 根据产品ID查询产品信息
     * @author: 聂裴涵
     * @date: 2022/8/9 9:56 AM
     * @return: com.cvte.product.test.Vo.ResultVo
     **/
    @GetMapping("/{proId}")
    public ResultVo getProductById(@PathVariable("proId") String proId){
        ProductProInfoVo productProInfoVo=productProInfoService.getProductById(proId);
        return ResultVo.ok().put(productProInfoVo);
    }

    /**
     * @description: 新增产品信息
     * @author: 聂裴涵
     * @date: 2022/8/9 9:19 AM
     * @return: com.cvte.product.test.Vo.ResultVo
     **/
    @PostMapping()
    public ResultVo insertProduct(@RequestBody ProductProInfoVo productProInfoVo){
        ProductProInfoVo proInfoVo = productProInfoService.insertProduct(productProInfoVo);
        return ResultVo.ok().put(proInfoVo);
    }
    /**
     * @description: 根据产品ID逻辑删除产品信息
     * @author: 聂裴涵
     * @date: 2022/8/9 9:34 AM
     * @return: com.cvte.product.test.Vo.ResultVo
     **/
    @DeleteMapping("/{proId}")
    public ResultVo deleteProductById(@PathVariable("proId") String proId) throws VerificationException {
        productProInfoService.deleteProductByProId(proId);
        return ResultVo.ok();
    }
    /**
     * @description: 根据产品ID，修改产品信息
     * @author: 聂裴涵
     * @date: 2022/8/9 9:58 AM
     * @return: com.cvte.product.test.Vo.ResultVo
     **/
    @PutMapping("/{proId}")
    public ResultVo updateProductById(@PathVariable("proId") String proId,@RequestBody ProductProInfoVo productProInfoVo){
        ProductProInfoVo newProductProInfoVo=productProInfoService.updateProductByProId(proId,productProInfoVo);
        return ResultVo.ok().put(newProductProInfoVo);
    }


}
