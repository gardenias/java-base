package com.yimin.jms.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by WuYimin on 2015/3/30.
 */
public class Consumer {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(MyConnectionFactoryConfig.class);
//        JmsTemplate jmsTemplate = context.getBean(JmsTemplate.class);
//        System.out.println("Received: " + jmsTemplate.receiveAndConvert());
    }

}
