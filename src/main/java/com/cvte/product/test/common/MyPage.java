package com.cvte.product.test.common;

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
