package com.jjtest.user.aop.transactional;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;

import java.lang.reflect.Method;

/**
 * 事务aop
 */
@Component
@Aspect
public class AopTransaction {
    @Autowired
    private TransactionUtil transactionUtil;

    private TransactionStatus transactionStatus;

    /**
     * 环绕通知，在方法 前---后 处理事情
     *
     * @param pjp 切入点
     */
    @Around("execution(* com.jjtest.user.service.*.*(..))")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        // 获取方法的注解
        MyAnnotation annotation = this.getMethodMyAnnotation(pjp);
        // 判断是否需要开启事务
        transactionStatus = begin(annotation);
        // 调用目标代理对象方法
        Object proceed = pjp.proceed();
        // 判断关闭事务
        commit(transactionStatus);
        return proceed;
    }

    /**
     * 获取代理方法上的事务注解
     *
     * @param pjp
     * @return
     * @throws Exception
     */
    private MyAnnotation getMethodMyAnnotation(ProceedingJoinPoint pjp) throws Exception {
        // 获取代理对象的方法
        String methodName = pjp.getSignature().getName();
        // 获取目标对象
        Class<?> classTarget = pjp.getTarget().getClass();
        // 获取目标对象类型
        Class<?>[] par = ((MethodSignature) pjp.getSignature()).getParameterTypes();
        // 获取目标对象方法
        Method objMethod = classTarget.getMethod(methodName, par);
        // 获取该方法上的事务注解
        MyAnnotation annotation = objMethod.getDeclaredAnnotation(MyAnnotation.class);
        return annotation;
    }

    /**
     * 开启事务
     *
     * @param annotation
     * @return
     */
    private TransactionStatus begin(MyAnnotation annotation) {
        if (annotation == null) {
            return null;
        }
        return transactionUtil.begin();
    }

    /**
     * 提交事务
     *
     * @param transactionStatus
     */
    private void commit(TransactionStatus transactionStatus) {
        if (transactionStatus != null) {
            transactionUtil.commit(transactionStatus);
        }
    }

    /**
     * 异常通知进行 回滚事务
     */
    @AfterThrowing("execution(* com.jjtest.user.service.*.*(..))")
    public void afterThrowing() {
        // 获取当前事务 直接回滚
        if (transactionStatus != null) {
            transactionUtil.rollback(transactionStatus);
        }
    }
}
