package com.ductram.learningspring.benchmark;

import com.alibaba.fastjson2.JSON;
import com.ductram.learningspring.model.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)        // đo thời gian trung bình mỗi lần chạy
@OutputTimeUnit(TimeUnit.MICROSECONDS)  // đơn vị µs/op
@Warmup(iterations = 3)                 // 3 lần warm-up
@Measurement(iterations = 5)            // đo 5 lần
@Fork(2)                                // chạy 2 JVM độc lập
@State(Scope.Benchmark)
public class JsonParseBenchmark {

    private final Gson gson = new Gson();
    private final ObjectMapper jackson = new ObjectMapper();

    private final String json = """
                {
                    "name": "Alice",
                    "age": 25,
                    "email": "alice@example.com",
                    "active": true
                }
            """;

    @Benchmark
    public Person parseWithGson() {
        return gson.fromJson(json, Person.class);
    }

    @Benchmark
    public Person parseWithJackson() throws Exception {
        return jackson.readValue(json, Person.class);
    }

    @Benchmark
    public Person parseWithFastjson() {
        return JSON.parseObject(json, Person.class);
    }

    @Benchmark
    public String writeWithGson() {
        return gson.toJson(new Person("Bob", 30, "bob@test.com", false));
    }

    @Benchmark
    public String writeWithJackson() throws Exception {
        return jackson.writeValueAsString(new Person("Bob", 30, "bob@test.com", false));
    }

    @Benchmark
    public String writeWithFastjson() {
        return JSON.toJSONString(new Person("Bob", 30, "bob@test.com", false));
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(JsonParseBenchmark.class.getSimpleName())
                .forks(2)
                .build();

        new Runner(opt).run();
    }
}
