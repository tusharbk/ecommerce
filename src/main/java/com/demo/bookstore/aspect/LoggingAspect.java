package com.demo.bookstore.aspect;

import lombok.SneakyThrows;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class LoggingAspect {

    public static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("@annotation(com.demo.bookstore.aspect.Logger)")
    @SneakyThrows
    public Object logging(ProceedingJoinPoint pjp){
        log.info("Enter: {}.{}() ",pjp.getSignature().getDeclaringTypeName(),pjp.getSignature().getName());
        StopWatch stopWatch=new StopWatch();
        stopWatch.start();
        Object proceed=pjp.proceed();
        stopWatch.stop();
        log.info("Exit: {}.{}() with result = {} with execution time={} ms",pjp.getSignature().getDeclaringTypeName(),
                pjp.getSignature().getName(),proceed,stopWatch.getTotalTimeMillis());
        return proceed;
    }

    @AfterThrowing(pointcut = "@annotation(com.demo.bookstore.aspect.Logger)",throwing = "e")
    public void loggingAfterException(JoinPoint jp,Exception e){
        log.error("Exception in {}.{}() with cause = {}",jp.getSignature().getDeclaringTypeName(),
                jp.getSignature().getName(),e.toString());
    }
}
