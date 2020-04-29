package com.uetty.sample.springboot.constant;

import com.github.pagehelper.Page;

import java.util.List;

public class PagedResponseData<T> {

    Long total;
    Integer totalPage;
    List<T> list;

    public PagedResponseData(Page<T> page) {
        this.total = page.getTotal();
        this.totalPage = page.getPages();
        this.list = page.getResult();
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
