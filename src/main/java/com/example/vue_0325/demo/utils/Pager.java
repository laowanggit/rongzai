package com.example.vue_0325.demo.utils;

/**
 * @author shkstart
 * @create 2019-03-27 14:03
 */
public class Pager {
    //分页工具类
    private Integer limit;
    private Integer offset;

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }
}
