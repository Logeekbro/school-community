package com.db.dbcommunity.common.util;

import java.util.List;

public class MyPage<T> {

    private Long current;

    private Integer size;

    private Long total;

    private List<T> records;

    public MyPage(Long current, Integer size, Long total) {
        this.current = current < 1 ? 1 : current;
        this.size = size < 0 ? 0 : size;
        this.total = total;
    }

    public Long offset() {
        return (this.current - 1) * this.size;
    }

    public Long getCurrent() {
        return current;
    }

    public void setCurrent(Long current) {
        this.current = current;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }
}
