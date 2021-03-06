 IOC 容器的核心package:
	1. org.springframework.beans
	2. org.springframework.context 
	
	
	
BeanFactory OR ApplicationContext?
建议使用 ApplicationContext， 因为 ApplicationContext extends BeanFactory，并且 ApplicationContext 提供更丰富的功能

1. 在IOC container中， beans跟它们的dependency，是通过读取配置信息，然后通过反射生成的
> Beans, and the dependencies among them, are reflected in the configuration metadata used by a container

配置信息可以通过： xml， 注解和 program code的形式进行定义
> The configuration metadata is represented in XML, Java annotations, or Java code


2. ApplicationContext 代表着 IOC Container， 它负责通过读取配置信息，进行beans的实例化，组装等工作.
> The interface org.springframework.context.ApplicationContext represents the Spring IoC container and is responsible for instantiating, configuring, and assembling the aforementioned beans

3. ApplicationContext 基本都是 不在spring的框架内创建的，即需要显式声明进行创建
	3.1. 比较常见的 Standalone ApplicationContext instance ： ClassPathXmlApplicationContext 和 FileSystemXmlApplicationContext。
		完全通过code，不通过XML的化，可以使用 AnnotationConfigApplicationContext
    3.2. web项目的化，一般是通过 org.springframework.web.context.ContextLoaderListener 创建的
	3.3. refer:
		- https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#beans-java (通过完全注解的方式创建一个web工程)
	

	
	
4. Java-based configuration: Starting with Spring 3.0
	4.1. @Configuration一般用于类中，用于标示该类是用于定义Bean的 (注: @Configuration本质上是一个Bean)
		@Bean一般用于方法中，由于生成一个bean

		@Configuration
		public class AppConfig {

			@Bean
			public MyService myService() {
				return new MyServiceImpl();
			}
		}

		等价于：
		<beans>
			<bean id="myService" class="com.acme.services.MyServiceImpl"/>
		</beans>
		
	4.2. @Bean的方法中调用另一个@Bean方法，称为 'inter-bean-reference', 推荐使用 full-mode，以免引起一些诡异的bug
		4.2.1. @Bean 与 @Configuration一起使用时，称为'full-mode'
			这时候， @Bean方法调用另一个@Bean的方法时，会像查找其他Bean一样，通过容器的getBean方法去查找. 所以AOP在这里是适用的
			如果多次调用另一个@Bean方法，那么得到的结果是同一个，应为 另一个@Bean在容器中已存在(只有是另一个@Bean的Scope是singleton的时候才这样)
		4.2.2. @Bean 与 @Configuration 不一起使用时，比如是在@Component的class中，这时候称为'lite-mode'
			这时候，@Bean方法调用另一个@Bean的方法时, AOP将不会起作用
			如果多次调用另一个@Bean方法，那么得到的结果是多个不同的实例
		4.2.3. 原理： 
			1. 在Spring启动的时候，所有的@Configuration类，都会被CGLIB代理，然后生成相应的子类
			2. 子类在调用另一个@Bean方法的时候，会先去container中查找对应的Bean，找不到才会再调用父类的@Bean方法
			3. 而在lite-mode方式上，都是在当前类中直接调用@Bean方法，所以每次都是新生成的
		4.2.4. 注意
			1. @Bean的方法，可以是static; 如果是static，那么即使它是定义在 @Configuration，调用类内的其他方法，都是直接调用; 因为CGLIB不会代理static方法
			2. 如果跟@Configuration一起，那么@Bean的方法不能是 private或者是final的，因为它要被CGLIB override
			
		>The @Bean methods in a regular Spring component are processed differently than their counterparts inside a Spring @Configuration class. The difference is that @Component classes are not enhanced with CGLIB to intercept the invocation of methods and fields. CGLIB proxying is the means by which invoking methods or fields within @Bean methods in @Configuration classes creates bean metadata references to collaborating objects; such methods are not invoked with normal Java semantics but rather go through the container in order to provide the usual lifecycle management and proxying of Spring beans even when referring to other beans via programmatic calls to @Bean methods. In contrast, invoking a method or field in an @Bean method within a plain @Component class has standard Java semantics, with no special CGLIB processing or other constraints applying.
			
	4.3. 通过 @Bean 生成的Bean，其名字默认是对应的方法名字，比如以下Bean的名字就是：'transferService'
		@Configuration
		public class AppConfig {

			@Bean
			public TransferServiceImpl transferService() {
				return new TransferServiceImpl();
			}
		}
	4.4. 通过@Bean定义，生成出来的Bean，其类型是方法的返回值. 
		如果返回类型是一个接口，而该接口又有多个实现时，在使用的时候需要指定名字(Spring会默认去查找 field的name对应的bean，如果找不到则会报错)
		如果在定义Bean的地方，既有返回类型是接口的，又有返回类型是实现类的，那么实现类将会认为"既是接口，又是实现类"
		    
		
		定义；
			@Bean
			public TransferService transferService(){
				return new TransferServiceImpl2();
			}

			@Bean
			public TransferService transferService2(){
				TransferServiceImpl result = new TransferServiceImpl();
				result.setName("from interface ");
				return result;
			}

			// generate bean by impl
			@Bean
			public TransferServiceImpl transferService3(){
				TransferServiceImpl result = new TransferServiceImpl();
				result.setName("from impl ");
				return result;
			}
		
		使用：
		
			@Autowired
			// private TransferService transferService; // 这个会找 name为'transferService'的bean
			private TransferService transferService2; // 这个会找 name为'transferService2'的bean
			private TransferService transferService3; // 这个会找 name为'transferService3'的bean
			private TransferServiceImpl transferService4; // 这个会找 类型为'TransferServiceImpl'的bean
			
	4.5. 当一个Bean需要dependency的时候，可以通过@Bean的method的参数实现
		```
		@Bean
		public TransferService transferService(AccountRepository accountRepository) {
			return new TransferServiceImpl(accountRepository);
		}
		```
		这样就认为 创建 TransferService需要 依赖AccountRepository; 也可以使用另一种方式：
		```
		@Autowired 
		private AccountRepository accountRepository;
		@Bean
		public TransferService transferService() {
			return new TransferServiceImpl(accountRepository);
		}
		```
		
		
5. 当一个定义了 scope的bean被当做另一个bean的dependency时，可能会出现 scope-bean-dependency 的scope小于 bean的scope
	这时候，就需要将 dependency那个，代理一下，否则它会在它的作用范围外使用不到
	比如，一个 bean 是HTTP-session的，那么HTTP-session完了之后，它就会消失; 那么它就不能用做 scope为singleton的Bean的dependecny;
	如果这时候需要使用，可以使用代理
```
	<!-- an HTTP Session-scoped bean exposed as a proxy -->
    <bean id="userPreferences" class="com.foo.UserPreferences" scope="session">
        <!-- instructs the container to proxy the surrounding bean -->
        <aop:scoped-proxy/>
    </bean>

    <!-- a singleton-scoped bean injected with a proxy to the above bean -->
    <bean id="userService" class="com.foo.SimpleUserService">
        <!-- a reference to the proxied userPreferences bean -->
        <property name="userPreferences" ref="userPreferences"/>
    </bean>
```
	注意，这里的代理，默认是CGLIB的，如果需要设置为JDK的动态代理，则将proxy-target-class="false"
	
	
6. 当一个Bean的scope被定义为 prototype时
	6.1. 如果使用的时候，是通过 @Autowire注入进来的，那么后面每一次使用它，都是刚开始注入进去的那个bean
	6.2. 如果使用的时候，都重新获取一次，那么每一次它都是一个新的对象
	
7. 使用一个 scope为 prototype的bean， 可以使用 method injection，有两种方式
	7.3. 不要通过 @Autowire的方式查找，原因在上面已经说明了
	7.1. 通过 ApplicationContext去获取 (不建议，因为会将Spring耦合进来)
	7.2. 通过lookup的方式去获取(建议)
		这种方式的原理是 使用 CGLIB的字节码生成功能，直接生成一个子类，复写父类生成bean的方法
		> The Spring Framework implements this method injection by using bytecode generation from the CGLIB library to generate dynamically a subclass that overrides the method
		
	
	
	
8. namespace
	c-namespace: construction, 构造函数的参数
	p-namespace: property， 设置类中的property
	
9. Bar类中有一个fred字段，而fred又有一个bob字段，并且bob字段又有一个sammy字段. 以下配置可以实现将fred的bob字段的Sammy字段设置为 123， 前提是： fred,bob,sammy都不为NULL
	<bean id="foo" class="foo.Bar">
		<property name="fred.bob.sammy" value="123" />
	</bean>
	
10. 依赖注入
	10.1. 通过 构造函数的字段进行注入 (Bean必须的property可以通过该方式注入)
	10.2. 通过 setter方法注入 (Bean 被必须的property可以通过该方式注入)
	10.3. 如果依赖出现 一个环，比如：
		A依赖B， B又依赖A
		1. 如果是通过构造函数的方式注入，则不会成功，在初始化ApplicationContext的时候，会报：BeanCurrentlyInCreationException
		2. 如果是通过Setter的方式注入，则会成功
		3. 但还是尽量不要出现 dependency环
		
11. bean的创建，默认是在 Spring IOC 容器启动的时候，即ApplicationContext创建的时候
	它的好处是： 
		1. 将bean都加载进来了
		2. 由于需要加载bean，所以会校验bean的配置是否正确，bean与bean之间的引用是否正确. 如果错误会抛错
	坏处：
		1. 启动的时候会耗费更多的时间与内存(因为要加载好多Bean)
		
12.
要想使project中的 注解生效，使用以下两种的一种：
	1. <!-- enable processing of annotations such as @Autowired and @Configuration -->
		<context:annotation-config/>
	2.<!-- picks up and registers AppConfig as a bean definition -->
		<context:component-scan base-package="com.acme"/>
	
	3. 当有了 component-scan时，annotation-config就可以不用声明了，因为 前者包含了后者的功能	
	> we don’t need to explicitly declare <context:annotation-config/>, because <context:component-scan/> enables the same functionality.
    
13. 在 BeanPostProcessor 跟 BeanFactoryPostProcessor 中使用 lazy加载，是会忽略的; 即lazy加载对他们无效
- BeanFactoryPostProcessor 可以修改 bean的configuration meta data，所以 PropertyPlaceholderConfigurer就可以修改配置信息中的占位符的值
> apply changes to the configuration metadata that define the container.

14. 如果一个Bean的创建，很复杂，需要用到很多其他的资源，可以考虑 创建一个自己的 FactoryBean，然后在里面进行处理. Spring中有很多 FactoryBean的实现者，比如 AOP的ProxyFactoryBean
```
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
}

```
使用的时候：
- 获取factory创建的bean,直接用 FactoryBean的名字，如果要获取 FactoryBean本省，则可以在 FactoryBean的名字前面加上'&'
```
		// 获取factory创建的beanBean
		FactoryBean1 factoryBean1 = (FactoryBean1) context.getBean("customFactoryBean");
        System.out.println(factoryBean1.getName());
		
		// 获取FactoryBean自身
		FactoryBean factoryBean = (FactoryBean) context.getBean("&customFactoryBean");
		Class<?> cls = factoryBean.getObjectType();
		System.out.println(cls.getName());
```


15. 如果 注解跟xml配置，同时存在的话， xml配置会将注解覆盖掉
> Annotation injection is performed before XML injection, thus the latter configuration will override the former for properties wired through both approaches.

16. @Autowired, @Inject, @Resource, and @Value 这些是通过BeanPostProcessor处理的，所以在 BeanPostProcessor或者 BeanFactoryBeanProcessor 中不应该使用它们
17. Bean的name就是 xml中的id, 比如以下bean，其name为'subBean'
```
	<bean id="subBean" parent="parentBean" class="com.SubBean">
        <property name="desc" value="sub bean desc"></property>
    </bean>
```

18. @Autowired 跟 @Resource的区别
- @Autowire
按照 type去查找bean，如果要按照名字去查找，那么可以结合 @Qualifier： 
1. 按照type查找bean
2. 从上一步获取到bean中，按照名字取获取
可出现在 field，setter(参数个数没限制)，construction(如果有多个construction的化，只能有一个construction可以被@Autowire标记)
- @Resource
按照名字取查找的, 只能出现在 field或者 只有一个参数的setter方法中
>  @Resource is supported only for fields and bean property setter methods with a single argument
如果@Resource没有设置名字，那么他会将 它作用的对象的名字作为其name去查找
>If no name is specified explicitly, the default name is derived from the field name or setter method
```
@Resource
    public void setMovieFinder(MovieFinder movieFinder) {
        this.movieFinder = movieFinder;
    }
```

19. profile 的激活
	- 可以通过 设置 spring.profiles.active 属性
		-- 可以通过以下方式：
			1. system environment variables
			2. JVM system properties
			3. servlet context parameters in web.xml
			4. or even as an entry in JNDI
	- 通过程序的方式
		ApplicationContext.getEnvironment().setActiveProfiles("profile1","profile2");
		
	- 默认 profile的配置项： spring.profiles.default
	
20. Spring的 标准的Environment(StandardEnvironment), 会读取两部分property信息： 
	- JVM system property (可通过System.getProperties() 获取到)
	- System environment variables (可通过 System.getenv() 获取到)
	- 如果一个 property在 两个地方都有，那么 前者会覆盖后者
> Spring’s StandardEnvironment is configured with two PropertySource objects — one representing the set of JVM system properties (a la System.getProperties()) and one representing the set of system environment variables (a la System.getenv())


