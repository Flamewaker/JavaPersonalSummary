# Spring AOP原理分析

[博客链接](https://blog.csdn.net/weixin_40160543/article/details/92010760)

**面向切面编程：Aspect Oriented Programming(AOP)**，利用AOP可以对业务逻辑的各个部分进行隔离，从而使得业务逻辑各部分之间的耦合度降低，提高程序的可重用性，同时提高了开发的效率。

主要的功能是：**日志记录，性能统计，安全控制，事务处理，异常处理**等等。

主要的意图是：将日志记录，性能统计，安全控制，事务处理，异常处理等代码从业务逻辑代码中划分出来，通过对这些行为的分离，我们希望可以将它们独立到非指导业务逻辑的方法中，进而改 变这些行为的时候不影响业务逻辑的代码。**可以通过预编译方式和运行期动态代理实现在不修改源代码的情况下给程序动态统一添加功能的一种技术。**

**总结：将那些与业务无关的，却可以被所有业务所调用的功能封装起来，从而使得业务逻辑各部分之间的耦合度降低，提高程序的可重用性，提高开发的效率。**



## 1. AOP底层实现原理

**代理模式**

通过代理控制对象的访问，可以详细访问某个对象的方法。可以在这个方法调用处理或调用后处理（代理模式可以对调用方法进行增强）。

**静态代理**

由程序员创建或工具生成代理类的源码，再编译代理类。所谓静态也就是在程序运行前就已经存在代理类的字节码文件，代理类和委托类的关系在运行前就确定了。

```java
public interface IUserDao {
    void save();
}
public class UserDao implements IUserDao {
    public void save() {
        System.out.println("已经保存数据...");
    }
}
public class UserDaoProxy implements IUserDao {
    private IUserDao target;

    public UserDaoProxy(IUserDao iuserDao) {
        this.target = iuserDao;
    }

    public void save() {
        System.out.println("开启事物...");
        target.save();
        System.out.println("关闭事物...");
    }

}

```

**动态代理**

1.代理对象,不需要实现接口

2.代理对象的生成，是利用JDK的API，动态的在内存中构建代理对象(需要我们指定创建代理对象/目标对象实现的接口的类型)

**JDK动态代理**

1)原理：是根据类加载器和接口创建代理类（此代理类是接口的实现类，所以必须使用接口 面向接口生成代理，位于java.lang.reflect包下）

2)实现方式：

1. 通过实现InvocationHandler接口创建自己的调用处理器 

   IvocationHandler handler = new InvocationHandlerImpl(…);

2. 通过为Proxy类指定ClassLoader对象和一组interface创建动态代理类

   Class clazz = Proxy.getProxyClass(classLoader,new Class[]{…});

3. 通过反射机制获取动态代理类的构造函数，其参数类型是调用处理器接口类型

   Constructor constructor = clazz.getConstructor(new Class[]{InvocationHandler.class});

4. 通过构造函数创建代理类实例，此时需将调用处理器对象作为参数被传入

   Interface Proxy = (Interface)constructor.newInstance(new Object[] (handler));

缺点：jdk动态代理，必须是面向接口，目标业务类必须实现接口

```java
// 每次生成动态代理类对象时,实现了InvocationHandler接口的调用处理器对象 
public class InvocationHandlerImpl implements InvocationHandler {
    private Object target;// 这其实业务实现类对象，用来调用具体的业务方法
    // 通过构造函数传入目标对象
    public InvocationHandlerImpl(Object target) {
        this.target = target;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;
        System.out.println("调用开始处理");
        result = method.invoke(target, args);
        System.out.println("调用结束处理");
        return result;
    }

    public static void main(String[] args) throws NoSuchMethodException, SecurityException, InstantiationException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        // 被代理对象
        IUserDao userDao = new UserDao();
        InvocationHandlerImpl invocationHandlerImpl = new InvocationHandlerImpl(userDao);
        ClassLoader loader = userDao.getClass().getClassLoader();
        Class<?>[] interfaces = userDao.getClass().getInterfaces();
        // 主要装载器、一组接口及调用处理动态代理实例
        IUserDao newProxyInstance = (IUserDao) Proxy.newProxyInstance(loader, interfaces, invocationHandlerImpl);
        newProxyInstance.save();
    }

}
```

**CGLIB动态代理**

原理：利用asm开源包，对代理对象类的class文件加载进来，通过修改其字节码生成子类来处理。

什么是CGLIB动态代理

使用cglib[Code Generation Library]实现动态代理，并不要求委托类必须实现接口，底层采用asm字节码生成框架生成代理类的字节码。

CGLIB动态代理相关代码

```java
public class CglibProxy implements MethodInterceptor {
    private Object targetObject;
    // 这里的目标类型为Object，则可以接受任意一种参数作为被代理类，实现了动态代理
    public Object getInstance(Object target) {
        // 设置需要创建子类的类
        this.targetObject = target;
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback(this);
        return enhancer.create();
    }

    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        System.out.println("开启事物");
        Object result = proxy.invoke(targetObject, args);
        System.out.println("关闭事物");
        // 返回代理对象
        return result;
    }
    public static void main(String[] args) {
        CglibProxy cglibProxy = new CglibProxy();
        UserDao userDao = (UserDao) cglibProxy.getInstance(new UserDao());
        userDao.save();
    }
}
```





CGLIB动态代理与JDK动态区别

java动态代理是利用反射机制生成一个实现代理接口的匿名类，在调用具体方法前调用InvokeHandler来处理。

而cglib动态代理是利用asm开源包，对代理对象类的class文件加载进来，通过修改其字节码生成子类来处理。

Spring中。

1、如果目标对象实现了接口，默认情况下会采用JDK的动态代理实现AOP

2、如果目标对象实现了接口，可以强制使用CGLIB实现AOP

3、如果目标对象没有实现了接口，必须采用CGLIB库，spring会自动在JDK动态代理和CGLIB之间转换

JDK动态代理只能对实现了接口的类生成代理，而不能针对类 。 CGLIB是针对类实现代理，主要是对指定的类生成一个子类，覆盖其中的方法 。 因为是继承，所以该类或方法最好不要声明成final ，final可以阻止继承和多态。


## 2. AOP编程使用

注解版本实现AOP

```
<aop:aspectj-autoproxy></aop:aspectj-autoproxy>  开启事物注解权限
@Aspect                           //指定一个类为切面类       
@Pointcut("execution(* com.service.UserService.add(..))")  //指定切入点表达式
@Before("pointCut_()")            //前置通知: 目标方法之前执行
@After("pointCut_()")             //后置通知：目标方法之后执行（始终执行）
@AfterReturning("pointCut_()")    //返回后通知： 执行方法结束前执行(异常不执行)
@AfterThrowing("pointCut_()")     //异常通知:  出现异常时候执行
@Around("pointCut_()")            //环绕通知： 环绕目标方法执行
```



```java
@Component
@Aspect
public class AopLog {

    // 前置通知
    @Before("execution(* com.service.UserService.add(..))")
    public void begin() {
        System.out.println("前置通知");
    }

    // 后置通知
    @After("execution(* com.service.UserService.add(..))")
    public void commit() {
        System.out.println("后置通知");
    }

    // 运行通知
    @AfterReturning("execution(* com.service.UserService.add(..))")
    public void returning() {
        System.out.println("运行通知");
    }

    // 异常通知
    @AfterThrowing("execution(* com.service.UserService.add(..))")
    public void afterThrowing() {
        System.out.println("异常通知");
    }

    // 环绕通知
    @Around("execution(* com.service.UserService.add(..))")
    public void around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("环绕通知开始");
        proceedingJoinPoint.proceed();
        System.out.println("环绕通知结束");
    }
}
```

XML方式实现AOP

```
Xml实现aop编程：
    1） 引入jar文件  【aop 相关jar， 4个】
    2） 引入aop名称空间
    3）aop 配置
        * 配置切面类 （重复执行代码形成的类）
        * aop配置
            拦截哪些方法 / 拦截到方法后应用通知代码
```

```xml
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:p="http://www.springframework.org/schema/p"
    xmlns:context="http://www.springframework.org/schema/context"
    xmlns:aop="http://www.springframework.org/schema/aop"
    xsi:schemaLocation="
        http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd
        http://www.springframework.org/schema/aop
        http://www.springframework.org/schema/aop/spring-aop.xsd">
    
    <!-- dao 实例 -->
    <bean id="userService" class="com.service.UserService"></bean>
    <!-- 切面类 -->
    <bean id="aop" class="com.aop2.AopLog2"></bean>
    <!-- Aop配置 -->
    <aop:config>
        <!-- 定义一个切入点表达式： 拦截哪些方法 -->
        <aop:pointcut expression="execution(* com.service.UserService.*(..))"
            id="pt" />
        <!-- 切面 -->
        <aop:aspect ref="aop">
            <!-- 环绕通知 -->
            <aop:around method="around" pointcut-ref="pt" />
            <!-- 前置通知： 在目标方法调用前执行 -->
            <aop:before method="begin" pointcut-ref="pt" />
            <!-- 后置通知： -->
            <aop:after method="after" pointcut-ref="pt" />
            <!-- 返回后通知 -->
            <aop:after-returning method="afterReturning"
                pointcut-ref="pt" />
            <!-- 异常通知 -->
            <aop:after-throwing method="afterThrowing"
                pointcut-ref="pt" />
        </aop:aspect>
    </aop:config>

</beans>
```

```java
public class AopLog2 {

    // 前置通知
    public void begin() {
        System.out.println("前置通知");
    }

    //
    // 后置通知
    public void commit() {
        System.out.println("后置通知");
    }

    // 运行通知
    public void returning() {
        System.out.println("运行通知");
    }

    // 异常通知
    public void afterThrowing() {
        System.out.println("异常通知");
    }

    // 环绕通知
    public void around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("环绕通知开始");
        proceedingJoinPoint.proceed();
        System.out.println("环绕通知结束");
    }
}
```

## 3. AOP架构分析

[博客链接](https://blog.csdn.net/qq_26323323/article/details/81012855)

 1）在使用ApplicationContext相关实现类加载bean的时候，会针对所有单例且非懒加载的bean，在构造ApplicationContext的时候就会创建好这些bean，而不会等到使用的时候才去创建。这也就是单例bean默认非懒加载的应用

2）读者需要了解BeanPostProcessor的相关使用，所有实现BeanPostProcessor接口的类，在初始化bean的时候都会调用这些类的方法，一般用于在bean初始化前或后对bean做一些修改。而AOP的功能实现正式基于此，在bean初始化后创建针对该bean的proxy，然后返回给用户该proxy。

 3）结合以上两点，被代理后的bean，实际在ApplicationContext构造完成之后就已经被创建完成，getBean()的操作直接从singletonObjects中获取即可

**AOP功能简单实现（简化前面的代码）**（**用SpringBoot开发**）

**1）引入maven依赖**

```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>1.5.3.RELEASE</version>
</parent>	   
<dependency>      
    <groupId>org.springframework.boot</groupId>  
    <artifactId>spring-boot-starter-aop</artifactId>  
</dependency>
```

**2) 创建接口及其实现类**

```java
public interface Person {
	void say();
}
public class Student implements Person{
 
	public void say(){
		System.out.println("这是一个苦逼的程序员");
	}
}
```

**3）创建切面类**

```java
@Aspect
public class AspectJTest {
	@Pointcut("execution(* *.say(..))")
	public void test(){}
	
	@Before("test()")
	public void before(){
		System.out.println("before test..");
	}
	
	@After("test()")
	public void after(){
		System.out.println("after test..");
	}
	
	@Around("test()")
	public Object around(ProceedingJoinPoint p){
		System.out.println("before1");
		Object o = null;
		try {
			o = p.proceed();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		System.out.println("after1");
		return o;
	}
}
```

**4）创建beans.xml**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:aop="http://www.springframework.org/schema/aop"
    xsi:schemaLocation="
        http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop.xsd">
 
	<aop:aspectj-autoproxy/>
    <bean id="student" class="test.Student"/>
	<bean class="test.AspectJTest"/>
</beans>
```

**5）测试类**

```java

public class Test {
 
	public static void main(String[] args) {
 
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("beans.xml");
		Person bean2 = (Person)ac.getBean("student");
		bean2.say();
	}
}
```

```
before1
before test..
这是一个苦逼的程序员
after1
after test..
```

###  1）寻找 \<aop:aspectj-autoproxy/> 注解对应的解析器

org.springframework.aop.config.AopNamespaceHandler类中有对应的解析行为，代码如下：

```java
public class AopNamespaceHandler extends NamespaceHandlerSupport {
	@Override
	public void init() {
		// In 2.0 XSD as well as in 2.1 XSD.
		registerBeanDefinitionParser("config", new ConfigBeanDefinitionParser());
		registerBeanDefinitionParser("aspectj-autoproxy", new AspectJAutoProxyBeanDefinitionParser());// 就是该段代码
		registerBeanDefinitionDecorator("scoped-proxy", new ScopedProxyBeanDefinitionDecorator());
 
		// Only in 2.0 XSD: moved to context namespace as of 2.1
		registerBeanDefinitionParser("spring-configured", new SpringConfiguredBeanDefinitionParser());
	}
}
```

### 2）了解AspectJAutoProxyBeanDefinitionParser对应的行为

总结：通过以上的代码分析，可知，AspectJAutoProxyBeanDefinitionParser主要的功能就是将AnnotationAwareAspectJAutoProxyCreator注册到Spring容器中，把bean交给Spring去托管。

AnnotationAwareAspectJAutoProxyCreator的功能我们大胆猜测一下：应该也就是生成对象的代理类的相关功能。

```java
class AspectJAutoProxyBeanDefinitionParser implements BeanDefinitionParser {
 
	@Override
	public BeanDefinition parse(Element element, ParserContext parserContext) {
        // 1.注册proxy creator
		AopNamespaceUtils.registerAspectJAnnotationAutoProxyCreatorIfNecessary(parserContext, element);
		extendBeanDefinition(element, parserContext);
		return null;
	}
    ...
    
    // registerAspectJAnnotationAutoProxyCreatorIfNecessary()
    public static void registerAspectJAnnotationAutoProxyCreatorIfNecessary(
			ParserContext parserContext, Element sourceElement) {
        // 注册行为主要内容
		BeanDefinition beanDefinition = AopConfigUtils.registerAspectJAnnotationAutoProxyCreatorIfNecessary(
				parserContext.getRegistry(), parserContext.extractSource(sourceElement));
		useClassProxyingIfNecessary(parserContext.getRegistry(), sourceElement);
		registerComponentIfNecessary(beanDefinition, parserContext);
	}
 
    // registerAspectJAnnotationAutoProxyCreatorIfNecessary()
	public static BeanDefinition registerAspectJAnnotationAutoProxyCreatorIfNecessary(BeanDefinitionRegistry registry, Object source) {
        // 主要就是为了注册AnnotationAwareAspectJAutoProxyCreator类
		return registerOrEscalateApcAsRequired(AnnotationAwareAspectJAutoProxyCreator.class, registry, source);
	}
 
    // 注册类相关代码
    private static BeanDefinition registerOrEscalateApcAsRequired(Class<?> cls, BeanDefinitionRegistry registry, Object source) {
		Assert.notNull(registry, "BeanDefinitionRegistry must not be null");
		if (registry.containsBeanDefinition(AUTO_PROXY_CREATOR_BEAN_NAME)) {
			BeanDefinition apcDefinition = registry.getBeanDefinition(AUTO_PROXY_CREATOR_BEAN_NAME);
			if (!cls.getName().equals(apcDefinition.getBeanClassName())) {
				int currentPriority = findPriorityForClass(apcDefinition.getBeanClassName());
				int requiredPriority = findPriorityForClass(cls);
				if (currentPriority < requiredPriority) {
					apcDefinition.setBeanClassName(cls.getName());
				}
			}
			return null;
		}
        
        // 类似于我们在使用BeanFactory.getBean()时候的操作，生成一个RootBeanDefinition，然后放入map中
		RootBeanDefinition beanDefinition = new RootBeanDefinition(cls);
		beanDefinition.setSource(source);
		beanDefinition.getPropertyValues().add("order", Ordered.HIGHEST_PRECEDENCE);
		beanDefinition.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
		registry.registerBeanDefinition(AUTO_PROXY_CREATOR_BEAN_NAME, beanDefinition);
		return beanDefinition;
	}
```

###  3）分析AnnotationAwareAspectJAutoProxyCreator主要行为

​        通过查看AnnotationAwareAspectJAutoProxyCreator的类层次结构，可知，其实现了BeanPostProcessor接口，实现类为AbstractAutoProxyCreator


### 4）**AbstractAutoProxyCreator主要方法**

```java
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) {
		return bean;
	}
 
    // 主要看这个方法，在bean初始化之后对生产出的bean进行包装
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if (bean != null) {
			Object cacheKey = getCacheKey(bean.getClass(), beanName);
			if (!this.earlyProxyReferences.contains(cacheKey)) {
				return wrapIfNecessary(bean, beanName, cacheKey);
			}
		}
		return bean;
	}
 
    // wrapIfNecessary
	protected Object wrapIfNecessary(Object bean, String beanName, Object cacheKey) {
		if (beanName != null && this.targetSourcedBeans.contains(beanName)) {
			return bean;
		}
		if (Boolean.FALSE.equals(this.advisedBeans.get(cacheKey))) {
			return bean;
		}
		if (isInfrastructureClass(bean.getClass()) || shouldSkip(bean.getClass(), beanName)) {
			this.advisedBeans.put(cacheKey, Boolean.FALSE);
			return bean;
		}
 
		// Create proxy if we have advice.
        // 意思就是如果该类有advice则创建proxy，
		Object[] specificInterceptors = getAdvicesAndAdvisorsForBean(bean.getClass(), beanName, null);
		if (specificInterceptors != DO_NOT_PROXY) {
			this.advisedBeans.put(cacheKey, Boolean.TRUE);
            // 1.通过方法名也能简单猜测到，这个方法就是把bean包装为proxy的主要方法，
			Object proxy = createProxy(
					bean.getClass(), beanName, specificInterceptors, new SingletonTargetSource(bean));
			this.proxyTypes.put(cacheKey, proxy.getClass());
            
            // 2.返回该proxy代替原来的bean
			return proxy;
		}
 
		this.advisedBeans.put(cacheKey, Boolean.FALSE);
		return bean;
	}
```

## 4. 创建proxy过程分析

通过之前的代码结构分析，我们知道，所有的bean在返回给用户使用之前都需要经过AnnotationAwareAspectJAutoProxyCreator类的postProcessAfterInitialization()方法，**而该方法的主要作用也就是将所有拥有advice的bean重新包装为proxy**，那么我们接下来直接分析这个包装为proxy的方法即可，看一下bean如何被包装为proxy，proxy在被调用方法时，是具体如何执行的。

**AbstractAutoProxyCreator.wrapIfNecessary(Object bean, String beanName, Object cacheKey)中的createProxy()代码片段分析**

```java
	protected Object createProxy(
			Class<?> beanClass, String beanName, Object[] specificInterceptors, TargetSource targetSource) {
 
		if (this.beanFactory instanceof ConfigurableListableBeanFactory) {
			AutoProxyUtils.exposeTargetClass((ConfigurableListableBeanFactory) this.beanFactory, beanName, beanClass);
		}
 
        // 1.创建proxyFactory，proxy的生产主要就是在proxyFactory做的
		ProxyFactory proxyFactory = new ProxyFactory();
		proxyFactory.copyFrom(this);
 
		if (!proxyFactory.isProxyTargetClass()) {
			if (shouldProxyTargetClass(beanClass, beanName)) {
				proxyFactory.setProxyTargetClass(true);
			}
			else {
				evaluateProxyInterfaces(beanClass, proxyFactory);
			}
		}
 
        // 2.将当前bean适合的advice，重新封装下，封装为Advisor类，然后添加到ProxyFactory中
		Advisor[] advisors = buildAdvisors(beanName, specificInterceptors);
		for (Advisor advisor : advisors) {
			proxyFactory.addAdvisor(advisor);
		}
 
		proxyFactory.setTargetSource(targetSource);
		customizeProxyFactory(proxyFactory);
 
		proxyFactory.setFrozen(this.freezeProxy);
		if (advisorsPreFiltered()) {
			proxyFactory.setPreFiltered(true);
		}
 
        // 3.调用getProxy获取bean对应的proxy
		return proxyFactory.getProxy(getProxyClassLoader());
	}
```

### 1）创建何种类型的Proxy？JDKProxy还是CGLIBProxy？

```java
    // getProxy()方法
	public Object getProxy(ClassLoader classLoader) {
		return createAopProxy().getProxy(classLoader);
	}
 
    
    // createAopProxy()方法就是决定究竟创建何种类型的proxy
	protected final synchronized AopProxy createAopProxy() {
		if (!this.active) {
			activate();
		}
        // 关键方法createAopProxy()
		return getAopProxyFactory().createAopProxy(this);
	}
 
    // createAopProxy()
	public AopProxy createAopProxy(AdvisedSupport config) throws AopConfigException {
        // 1.config.isOptimize()是否使用优化的代理策略，目前使用与CGLIB
        // config.isProxyTargetClass() 是否目标类本身被代理而不是目标类的接口
        // hasNoUserSuppliedProxyInterfaces()是否存在代理接口
		if (config.isOptimize() || config.isProxyTargetClass() || hasNoUserSuppliedProxyInterfaces(config)) {
			Class<?> targetClass = config.getTargetClass();
			if (targetClass == null) {
				throw new AopConfigException("TargetSource cannot determine target class: " +
						"Either an interface or a target is required for proxy creation.");
			}
            
            // 2.如果目标类是接口或者是代理类，则直接使用JDKproxy
			if (targetClass.isInterface() || Proxy.isProxyClass(targetClass)) {
				return new JdkDynamicAopProxy(config);
			}
            
            // 3.其他情况则使用CGLIBproxy
			return new ObjenesisCglibAopProxy(config);
		}
		else {
			return new JdkDynamicAopProxy(config);
		}
	}
```

### **2）getProxy()方法**

  由1）可知，通过createAopProxy()方法来确定具体使用何种类型的Proxy

  针对于该示例，我们具体使用的为JdkDynamicAopProxy，下面来看下JdkDynamicAopProxy.getProxy()方法

```java
final class JdkDynamicAopProxy implements AopProxy, InvocationHandler, Serializable// JdkDynamicAopProxy类结构，由此可知，其实现了InvocationHandler，则必定有invoke方法，来被调用，也就是用户调用bean相关方法时，此invoke()被真正调用
    // getProxy()
    public Object getProxy(ClassLoader classLoader) {
		if (logger.isDebugEnabled()) {
			logger.debug("Creating JDK dynamic proxy: target source is " + this.advised.getTargetSource());
		}
		Class<?>[] proxiedInterfaces = AopProxyUtils.completeProxiedInterfaces(this.advised, true);
		findDefinedEqualsAndHashCodeMethods(proxiedInterfaces);
        
        // JDK proxy 动态代理的标准用法
		return Proxy.newProxyInstance(classLoader, proxiedInterfaces, this);
	}
```

###  **3）invoke()方法**

 以上的代码模式可以很明确的看出来，使用了JDK动态代理模式，真正的方法执行在invoke()方法里，下面我们来看下该方法，来看下bean方法如何被代理执行的

```java
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		MethodInvocation invocation;
		Object oldProxy = null;
		boolean setProxyContext = false;
 
		TargetSource targetSource = this.advised.targetSource;
		Class<?> targetClass = null;
		Object target = null;
 
		try {
            // 1.以下的几个判断，主要是为了判断method是否为equals、hashCode等Object的方法
			if (!this.equalsDefined && AopUtils.isEqualsMethod(method)) {
				// The target does not implement the equals(Object) method itself.
				return equals(args[0]);
			}
			else if (!this.hashCodeDefined && AopUtils.isHashCodeMethod(method)) {
				// The target does not implement the hashCode() method itself.
				return hashCode();
			}
			else if (method.getDeclaringClass() == DecoratingProxy.class) {
				// There is only getDecoratedClass() declared -> dispatch to proxy config.
				return AopProxyUtils.ultimateTargetClass(this.advised);
			}
			else if (!this.advised.opaque && method.getDeclaringClass().isInterface() &&
					method.getDeclaringClass().isAssignableFrom(Advised.class)) {
				// Service invocations on ProxyConfig with the proxy config...
				return AopUtils.invokeJoinpointUsingReflection(this.advised, method, args);
			}
 
			Object retVal;
 
			if (this.advised.exposeProxy) {
				// Make invocation available if necessary.
				oldProxy = AopContext.setCurrentProxy(proxy);
				setProxyContext = true;
			}
 
			// May be null. Get as late as possible to minimize the time we "own" the target,
			// in case it comes from a pool.
			target = targetSource.getTarget();
			if (target != null) {
				targetClass = target.getClass();
			}
 
			// 2.获取当前bean被拦截方法链表
			List<Object> chain = this.advised.getInterceptorsAndDynamicInterceptionAdvice(method, targetClass);
 
			// 3.如果为空，则直接调用target的method
			if (chain.isEmpty()) {
				Object[] argsToUse = AopProxyUtils.adaptArgumentsIfNecessary(method, args);
				retVal = AopUtils.invokeJoinpointUsingReflection(target, method, argsToUse);
			}
            
            // 4.不为空，则逐一调用chain中的每一个拦截方法的proceed
			else {
				// We need to create a method invocation...
				invocation = new ReflectiveMethodInvocation(proxy, target, method, args, targetClass, chain);
				// Proceed to the joinpoint through the interceptor chain.
				retVal = invocation.proceed();
			}
 
			...
			return retVal;
		}
		...
	}
```

###  4）拦截方法真正被执行调用invocation.proceed()

```java
	public Object proceed() throws Throwable {
		//	We start with an index of -1 and increment early.
		if (this.currentInterceptorIndex == this.interceptorsAndDynamicMethodMatchers.size() - 1) {
			return invokeJoinpoint();
		}
 
		Object interceptorOrInterceptionAdvice =
				this.interceptorsAndDynamicMethodMatchers.get(++this.currentInterceptorIndex);
		if (interceptorOrInterceptionAdvice instanceof InterceptorAndDynamicMethodMatcher) {
			// Evaluate dynamic method matcher here: static part will already have
			// been evaluated and found to match.
			InterceptorAndDynamicMethodMatcher dm =
					(InterceptorAndDynamicMethodMatcher) interceptorOrInterceptionAdvice;
			if (dm.methodMatcher.matches(this.method, this.targetClass, this.arguments)) {
				return dm.interceptor.invoke(this);
			}
			else {
				// Dynamic matching failed.
				// Skip this interceptor and invoke the next in the chain.
				return proceed();
			}
		}
		else {
			// It's an interceptor, so we just invoke it: The pointcut will have
			// been evaluated statically before this object was constructed.
			return ((MethodInterceptor) interceptorOrInterceptionAdvice).invoke(this);
		}
	}
```

 总结4：依次遍历拦截器链的每个元素，然后调用其实现，将真正调用工作委托给各个增强器



总结：

    纵观以上过程可知：实际就是为bean创建一个proxy，JDKproxy或者CGLIBproxy，然后在调用bean的方法时，会通过proxy来调用bean方法
    
    重点过程可分为：
    
    1）通过AspectJAutoProxyBeanDefinitionParser类将AnnotationAwareAspectJAutoProxyCreator注册到Spring容器中。
    
    2）AnnotationAwareAspectJAutoProxyCreator类的postProcessAfterInitialization()方法将所有有advice的bean重新包装成proxy。
    
    3）调用bean方法时通过proxy来调用，proxy依次调用增强器的相关方法，来实现方法切入。
