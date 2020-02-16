package com.study.aop.myaop;

/**
 * 项目名称：myaop
 * 类名称：ApplicationContext
 * 类描述：
 * 创建人：ygy
 * 邮箱：ygy@cdqcp.com
 * 创建时间：2020/2/16 16:36
 * 修改人：ygy
 * 修改时间：2020/2/16 16:36
 * 修改备注：
 */
public interface ApplicationContext {

    Object getBean(String beanName) throws Throwable;

    void registerBeanDefinition(String beanName,Class<?> beanClass);

    void setAspect(Aspect aspect);
}
