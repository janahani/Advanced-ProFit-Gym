package com.profitgym.profitgym.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class UserControllerAspect {
    
    @Before("within(com.profitgym.profitgym.controllers.UserController)")
    public void beforeUserControllerMethods(JoinPoint joinPoint) {
        System.out.println("Before user controller method:" + joinPoint.getSignature());
    }

    @After("within(com.profitgym.profitgym.controllers.UserController)")
    public void afterUserControllerMethods(JoinPoint joinPoint) {
        System.out.println("After user controller method: " + joinPoint.getSignature());
    }

    @AfterReturning(pointcut = "execution(* com.profitgym.profitgym.controllers.UserController.*(..))", returning = "result")
    public void afterReturningUserControllerMethods(JoinPoint joinPoint, Object result) {
        System.out.println("After returning user controller method:" + joinPoint.getSignature() + ", result =" + result);
    }

    @AfterThrowing(pointcut = "execution(* com.profitgym.profitgym.controllers.UserController.*(..))", throwing = "ex")
    public void afterThrowingUserControllerMethods(JoinPoint joinPoint, Exception ex) {
        System.out.println("Exception thrown:" + joinPoint.getSignature() + ", ex =" + ex.getMessage());
    }

    @Around("within(com.profitgym.profitgym.controllers.UserController)")
    public Object timeTracker(ProceedingJoinPoint joinPoint) throws Throwable
    {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        long timeTakeninMS = endTime - startTime;
        System.out.println("Time taken by " + joinPoint.getSignature()+" is " + timeTakeninMS + "ms");
        System.out.println("***********************************************************************************************************************");
        return result;
    }
}
