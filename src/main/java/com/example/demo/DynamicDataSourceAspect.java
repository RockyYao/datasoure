package com.example.demo;

import com.example.demo.enumDemo.DataSoureKey;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Order(-1)
@Component
public class DynamicDataSourceAspect {
    private static final Logger LOG = Logger.getLogger(DynamicDataSourceAspect.class);


    @Pointcut("execution(* com.example.demo.controller..*(..))")
    public void pointCut() {
    }


    @Before("@annotation(targetDataSource)")
    public void doBefore(JoinPoint joinPoint,TargetDataSource targetDataSource){
        DataSoureKey dataSourceKey = targetDataSource.dataSourceKey();
        if (dataSourceKey == DataSoureKey.datasource2) {
            LOG.info(String.format("设置数据源为  %s", DataSoureKey.datasource2));
            DynamicDataSourceContextHolder.set(DataSoureKey.datasource2);
        } else if (dataSourceKey == DataSoureKey.datasource){
            LOG.info(String.format("使用默认数据源  %s",DataSoureKey.datasource));
            DynamicDataSourceContextHolder.set(DataSoureKey.datasource);
        }else{
            LOG.info(String.format("设置数据源为  %s",DataSoureKey.datasource3));
            DynamicDataSourceContextHolder.set(DataSoureKey.datasource3);
        }

        }

    @After("@annotation(targetDataSource)")
    public void doAfter(JoinPoint joinPoint, TargetDataSource targetDataSource) {
        LOG.info(String.format("当前数据源  %s  执行清理方法", targetDataSource.dataSourceKey()));
        DynamicDataSourceContextHolder.clear();
    }




    @Before(value = "pointCut()")
    public void doBeforeWithSlave(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        //获取当前切点方法对象
        Method method = methodSignature.getMethod();
        if (method.getDeclaringClass().isInterface()) {//判断是否为借口方法
            try {
                //获取实际类型的方法对象
                method = joinPoint.getTarget().getClass()
                        .getDeclaredMethod(joinPoint.getSignature().getName(), method.getParameterTypes());
            } catch (NoSuchMethodException e) {
                LOG.error("方法不存在！", e);
            }
        }
        if (null == method.getAnnotation(TargetDataSource.class)) {
            DynamicDataSourceContextHolder.setSlave();
        }
    }
}



