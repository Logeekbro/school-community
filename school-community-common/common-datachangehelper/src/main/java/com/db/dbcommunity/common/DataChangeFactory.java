package com.db.dbcommunity.common;

import com.sun.istack.internal.logging.Logger;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DataChangeFactory {

    private static final Logger logger = Logger.getLogger(DataChangeFactory.class);

    private static final Map<String, List<Runnable>> handlerMap = new HashMap<>();

    public static void handle(String type) {
        List<Runnable> tasks = handlerMap.get(type);
        if(tasks != null) {
            for(Runnable task : tasks) {
                task.run();
            }
        } else {
            logger.warning("not handler provided for this type: " + type);
        }
    }

    public static void addHandler(IDataChangeHandler handler) {
        for (String type : handler.handleType()) {
            List<Runnable> tasks = handlerMap.get(type);
            if(tasks == null) tasks = new LinkedList<>();
            tasks.add(handler.runnable());
            handlerMap.put(type, tasks);
        }

    }
}
