package com.cvte.product.test.common;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

import java.util.List;

/**
 * @author 聂裴涵
 * @date 2022/8/9 10:50 AM
 */
@Data
public class MyPage<T>{
    private List<T> list;
    private Pagination pagination;

    public MyPage() {
        pagination= new Pagination();
    }
    public void setPaginationByIPage(IPage iPage){
        pagination.setPageNum(iPage.getCurrent());
        pagination.setTotal(iPage.getTotal());
        pagination.setPages(iPage.getPages());
        pagination.setPageSize(iPage.getSize());
    }

    @Data
    public static class Pagination{
        private Long pageNum;
        private Long pageSize;
        private Long total;
        private Long pages;

        public Pagination() {
        }
    }
}
