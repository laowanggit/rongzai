package com.example.vue_0325.demo.utils;

import java.util.List;


//bootstrap-table的分页工具类
public class ResultData {

    private long total;//总的记录数
    private List<?> rows;//当前页集合

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }

    public ResultData(long total, List<?> rows) {
        this.total = total;
        this.rows = rows;
    }

    public ResultData() {
    }
}
