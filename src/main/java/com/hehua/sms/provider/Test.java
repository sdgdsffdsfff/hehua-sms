/**
 * 
 */
package com.hehua.sms.provider;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hehua.sms.provider.exception.SmsException;
import com.hehua.sms.provider.guodu.GuoduSmsProvider;

/**
 * @author zhihua
 *
 */
public class Test {

    public static void main(String[] args) {

        try {

            ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
                    "classpath:/spring/applicationContext*.xml");

            //            SmsProvider smsProvider = applicationContext.getBean(SmsProvider.class);

            SmsProvider smsProvider = new GuoduSmsProvider();
            System.out
                    .println(smsProvider.sendDownstreamMessage("18601189513,13141097831", "恭喜发财"));

            // 这
        } catch (SmsException e) {
            e.printStackTrace();
        }
    }
}
