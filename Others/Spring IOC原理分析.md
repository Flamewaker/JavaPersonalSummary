# Spring IOC

[博客链接](https://blog.csdn.net/zhangcongyi420/article/details/89419715)

**spring ioc指的是控制反转，IOC容器负责实例化、定位、配置应用程序中的对象及建立这些对象间的依赖。交由Spring容器统一进行管理，从而实现松耦合**

在Java开发中，Ioc意味着将你设计好的对象交给容器控制，而不是传统的在你的对象内部直接控制。

为何是反转，哪些方面反转了：有反转就有正转，传统应用程序是由我们自己在对象中主动控制去直接获取依赖对象，也就是正转；而反转则是由容器来帮忙创建及注入依赖对象；为何是反转？因为由容器帮我们查找及注入依赖对象，对象只是被动的接受依赖对象，所以是反转；哪些方面反转了？依赖对象的获取被反转了。

## 1. IOC实现原理

**使用反射机制+XML技术**

当web容器启动的时候，spring的全局bean的管理器会去xml配置文件中扫描的包下面获取到所有的类，并根据你使用的注解，进行对应的封装，封装到全局的bean容器中进行管理，一旦容器初始化完毕，beanID以及bean实例化的类对象信息就全部存在了，现在我们需要在某个service里面调用另一个bean的某个方法的时候，我们只需要依赖注入进来另一个bean的Id即可，调用的时候，spring会去初始化完成的bean容器中获取即可，如果存在就把依赖的bean的类的实例化对象返回给你，你就可以调用依赖的bean的相关方法或属性等；


1. 首先我们自定义两个注解，我们知道在业务类中经常使用 @Service来标记这个类是bean管理的类，而@Autowired或者@Resource用于bean之间相互依赖使用，

```java
// 自定义@Service注解 注入bean容器
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface SelfService {

}
```

```java
//模拟@Autowired注解
@Target({ TYPE, FIELD, METHOD })
@Retention(RUNTIME)
public @interface SelfAutowired {

}
```

2. 模拟spring的bean容器类，想必使用过spring框架在进行整合测试的时候，都使用到下面这段代码，这段代码的作用其实就是模拟在spring启动加载的时候，通过这个类去初始化一个bean 的容器管理类，所有的bean的信息解析和保存都会在这个类里面进行，下面我们写一个这样的类来还原一下这个过程: 
   

```java
SelfPathXmlApplicationContext app = new SelfPathXmlApplicationContext("com.congge.service.impl");
```

自定义spring的bean容器类：

```java
/**
 *自定义bean管理器
 */
public class SelfPathXmlApplicationContext {
    private String packageName;

    // 封装所有的bean容器
    private ConcurrentHashMap<String, Object> beans = null;

    /**
     * 该类被创建出来的时候加载
     * @param packageName
     * @throws Exception
     */
    public SelfPathXmlApplicationContext(String packageName) throws Exception {
        beans = new ConcurrentHashMap<String, Object>();
        this.packageName = packageName;
        initBeans();
        initEntryField();
    }

    /**
     * 初始化bean的实例对象的所有属性
     * @throws Exception
     */
    private void initEntryField() throws Exception {
        // 1.遍历所有的bean容器对象
        for (Entry<String, Object> entry : beans.entrySet()) {
            // 2.判断属性上面是否有加注解
            Object bean = entry.getValue();
            attriAssign(bean);
        }
    }

    /**
     * 根据beanId获取bean名称
     * @param beanId
     * @return
     * @throws Exception
     */
    public Object getBean(String beanId) throws Exception {
        if (StringUtils.isEmpty(beanId)) {
            throw new Exception("beanId参数不能为空");
        }
        // 1.从spring容器获取bean
        Object object = beans.get(beanId);
        // attriAssign(object);
        return object;
    }

    /**
     * 获取扫描包下面的所有bean
     */
    private void initBeans() throws Exception {
        // 1.使用java的反射机制扫包,[获取当前包下所有的类]
        List<Class<?>> classes = ClassParseUtil.getClasses(packageName);
        // 2、判断类上面是否存在注入的bean的注解
        ConcurrentHashMap<String, Object> classExisAnnotation = findClassExisAnnotation(classes);
        if (classExisAnnotation == null || classExisAnnotation.isEmpty()) {
            throw new Exception("该包下没有任何类加上注解");
        }

    }

    /**
     * 判断类上是否存在注入自定义的bean的注解
     * @param classes
     * @return
     * @throws Exception
     */
    public ConcurrentHashMap<String, Object> findClassExisAnnotation(List<Class<?>> classes) throws Exception {
        for (Class<?> classInfo : classes) {
            // 判断类上是否有注解 [获取自定义的service注解]
            SelfService annotation = classInfo.getAnnotation(SelfService.class);
            if (annotation != null) {
                // 获取当前类的类名
                String className = classInfo.getSimpleName();
                String beanId = toLowerCaseFirstOne(className);
                Object newInstance = newInstance(classInfo);
                beans.put(beanId, newInstance);
            }

        }
        return beans;
    }

    // 首字母转小写
    public static String toLowerCaseFirstOne(String s) {
        if (Character.isLowerCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
    }

    /**
     * 通过class名称获取类的实例化对象
     * @param classInfo
     * @return
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public Object newInstance(Class<?> classInfo)
            throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        return classInfo.newInstance();
    }

    /**
     * 依赖注入注解原理
     * @param object
     * @throws Exception
     */
    public void attriAssign(Object object) throws Exception {

        // 1.使用反射机制,获取当前类的所有属性
        Class<? extends Object> classInfo = object.getClass();
        Field[] declaredFields = classInfo.getDeclaredFields();

        // 2.判断当前类属性是否存在注解
        for (Field field : declaredFields) {
            SelfAutowired extResource = field.getAnnotation(SelfAutowired.class);
            if (extResource != null) {
                // 获取属性名称
                String beanId = field.getName();
                Object bean = getBean(beanId);
                if (bean != null) {
                    // 3.默认使用属性名称，查找bean容器对象 1参数 当前对象 2参数给属性赋值
                    field.setAccessible(true); // 允许访问私有属性
                    field.set(object, bean);
                }
            }
        }

    }

}
```

1) 当这个类被初始化的时候，通过构造函数里面的两个方法，通过外部传入指定的包名，解析该包下面的所有类和相关注解，其实现原理主要是使用了反射。

```java
	/**
     * 该类被创建出来的时候加载
     * @param packageName
     * @throws Exception
     */
    public SelfPathXmlApplicationContext(String packageName) throws Exception {
        beans = new ConcurrentHashMap<String, Object>();
        this.packageName = packageName;
        initBeans();
        initEntryField();
    }
```

2) 通过一个工具类获取到了所有的类的实例化集合后，我们对这个集合进行遍历，具体的执行方法可以看findClassExisAnnotation 这个方法

```java
	/**
     * 获取扫描包下面的所有bean
     */
    private void initBeans() throws Exception {
        // 1.使用java的反射机制扫包,[获取当前包下所有的类]
        List<Class<?>> classes = ClassParseUtil.getClasses(packageName);
        // 2、判断类上面是否存在注入的bean的注解
        ConcurrentHashMap<String, Object> classExisAnnotation = findClassExisAnnotation(classes);
        if (classExisAnnotation == null || classExisAnnotation.isEmpty()) {
            throw new Exception("该包下没有任何类加上注解");
        }
    }
```

```java
	/**
     * 判断类上是否存在注入自定义的bean的注解
     * @param classes
     * @return
     * @throws Exception
     */
    public ConcurrentHashMap<String, Object> findClassExisAnnotation(List<Class<?>> classes) throws Exception {
        for (Class<?> classInfo : classes) {
            // 判断类上是否有注解 [获取自定义的service注解]
            SelfService annotation = classInfo.getAnnotation(SelfService.class);
            if (annotation != null) {
                // 获取当前类的类名
                String className = classInfo.getSimpleName();
                String beanId = toLowerCaseFirstOne(className);
                //通过反射进行实列化
                Object newInstance = newInstance(classInfo);
                beans.put(beanId, newInstance);
            }

        }
        return beans;
    }
```

```java
	/**
     * 通过class名称获取类的实例化对象
     * @param classInfo
     * @return
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public Object newInstance(Class<?> classInfo)
            throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        return classInfo.newInstance();
    }
```



3) 这一步完成之后，包含了自定义注解[@Service]的相关类对象就存在了内存集合中了，如果在web容器启动完毕之后，使用自己的bean的时候就可以直接通过getBean这个方法去容器里面直接获取就可以了，通过这个方法就可以拿到当前beanId对应的类的实例化对象，可以使用里面的相关方法，

```java
/**
     * 根据beanId获取bean名称
     * @param beanId
     * @return
     * @throws Exception
     */
    public Object getBean(String beanId) throws Exception {
        if (StringUtils.isEmpty(beanId)) {
            throw new Exception("beanId参数不能为空");
        }
        // 1.从spring容器获取bean
        Object object = beans.get(beanId);
        // attriAssign(object);
        return object;
    }
```

4) 但是到这里并没有完事啊，假如在我们某个标注了@Service的类里面有下面这样的注解，即依赖了其他的某个bean，比如，在我们的userService类里面依赖了orderService 加上@SelfAutowired，就形成了所谓的依赖注入。

同样，依赖注入也是通过上面相同的方式，在initBean()方法结束之后立即执行，我们来看看这个方法。在initEntryField()这个方法里，要做的事情就是遍历上述初始化好的所有bean，然后再去每个bean的实例对象中解析并封装属性相关的对应信息，下面看一下initEntryField()这个方法。

```java
	/**
     * 初始化bean的实例对象的所有属性
     * @throws Exception
     */
    private void initEntryField() throws Exception {
        // 1.遍历所有的bean容器对象
        for (Entry<String, Object> entry : beans.entrySet()) {
            // 2.判断属性上面是否有加注解
            Object bean = entry.getValue();
            attriAssign(bean);
        }
    }
```

```java
 	/**
     * 依赖注入注解原理
     * @param object
     * @throws Exception
     */
    public void attriAssign(Object object) throws Exception {

        // 1.使用反射机制,获取当前类的所有属性
        Class<? extends Object> classInfo = object.getClass();
        Field[] declaredFields = classInfo.getDeclaredFields();

        // 2.判断当前类属性是否存在注解
        for (Field field : declaredFields) {
            SelfAutowired extResource = field.getAnnotation(SelfAutowired.class);
            if (extResource != null) {
                // 获取属性名称
                String beanId = field.getName();
                Object bean = getBean(beanId);
                if (bean != null) {
                    // 3.默认使用属性名称，查找bean容器对象 1参数 当前对象 2参数给属性赋值
                    field.setAccessible(true); // 允许访问私有属性
                    field.set(object, bean);
                }
            }
        }

    }
```



