package com.db.dbcommunity.article.util;

import com.db.dbcommunity.article.enums.DataChangeType;
import com.db.dbcommunity.article.handler.IDataChangeHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class DataChangeFactory {

    private static final Map<DataChangeType, Runnable> handlerMap = new HashMap<>();

    public static void handle(DataChangeType type) {
        Runnable runnable = handlerMap.get(type);
        if(runnable != null) {
            runnable.run();
        } else {
            log.warn("not handler provided for this type: {}", type);
        }
    }

    public static void addHandler(IDataChangeHandler handler) {
        for (DataChangeType type : handler.handleType()) {
            handlerMap.put(type, handler.runnable());
        }

    }
}
