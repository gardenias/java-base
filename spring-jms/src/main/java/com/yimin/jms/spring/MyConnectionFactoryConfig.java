package com.yimin.jms.spring;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * Created by WuYimin on 2015/3/30.
 */
@EnableJms
@Configuration
public class MyConnectionFactoryConfig {
    private static final String BROKER_URL = "tcp://123.56.95.92:61616";
    private static final long RECEIVET_IMEOUT = 10000;

    /*==================================================================*/
    /*=================== ConnectionFactory Start ======================*/
    /*==================================================================*/
    @Bean
    public CachingConnectionFactory cacheConnectionFactory() {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
        cachingConnectionFactory.setSessionCacheSize(20);
        cachingConnectionFactory.setTargetConnectionFactory(targetConnectionFactory());
        return cachingConnectionFactory;
    }

    @Bean
    public ConnectionFactory targetConnectionFactory() {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory("admin", "password", BROKER_URL);
        activeMQConnectionFactory.setRedeliveryPolicy(redeliveryPolicy());
        activeMQConnectionFactory.setMaxThreadPoolSize(100);
        activeMQConnectionFactory.setExceptionListener(exception -> {
            System.out.println(exception.getLocalizedMessage());
        });
        activeMQConnectionFactory.setStatsEnabled(true);
        activeMQConnectionFactory.setExclusiveConsumer(false);
        return activeMQConnectionFactory;
    }

    @Bean
    public RedeliveryPolicy redeliveryPolicy() {
        RedeliveryPolicy redeliveryPolicy = new RedeliveryPolicy();
        redeliveryPolicy.setMaximumRedeliveries(20);//default 6
        return redeliveryPolicy;
    }

    /*==================================================================*/
    /*===================== ConnectionFactory End ======================*/
    /*==================================================================*/

   /* @Bean
    public JmsTemplate jmsTemplate() {
        JmsTemplate template = new JmsTemplate(cacheConnectionFactory());
        template.setReceiveTimeout(RECEIVET_IMEOUT);
        template.setDefaultDestination(testQueue());
        template.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);
        return template;
    }

    @Bean(name = "destination")
    public Queue testQueue() {
        return new ActiveMQQueue("test-queue");
    }*/

    @Bean(name = "defaultJmsListenerContainerFactory")
    public DefaultJmsListenerContainerFactory defaultJmsListenerContainerFactory() {
        DefaultJmsListenerContainerFactory defaultJmsListenerContainerFactory = new DefaultJmsListenerContainerFactory();
        defaultJmsListenerContainerFactory.setConnectionFactory(cacheConnectionFactory());

        defaultJmsListenerContainerFactory.setConcurrency("50-200");
        defaultJmsListenerContainerFactory.setTaskExecutor(executor());

        defaultJmsListenerContainerFactory.setReceiveTimeout(RECEIVET_IMEOUT);

        defaultJmsListenerContainerFactory.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);

        return defaultJmsListenerContainerFactory;
    }

    @JmsListener(destination = "test-queue", containerFactory = "defaultJmsListenerContainerFactory")
    public void messageReceiver(TextMessage textMessage) throws JMSException {
        doSomethingWithMessageContent(textMessage.getText());
    }

    static List<String> all = new LinkedList<>();
    static List<String> containerSuccess = new ArrayList<>(100);
    static List<String> containerFailure = new ArrayList<>(100);
    int allCount, successCount, failureCount;

    private void doSomethingWithMessageContent(String text) {
//        all.add(text);
        allCount++;
        long mills = System.currentTimeMillis();
        if ((mills % 5) > 3) {
//            System.out.println("FAILURE process message: " + text);
//            doStat(containerFailure, text);
            failureCount++;
            throw new RuntimeException(text + ". FAILURE");
        }
//        System.out.println("SUCCESS process message: " + text);
//        doStat(containerSuccess, text);
        successCount++;

    }

    @Bean
    Executor executor() {
        ThreadPoolTaskExecutor threadPoolExecutor = new ThreadPoolTaskExecutor();
        threadPoolExecutor.setMaxPoolSize(300);
        threadPoolExecutor.setQueueCapacity(1000);
        threadPoolExecutor.setCorePoolSize(100);
        threadPoolExecutor.setKeepAliveSeconds(3600);// 1 hours
        return threadPoolExecutor;
    }

    private void doStat(List<String> container, String text) {
        container.add(text);
        printStat();
    }

    public void printStat() {
        System.out.println("");
        System.out.println("=====================================");
        System.out.println("========= All Container ===" + allCount + "==========");
//        System.out.println(Joiner.on(",\n").join(containerSuccess));
        System.out.println("======= Success Container =" + successCount + "==========");
//        System.out.println(Joiner.on(",\n").join(containerSuccess));
        System.out.println("======= Failure Container =" + failureCount + "==========");
//        System.out.println(Joiner.on(",\n").join(containerFailure));
        System.out.println("=====================================");
    }
}

