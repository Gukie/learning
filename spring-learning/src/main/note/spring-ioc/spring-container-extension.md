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
BeanFactoryPostProcessor 是在容器中的所有bean开始实例化之前， 它的一个方法是： postProcessBeanFactory
可以通过实现BeanFactoryPostProcessor，对bean的definition进行一些修改. 

如果是想对Bean的实例做一些修改，可以通过BeanPostProcessor进行