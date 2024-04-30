package uz.genesis.aiquest.webserver.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class BeanUtils {

    private static ApplicationContext applicationContext;

    @Autowired
    public BeanUtils(ApplicationContext applicationContext) {
        BeanUtils.applicationContext = applicationContext;
    }

    public static  <T> T getBean(Class<T> classType) {
        return applicationContext.getBean(classType);
    }

}