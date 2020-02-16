package com.study.aop.myaop;

/**
 * 项目名称：myaop
 * 类名称：Pointcut
 * 类描述：
 * 创建人：ygy
 * 邮箱：ygy@cdqcp.com
 * 创建时间：2020/2/16 16:23
 * 修改人：ygy
 * 修改时间：2020/2/16 16:23
 * 修改备注：
 */
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

    public String getClassPattern() {
        return classPattern;
    }

    public void setClassPattern(String classPattern) {
        this.classPattern = classPattern;
    }

    public String getMethodPattern() {
        return methodPattern;
    }

    public void setMethodPattern(String methodPattern) {
        this.methodPattern = methodPattern;
    }
}
