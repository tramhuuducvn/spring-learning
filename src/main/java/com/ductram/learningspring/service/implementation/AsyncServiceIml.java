package com.ductram.learningspring.service.implementation;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.ductram.learningspring.service.AsyncService;

@Component
public class AsyncServiceIml implements AsyncService {

    @Async("threadPoolTaskExecutor")
    @Override
    public void asyncMethodWithVoidReturnType() {
        System.out.println("Execute method asynchronously. "
                + Thread.currentThread().getName());
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
