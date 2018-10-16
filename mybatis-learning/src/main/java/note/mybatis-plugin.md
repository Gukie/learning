## refer 
- http://www.mybatis.org/mybatis-3/configuration.html#plugins

### note
1. plugin的化，是采用代理的方式对目标对象进行代理
2. 自己写的Plugin，需要加上注解@Intercepts. 并且只能在几个类中加入plugin, 这个可以从Interceptor.pluginAll的调用方看出来
>- ParameterHandler
>- ResultSetHandler
>- Executor
>- StatementHandler

```java
public class InterceptorChain {
  public Object pluginAll(Object target) {
    for (Interceptor interceptor : interceptors) {
      target = interceptor.plugin(target);
    }
    return target;
  }
 }
 ```
 
 自己写的plugin
 ```java

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
        return Plugin.wrap(target,this);
    }

    public void setProperties(Properties properties) {

    }
}

```
```java
 public class Plugin implements InvocationHandler {
   public static Object wrap(Object target, Interceptor interceptor) {
     Map<Class<?>, Set<Method>> signatureMap = getSignatureMap(interceptor);
     Class<?> type = target.getClass();
     Class<?>[] interfaces = getAllInterfaces(type, signatureMap);
     if (interfaces.length > 0) {
       return Proxy.newProxyInstance(
           type.getClassLoader(),
           interfaces,
           new Plugin(target, interceptor, signatureMap));
     }
     return target;
   }
  }
```


