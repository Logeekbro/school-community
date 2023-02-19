package com.db.dbcommunity.common;

import com.sun.istack.internal.logging.Logger;

import java.util.HashMap;
import java.util.Map;

public class DataChangeFactory {

    private static final Logger logger = Logger.getLogger(DataChangeFactory.class);

    private static final Map<String, Runnable> handlerMap = new HashMap<>();

    public static void handle(String type) {
        Runnable runnable = handlerMap.get(type);
        if(runnable != null) {
            runnable.run();
        } else {
            logger.warning("not handler provided for this type: " + type);
        }
    }

    public static void addHandler(IDataChangeHandler handler) {
        for (String type : handler.handleType()) {
            handlerMap.put(type, handler.runnable());
        }

    }
}
