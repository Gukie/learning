## BeanPostProcessor
在bean的初始化前后，做一些用户自定义的工作，比较常用的是在初始化好之后将bean的实例应用到AOP代理中

它的两个方法是：
postProcessBeforeInitialization
postProcessAfterInitialization

需要注意的是
>- 如果一个Bean实现了 BeanPostProcessor，那么所有的Bean在初始化的前后都会 去调用 BeanPostProcessor-Bean的postProcessBeforeInitialization和postProcessAfterInitialization方法
>- 而 InitializingBean's afterPropertiesSet 或者说是 用户自定义的 init-method则是在 BeanPostProcessor-Bean的两个方法的中间


从BeanFactory的javaDoc中可以看到一个Bean的初始化过程的整体顺序：
Bean factory implementations should support the standard bean lifecycle interfaces as far as possible. The full set of initialization methods and their standard order is:
>- BeanNameAware's setBeanName
>- BeanClassLoaderAware's setBeanClassLoader
>- BeanFactoryAware's setBeanFactory
>- EnvironmentAware's setEnvironment
>- EmbeddedValueResolverAware's setEmbeddedValueResolver
>- ResourceLoaderAware's setResourceLoader (only applicable when running in an application context)
>- ApplicationEventPublisherAware's setApplicationEventPublisher (only applicable when running in an application context)
>- MessageSourceAware's setMessageSource (only applicable when running in an application context)
>- ApplicationContextAware's setApplicationContext (only applicable when running in an application context)
>- ServletContextAware's setServletContext (only applicable when running in a web application context)
>- postProcessBeforeInitialization methods of BeanPostProcessors
>- InitializingBean's afterPropertiesSet
>- a custom init-method definition
>- postProcessAfterInitialization methods of BeanPostProcessors


## BeanFactoryPostProcessor
BeanFactoryPostProcessor 是在容器中的所有bean开始实例化之前对metadata进行一些修改(所谓的metadata就是bean的定义，比如类的field是什么，field的值是多少)
可以通过实现BeanFactoryPostProcessor，对bean的definition进行一些修改. 
如果是想对Bean的实例做一些修改，可以通过BeanPostProcessor进行
>BeanFactoryPostProcessor operates on the bean configuration metadata; that is, the Spring IoC container allows a BeanFactoryPostProcessor to read the configuration metadata and potentially change it before the container instantiates any beans other than BeanFactoryPostProcessors


BeanPostProcessors 跟 BeanFactoryPostProcessor在容器启动的时候，就会被检查到并被实例化出来; 所以lazy initialization对它们是无效的
### BeanFactoryPostProcessor的两个实现, PropertyOverrideConfigurer vs PropertyPlaceholderConfigurer
PropertyOverrideConfigurer,PropertyPlaceholderConfigurer 它们都是在bean的实例化之前，对bean的metadata做一些修改; 不同的是：
- PropertyOverrideConfigurer，是push的方式，会从properties文件中将bean的metadata信息覆盖掉
- PropertyPlaceholderConfigurer，是pull的方式，会从从properties文件中获取值，然后替代掉 占位符中的值; 通过它可以设置一个默认值，语法如下: 
```
<property name="url" value="jdbc:${dbname:defaultdb}"/>
```

## FactoryBean
FactoryBean一般用户架构的基础层，对一些构造过程负载的bean可以考虑采用它; 比如 读取xml中的list，set，map，就会用到， 他们对应的FactoryBean是： 
- ListFactoryBean
- MapFactoryBean
- SetFactoryBean 

它的getObjectType()和 getObject()两个方法，会在启动的时候就被调用，一般会早于post-processor bean的创建
>FactoryBean is a programmatic contract. Implementations are not supposed to rely on annotation-driven injection or other reflective facilities. 
>getObjectType() getObject() invocations may arrive early in the bootstrap process, even ahead of any post-processor setup. If you need access other beans, implement BeanFactoryAware and obtain them programmatically.


```
        FactoryBean factoryBean = (FactoryBean) context.getBean("&customFactoryBean");
        Class<?> cls = factoryBean.getObjectType();
        System.out.println(cls.getName());

        FactoryBean1 factoryBean1 = (FactoryBean1) context.getBean("customFactoryBean");
        System.out.println(factoryBean1.getName());
```
```java
@Component
public class CustomFactoryBean implements FactoryBean<FactoryBean1> {
    @Nullable
    @Override
    public FactoryBean1 getObject() throws Exception {
        // 这里可以定义复杂的 bean的创建
        FactoryBean1 result = new FactoryBean1();
        result.setName("created from factoryBean");
        return result;
    }

    @Nullable
    @Override
    public Class<?> getObjectType() {
        return FactoryBean1.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

```