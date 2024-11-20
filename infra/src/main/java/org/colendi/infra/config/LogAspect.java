package org.colendi.infra.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LogAspect {


    @Around("execution(* org.colendi.domain..*(..))")
    public Object logTellerMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        return log(joinPoint);

    }



    private Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();


        log.info("Method: {}.{} called with arguments: {}", className, methodName, Arrays.toString(args));
        return joinPoint.proceed();
    }
}
