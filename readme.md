#### 实现简易的 aop 和 ioc 功能

首先定义一个 Advice 接口，在接口中定义一个 invoke 方法，
用户在定义增强方法时需要实现Advice接口，在实现中要能调用执行业务目标方法
```java
public interface Advice {

    //定义一个怎样的方法？
    //用途？ --》 用户提供增强逻辑
    // 方法前     方法后  在实现中要能调用执行业务目标方法
    Object invoke(Object target, Method method, Object[] args) throws Throwable;
}
```

定义一个TimeCsAdvice类，用来计算方法执行时间，通过反射来调用service曾需要增强的方法

```java
public class TimeCsAdvice implements Advice {

    @Override
    public Object invoke(Object target, Method method, Object[] args) throws Throwable {
        long stime=System.currentTimeMillis(); //开始时间
        Object ret=method.invoke(target,args); //调用执行方法
        long useTime=System.currentTimeMillis()-stime; //得到耗时
        System.out.println("记录："+target.getClass().getName()+"."+method.getName()+"耗时："+(useTime/1000)+"秒");
        return ret;
    }
}
```

定义一个Pointcut类，这里使用正则表达式的方式来匹配实际增强的类和方法

```java
public class Pointcut {
    // xxx类 xx方法

    // 类名匹配模式
    private String classPattern;

    // 方法级的匹配模式
    private String methodPattern;

    public Pointcut(String classPattern, String methodPattern) {
        this.classPattern = classPattern;
        this.methodPattern = methodPattern;
    }
}
```

有了Advice(增强)和Pointcut(切入点)就可以组成一个Aspect(切面)

```java
public class Aspect {

    // 切点表达式
    private Pointcut pointcut;

    // 增强通知
    private Advice advice;

    public Aspect(Pointcut pointcut, Advice advice) {
        this.pointcut = pointcut;
        this.advice = advice;
    }
}
```

TimeCsAdvice为用户给出的增强逻辑，Pointcut为用户给出的增强逻辑的类和方法的正则表达式，由前面两者组成一个aspect切面

```
// 用户给出了增强逻辑
Advice advice=new TimeCsAdvice();
Pointcut pointcut=new Pointcut("com\\.study\\.aop\\.service\\..*",".*Message");
Aspect aspect=new Aspect(pointcut,advice);
```

到此用户已经给出了他的切面，框架需要把这个切面生效，用户要使用xxxService对象时，
不能让用户自己new对象，因为如果通过用户自己创建对象，无法让增强的逻辑生效，所以需要一个
工厂，工厂能对目标对象进行加工处理，这里就需要ioc，所以ioc是aop的基石<br>
新建一个ApplicationContext接口

```java
public interface ApplicationContext {
    //获取bean
    Object getBean(String beanName) throws Throwable;
    //注册bean的定义
    void registerBeanDefinition(String beanName,Class<?> beanClass);
    //设置切面
    void setAspect(Aspect aspect);
}
```

新建ApplicationContextImpl实现ApplicationContext接口

```java
public class ApplicationContextImpl implements ApplicationContext {
    //使用map来存储bean的定义<beanName,beanClass>
    private Map<String,Class<?>> beanDefinitionMap=new ConcurrentHashMap<>();

    // 可以有多个切面，这里用一个来示例
    private Aspect aspect;

    
    @Override
    public Object getBean(String beanName) throws Throwable {
        //怎么创建这个名字的bean对象  beanName -- 类
        Object bean=createBeanInstance(beanName);
        // 返回一个加工了的对象  代理
        bean=proxyEnhance(bean);
        return bean;
    }
    //使用代理进行增强
    private Object proxyEnhance(Object bean) {
        //判断是否要切面增强，通过正则匹配判断bean的类名与用户给出的切面是否匹配
        if(this.aspect!=null && bean.getClass().getName().matches(this.aspect.getPointcut().getClassPattern())){
            //代理使用jdk自带的动态代理，需要传入对象的类加载器、实现的接口和InvocationHandler
            //传入类加载器是因为需要用与被代理类对象相同的类加载器来加载代理后的对象
            //传入接口是因为代理前和代理后的对象是同一个类型的
            //最后在AopInvocationHandler中调用增强的方法
            return Proxy.newProxyInstance(
                    bean.getClass().getClassLoader(),
                    bean.getClass().getInterfaces(),
                    new AopInvocationHandler(aspect,bean));
        }

        return bean;
    }
    //通过反射获取到对应beanName的beanClass的实例化对象
    private Object createBeanInstance(String beanName) throws Throwable {
        return this.beanDefinitionMap.get(beanName).newInstance();
    }

    //注册bean定义就是将beanName和beanClass一起保存到map中
    @Override
    public void registerBeanDefinition(String beanName, Class<?> beanClass) {
        // 保存下来
        this.beanDefinitionMap.put(beanName,beanClass);
    }

    @Override
    public void setAspect(Aspect aspect) {
        this.aspect=aspect;
    }
}
```

新建AopInvocationHandler类,实现InvocationHandler来自定义invoke方法

```java
public class AopInvocationHandler implements InvocationHandler {

    private Aspect aspect;

    private Object bean;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //应用增强逻辑
        //判断当前方法是否是用户要增强的方法
        if(method.getName().matches(aspect.getPointcut().getMethodPattern())){
            return aspect.getAdvice().invoke(bean,method,args);
        }
        return method.invoke(bean,args);
    }

    public AopInvocationHandler(Aspect aspect, Object bean) {
        this.aspect = aspect;
        this.bean = bean;
    }
}
```

最后来使用一下自己写的aop框架

```java
public class AopMain {
    public static void main(String[] args) throws Throwable {
        // 用户给出了增强逻辑
        Advice advice=new TimeCsAdvice();
        Pointcut pointcut=new Pointcut("com\\.study\\.aop\\.service\\..*",".*Message");
        Aspect aspect=new Aspect(pointcut,advice);
        // 用户已经给出了他的切面
        // 框架要把这个切面生效
        // 要使用xxxService对象   能让用户自己去new 对象吗
        // 不能  需要用到一个工厂  工厂能对目标对象进行加工处理
        // IOC 是 AOP 的基石
        ApplicationContext context=new ApplicationContextImpl();
        //注册bean定义
        context.registerBeanDefinition("spa", SPAServiceImpl.class);
        context.registerBeanDefinition("ktv", KTVServiceImpl.class);
        // xml  注解
        context.setAspect(aspect);
        SPAService spaService=(SPAService) context.getBean("spa");
        KTVService ktvService=(KTVService) context.getBean("ktv");
        spaService.aromaOilMessage("cnm");
        ktvService.momoSing("rnm");
        ktvService.cnmMessage("aaa");
    }
}
```
