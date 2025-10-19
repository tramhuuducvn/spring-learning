package com.ductram.learningspring.benchmark;

import com.ductram.learningspring.LearningSpringApplication;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@Warmup(iterations = 3)
@Measurement(iterations = 5)
@Fork(value = 2)
@State(Scope.Benchmark)
public class GreetingBenchmark {
    private static ConfigurableApplicationContext context;
    private WebClient client;

    @Setup(Level.Trial)
    public void setup() {
        // 🔹 Khởi chạy Spring Boot app trong cùng JVM
        if (context == null) {
            context = SpringApplication.run(LearningSpringApplication.class);
        }

        client = WebClient.builder()
                .baseUrl("http://localhost:8081")
                .build();
    }

    @TearDown(Level.Trial)
    public void tearDown() {
        // Nếu muốn tắt app sau khi benchmark
         context.close();
    }

    @Benchmark
    public String callHelloEndpoint() {
        // 🔹 Gọi API thật
        return client.get()
                .uri(uriBuilder -> uriBuilder.path("/hello")
                        .queryParam("name", "Alice")
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(GreetingBenchmark.class.getSimpleName())
                .forks(1)
                .build();
        new Runner(opt).run();
    }
}
