package com.study.aop.myaopdemo;

import com.study.aop.myaop.*;
import com.study.aop.service.KTVService;
import com.study.aop.service.KTVServiceImpl;
import com.study.aop.service.SPAService;
import com.study.aop.service.SPAServiceImpl;

/**
 * 项目名称：myaop
 * 类名称：Aopmain
 * 类描述：
 * 创建人：ygy
 * 邮箱：ygy@cdqcp.com
 * 创建时间：2020/2/16 16:19
 * 修改人：ygy
 * 修改时间：2020/2/16 16:19
 * 修改备注：
 */
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
