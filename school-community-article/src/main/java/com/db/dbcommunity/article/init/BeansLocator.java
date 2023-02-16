package com.db.dbcommunity.article.init;

import com.db.dbcommunity.article.handler.IDataChangeHandler;
import com.db.dbcommunity.article.thread.MyThreadPoolExecutor;
import com.db.dbcommunity.article.util.DataChangeFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Component
public class BeansLocator implements ApplicationContextAware {


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        // 扫描所有IDataChangeHandler的实现类并添加到DataChangeFactory的map中
        Map<String, IDataChangeHandler> beans = applicationContext.getBeansOfType(IDataChangeHandler.class);
        beans.forEach((s, i) ->
                CompletableFuture.runAsync(() -> DataChangeFactory.addHandler(i), MyThreadPoolExecutor.getThreadPool()));
    }
}
