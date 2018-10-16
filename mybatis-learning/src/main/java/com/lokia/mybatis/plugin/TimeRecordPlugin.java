package com.lokia.mybatis.plugin;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.Properties;

/**
 * @author gushu
 * @data 2018/10/16
 */
@Intercepts({@Signature(method = "query",type = Executor.class,args={MappedStatement.class,Object.class, RowBounds.class, ResultHandler.class})})
public class TimeRecordPlugin implements Interceptor {
    public Object intercept(Invocation invocation) throws Throwable {
        Long start = System.currentTimeMillis();
        Object result = invocation.proceed();
        Long end = System.currentTimeMillis();
        System.out.println("time consumed:"+(end-start));
        return result;
    }

    public Object plugin(Object target) {
        if(target instanceof Executor){
            return Plugin.wrap(target,this);
        }
        return target;
    }

    public void setProperties(Properties properties) {

    }
}
