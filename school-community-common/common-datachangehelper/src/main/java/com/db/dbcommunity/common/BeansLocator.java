package com.db.dbcommunity.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class BeansLocator implements ApplicationContextAware {


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        // 扫描所有IDataChangeHandler的实现类并添加到DataChangeFactory的map中
        Map<String, IDataChangeHandler> beans = applicationContext.getBeansOfType(IDataChangeHandler.class);
        beans.forEach((s, i) -> {
            System.out.println(s);
            DataChangeFactory.addHandler(i);
        });
    }
}
