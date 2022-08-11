package com.cvte.product.test.controller;


import com.cvte.product.test.Vo.ProductCustomerMatchVo;
import com.cvte.product.test.Vo.ProductProInfoVo;
import com.cvte.product.test.Vo.ResultVo;
import com.cvte.product.test.common.MyPage;
import com.cvte.product.test.exception.VerificationException;
import com.cvte.product.test.service.ProductCustomerMatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 聂裴涵
 * @since 2022-08-08
 */
@RestController
@RequestMapping("/product-customer-match")
public class ProductCustomerMatchController {
    @Autowired
    ProductCustomerMatchService productCustomerMatchService;

    /**
     * @description: 查询所有的模块信息，并完成分页
     * @author: 聂裴涵
     * @date: 2022/8/9 9:57 AM
     * @return: com.cvte.product.test.Vo.ResultVo
     **/
    @GetMapping()
    public ResultVo getProductAll(@RequestParam(value = "page", defaultValue = "1",required = false) Integer page, @RequestParam(value = "keyword", defaultValue = "",required = false) String keyword,
                                  @RequestParam(value = "pageSize", defaultValue = "10",required = false) Integer pageSize, @RequestParam(value = "orderBy", defaultValue = "module_id",required = false) String orderBy,
                                  @RequestParam(value = "order", defaultValue = "ASC",required = false) String order) {
        MyPage<ProductCustomerMatchVo> productCustomerMatchVoMyPage = productCustomerMatchService.getModuleAll(page, pageSize, keyword, orderBy, order);
        return ResultVo.ok().put(productCustomerMatchVoMyPage);
    }

    /**
     * @description: 根据模块ID查询模块信息
     * @author: 聂裴涵
     * @date: 2022/8/9 9:56 AM
     * @return: com.cvte.product.test.Vo.ResultVo
     **/
    @GetMapping("/{moduleId}")
    public ResultVo getModuleById(@PathVariable("moduleId") String moduleId) {
        ProductCustomerMatchVo productCustomerMatchVo = productCustomerMatchService.getModuleById(moduleId);
        return ResultVo.ok().put(productCustomerMatchVo);
    }

    /**
     * @description: 新增模块信息
     * @author: 聂裴涵
     * @date: 2022/8/9 9:19 AM
     * @return: com.cvte.product.test.Vo.ResultVo
     **/
    @PostMapping()
    public ResultVo insertModule(@RequestBody ProductCustomerMatchVo productCustomerMatchVo) {
        ProductCustomerMatchVo customerMatchVo = productCustomerMatchService.insertModule(productCustomerMatchVo);
        return ResultVo.ok().put(customerMatchVo);
    }

    /**
     * @description: 根据模块ID逻辑删除模块信息
     * @author: 聂裴涵
     * @date: 2022/8/9 9:34 AM
     * @return: com.cvte.product.test.Vo.ResultVo
     **/
    @DeleteMapping("/{moduleId}")
    public ResultVo deleteModuleById(@PathVariable("moduleId") String moduleId) throws VerificationException {
        productCustomerMatchService.deleteModuleById(moduleId);
        return ResultVo.ok();
    }

    /**
     * @description: 根据模块ID，修改模块信息
     * @author: 聂裴涵
     * @date: 2022/8/9 9:58 AM
     * @return: com.cvte.product.test.Vo.ResultVo
     **/
    @PutMapping("/{moduleId}")
    public ResultVo updateModuleById(@PathVariable("moduleId") String moduleId, @RequestBody ProductCustomerMatchVo productCustomerMatchVo) {
        ProductCustomerMatchVo newProductCustomerMatchVo = productCustomerMatchService.updateModuleById(moduleId, productCustomerMatchVo);
        return ResultVo.ok().put(newProductCustomerMatchVo);
    }

    @GetMapping("/fuzzyMatch")
    public ResultVo getProductFuzzyMatch(@RequestParam(value = "moduleName",defaultValue = "",required = false) String moduleName,
                                         @RequestParam(value = "customerName", defaultValue = "",required = false) String customerName,
                                         @RequestParam(value = "customerPartNum", defaultValue = "",required = false) String customerPartNum,
                                         @RequestParam(value = "customerBatchNum", defaultValue = "",required = false) String customerBatchNum,
                                         @RequestParam(value = "country", defaultValue = "",required = false) String country) {
        List<ProductProInfoVo> proInfoVoList = productCustomerMatchService.getProductFuzzyMatch(customerName,customerPartNum,customerBatchNum,country,moduleName);
        return ResultVo.ok().put(proInfoVoList);
    }
}
