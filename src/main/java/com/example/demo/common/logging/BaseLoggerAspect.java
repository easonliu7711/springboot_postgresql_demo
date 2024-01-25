package com.example.demo.common.logging;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
@EnableAspectJAutoProxy
public class BaseLoggerAspect {

    @Resource
    private HttpServletRequest request;

    // 切面為非Service(因為Service都會向外拋錯誤)之外的所有類別
    @Pointcut("execution(* com.example.demo..*.*(..)) && !execution(* com.example.demo.domain.service..*.*(..))")
    public void commonClassMethods() {
    }

    // 針對拋出的異常進行日誌紀錄
    @AfterThrowing(value = "commonClassMethods()", throwing = "t")
    public void commonLog(JoinPoint joinPoint, Throwable t) {
        LoggingHelper.toExceptionLogger(getClassName(joinPoint), getMethodName(joinPoint), joinPoint.getArgs(), t);
    }

    @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
    public void controllerClassMethods() {
        // do nothing...
    }

    @Around("controllerClassMethods()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        LoggingHelper.toControllerRequestLog(getRequestURI(), getInput(joinPoint));

        LocalDateTime start = LocalDateTime.now();
        Object result = getProcessedResult(joinPoint);
        LocalDateTime end = LocalDateTime.now();

        LoggingHelper.toControllerProcessedLog(getClassName(joinPoint), getMethodName(joinPoint), start, getInput(joinPoint), result, end);

        return result;
    }

    @Around("serviceClassMethods()")
    public Object doAroundWithService(ProceedingJoinPoint joinPoint) throws Throwable {
        LocalDateTime start = LocalDateTime.now();
        Object result = getProcessedResult(joinPoint);
        LocalDateTime end = LocalDateTime.now();

        LoggingHelper.toServiceLog(getClassName(joinPoint), getMethodName(joinPoint), start, getInput(joinPoint), result, end);

        return result;
    }

    private String getClassName(JoinPoint joinPoint) {
        return joinPoint.getSignature().getDeclaringTypeName();
    }

    private Object[] getInput(ProceedingJoinPoint joinPoint) {
        return joinPoint.getArgs();
    }

    private String getMethodName(JoinPoint joinPoint) {
        return joinPoint.getSignature().getName();
    }

    private Object getProcessedResult(ProceedingJoinPoint joinPoint) throws Throwable {
        return joinPoint.proceed();
    }

    private String getRequestURI() {
        return request.getRequestURI();
    }
    
    @Pointcut("@within(org.springframework.stereotype.Service)")
    public void serviceClassMethods() {
        // do nothing...
    }
}
