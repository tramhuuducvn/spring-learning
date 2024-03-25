package com.ductram.learningspring;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ductram.learningspring.service.AsyncService;

@SpringBootApplication
public class LearningSpringApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(LearningSpringApplication.class, args);
	}

	@Autowired
	@Qualifier("MyAsyncServiceImpl")
	private AsyncService asyncService;

	public void testAsyncAnnotationForMethodsWithReturnType()
			throws InterruptedException, ExecutionException {

		System.out.println("Invoking an asynchronous method. "
				+ Thread.currentThread().getName());

		Future<String> future = asyncService.asyncMethodWithReturnType();

		while (true) {
			if (future.isDone()) {
				System.out.println("Result from asynchronous process - " + future.get());
				break;
			} else {
				asyncService.asyncMethodWithVoidReturnType();
			}
			System.out.println("Continue doing something else. ");
			Thread.sleep(1000);
		}
	}

	@Override
	public void run(String... args) throws Exception {
		testAsyncAnnotationForMethodsWithReturnType();
	}

}
