package com.study.aop.myaopdemo;

import com.study.aop.myaop.Advice;

import java.lang.reflect.Method;

/**
 * 项目名称：myaop
 * 类名称：TimeCsAdvice
 * 类描述：
 * 创建人：ygy
 * 邮箱：ygy@cdqcp.com
 * 创建时间：2020/2/16 16:12
 * 修改人：ygy
 * 修改时间：2020/2/16 16:12
 * 修改备注：
 */
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
