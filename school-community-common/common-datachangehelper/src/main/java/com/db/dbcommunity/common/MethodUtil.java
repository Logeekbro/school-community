package com.db.dbcommunity.common;


import java.util.function.Supplier;

public class MethodUtil {

    /**
     * 调用会改变数据的方法时的统一包装方法，以便于添加 数据改变时 的统一操作，如：更新缓存、发送消息等
     * @param type 调用的方法标识
     * @param supplier 调用的方法
     * @return 是否执行成功
     */
    public static boolean dataChangeCall(String type, Supplier<Boolean> supplier) {
        // 前置操作...

        // 真正的方法执行
        Boolean result = supplier.get();

        // 后置操作...
        DataChangeFactory.handle(type);

        return result;
    }
}
