package com.ductram.learningspring.aop;

import com.ductram.learningspring.aop.annotation.BankAccountLog;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExampleAspect {
    private String result;

    @Pointcut("@annotation(com.ductram.learningspring.aop.annotation.LogExecutionTime)")
    public void myLoggingMethods() {
    }

    @Pointcut("@annotation(com.ductram.learningspring.aop.annotation.BankAccountLog)")
    public void additionInfoLoggingMethods() {
    }

    @Before(value = "additionInfoLoggingMethods() && args(id)")
    public void beforeAdvice(String id) {
//        System.out.println(joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
    }

    @AfterReturning(pointcut = "additionInfoLoggingMethods()", returning = "result")
    public void afterReturningAdvice(JoinPoint joinPoint, Object result) {
        System.out.println(joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
    }

    @Around("myLoggingMethods()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {

        Object[] args = joinPoint.getArgs();
        Object target = joinPoint.getTarget();

        // Log method execution
        System.out.println("Executing method: " + joinPoint.getSignature().getName());

        // Find methods with @AdditionInfoLogging and invoke them
        for (var method : target.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(BankAccountLog.class)) {
                method.setAccessible(true); // Ensure access to private/protected methods
                Object result = method.invoke(target, args);
                System.out.println("Additional Info from @AdditionInfoLogging: " + result);
            }
        }

        long start = System.currentTimeMillis();

        Object proceed = joinPoint.proceed();

        long executionTime = System.currentTimeMillis() - start;

        System.out.println("@DUKE_LOG: " + joinPoint.getSignature() + " executed in " + executionTime + "ms");
        System.out.println("@DUKE_LOG: result: =>> " + result);
        return proceed;
    }

    // @Around("@annotation(com.ductram.learningspring.aop.annotation.BankAccountLog)")
    // public Object logTransactionLog(ProceedingJoinPoint joinPoint) throws
    // Throwable {
    // long start = System.currentTimeMillis();

    // Object proceed = joinPoint.proceed();

    // long executionTime = System.currentTimeMillis() - start;

    // System.out.println("@DUKE_LOG: " + joinPoint.getSignature() + " executed in "
    // + executionTime + "ms");
    // result = proceed.toString();
    // return proceed;
    // }
}
