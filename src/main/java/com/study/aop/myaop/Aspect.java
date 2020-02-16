package com.study.aop.myaop;

/**
 * 项目名称：myaop
 * 类名称：Aspect
 * 类描述：
 * 创建人：ygy
 * 邮箱：ygy@cdqcp.com
 * 创建时间：2020/2/16 16:26
 * 修改人：ygy
 * 修改时间：2020/2/16 16:26
 * 修改备注：
 */
public class Aspect {

    // 切点表达式
    private Pointcut pointcut;

    // 增强通知
    private Advice advice;

    public Aspect(Pointcut pointcut, Advice advice) {
        this.pointcut = pointcut;
        this.advice = advice;
    }

    public Pointcut getPointcut() {
        return pointcut;
    }

    public void setPointcut(Pointcut pointcut) {
        this.pointcut = pointcut;
    }

    public Advice getAdvice() {
        return advice;
    }

    public void setAdvice(Advice advice) {
        this.advice = advice;
    }
}
