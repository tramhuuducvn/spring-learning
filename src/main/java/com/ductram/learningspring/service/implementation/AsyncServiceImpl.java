package com.ductram.learningspring.service.implementation;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.ductram.learningspring.properties.ApplicationProperties;
import com.ductram.learningspring.service.AsyncService;

@Service("MyAsyncServiceImpl")
// @EnableConfigurationProperties(ApplicationProperties.class)
public class AsyncServiceImpl implements AsyncService {

    private final ApplicationProperties properties;

    public AsyncServiceImpl(ApplicationProperties properties) {
        this.properties = properties;
    }

    @Async("threadPoolTaskExecutor")
    @Override
    public void asyncMethodWithVoidReturnType() {
        System.out.println("Execute method asynchronously. "
                + Thread.currentThread().getName());

        System.out.println(this.properties);
    }

    @Async("threadPoolTaskExecutor")
    @Override
    public Future<String> asyncMethodWithReturnType() {
        System.out.println("Execute method asynchronously - "
                + Thread.currentThread().getName());
        try {
            Thread.sleep(5000);
            return CompletableFuture.completedFuture("Hello world !!!!");
        } catch (InterruptedException e) {
            //
        }

        return null;
    }

}
