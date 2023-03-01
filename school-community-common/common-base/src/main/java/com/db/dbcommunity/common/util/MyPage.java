package com.db.dbcommunity.common.util;

import java.util.Collection;
import java.util.List;

public class MyPage<T> {

    private Long current;

    private Short size;

    private Long total;

    private Collection<T> records;

    public MyPage(Long current, Short size, Long total) {
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

    public Short getSize() {
        return size;
    }

    public void setSize(Short size) {
        this.size = size;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Collection<T> getRecords() {
        return records;
    }

    public void setRecords(Collection<T> records) {
        this.records = records;
    }
}
