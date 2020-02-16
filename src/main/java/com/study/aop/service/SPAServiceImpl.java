package com.study.aop.service;

import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 项目名称：myaop
 * 类名称：SPAServiceImpl
 * 类描述：
 * 创建人：ygy
 * 邮箱：ygy@cdqcp.com
 * 创建时间：2020/2/16 14:53
 * 修改人：ygy
 * 修改时间：2020/2/16 14:53
 * 修改备注：
 */
@Service
public class SPAServiceImpl implements SPAService {

    private Random random=new Random();

    private int bound=5;

    @Override
    public void aromaOilMessage(String customer) {
        try {
            TimeUnit.SECONDS.sleep(random.nextInt(bound));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(customer+"享受完aromaOilMessage服务！");
    }

    @Override
    public void rest() {

    }
}
