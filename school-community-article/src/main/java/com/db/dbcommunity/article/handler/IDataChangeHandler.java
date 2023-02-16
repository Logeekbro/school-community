package com.db.dbcommunity.article.handler;

import com.db.dbcommunity.article.enums.DataChangeType;

/**
 * 定义调用改变数据方法时的后续处理（注：由于借助了Spring来扫描实现类，所以实现类要使用@Component等注解声明为一个Bean）
 */
public interface IDataChangeHandler {

    /**
     * 处理的类型,支持多个类型
     */
    DataChangeType[] handleType();

    /**
     * 具体的处理代码
     */
    Runnable runnable();
}
