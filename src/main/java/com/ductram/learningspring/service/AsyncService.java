package com.ductram.learningspring.service;

import java.util.concurrent.Future;

public interface AsyncService {

    void asyncMethodWithVoidReturnType();

    Future<String> asyncMethodWithReturnType();
}
