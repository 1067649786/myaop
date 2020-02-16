package com.study.aop.service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 项目名称：myaop
 * 类名称：KTVServiceImpl
 * 类描述：
 * 创建人：ygy
 * 邮箱：ygy@cdqcp.com
 * 创建时间：2020/2/16 17:25
 * 修改人：ygy
 * 修改时间：2020/2/16 17:25
 * 修改备注：
 */
public class KTVServiceImpl implements KTVService {

    private Random random=new Random();

    private int bound=5;

    @Override
    public void momoSing(String customer) {
        try {
            TimeUnit.SECONDS.sleep(random.nextInt(bound));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(customer+"享受完momoSing服务！");
    }

    @Override
    public void cnmMessage(String customer) {
        try {
            TimeUnit.SECONDS.sleep(random.nextInt(bound));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(customer+"享受完cnmMessage服务！");
    }
}
