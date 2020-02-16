package com.study.aop.myaop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 项目名称：myaop
 * 类名称：AopInvocationHandler
 * 类描述：
 * 创建人：ygy
 * 邮箱：ygy@cdqcp.com
 * 创建时间：2020/2/16 17:17
 * 修改人：ygy
 * 修改时间：2020/2/16 17:17
 * 修改备注：
 */
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
