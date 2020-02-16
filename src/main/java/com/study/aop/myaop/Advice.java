package com.study.aop.myaop;

import java.lang.reflect.Method;

/**
 * 项目名称：myaop
 * 类名称：Advice
 * 类描述：
 * 创建人：ygy
 * 邮箱：ygy@cdqcp.com
 * 创建时间：2020/2/16 16:08
 * 修改人：ygy
 * 修改时间：2020/2/16 16:08
 * 修改备注：
 */
public interface Advice {

    //定义一个怎样的方法？
    //用途？ --》 用户提供增强逻辑
    // 方法前     方法后  在实现中要能调用执行业务目标方法
    Object invoke(Object target, Method method, Object[] args) throws Throwable;
}
