package com.wx.wxlogin.task;

import com.wx.wxlogin.util.EmailUtil;
import com.wx.wxlogin.util.HttpClientUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class EmailTask {

    private static final Logger logger = LoggerFactory.getLogger(EmailTask.class);

    @Autowired
    EmailUtil emailUtil;

    private static Integer sendCount = 0;

    @Scheduled(cron = "0 */1 * * * ?")
    public void sendEmail(){
        String message = HttpClientUtil.getOneS();
        System.out.println(message);
        if (message.contains("访问限制")){
            message = "访问频繁，我是你爸爸。";
        }
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        try {
            emailUtil.sendMessage("宝贝",message);
            sendCount++;
            logger.info("已累计发送" + sendCount + "条短信");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("邮件发送失败，失败原因："  + e.getMessage());
        }
    }
}
