package com.study.aop.myaop;

import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 项目名称：myaop
 * 类名称：ApplicationContextImpl
 * 类描述：
 * 创建人：ygy
 * 邮箱：ygy@cdqcp.com
 * 创建时间：2020/2/16 16:38
 * 修改人：ygy
 * 修改时间：2020/2/16 16:38
 * 修改备注：
 */
public class ApplicationContextImpl implements ApplicationContext {

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

    private Object proxyEnhance(Object bean) {
        //判断是否要切面增强
        if(this.aspect!=null && bean.getClass().getName().matches(this.aspect.getPointcut().getClassPattern())){
            return Proxy.newProxyInstance(
                    bean.getClass().getClassLoader(),
                    bean.getClass().getInterfaces(),
                    new AopInvocationHandler(aspect,bean));
        }

        return bean;
    }

    private Object createBeanInstance(String beanName) throws Throwable {
        return this.beanDefinitionMap.get(beanName).newInstance();
    }

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
