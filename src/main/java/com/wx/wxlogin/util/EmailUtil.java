package com.wx.wxlogin.util;

import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.security.servlet.ApplicationContextRequestMatcher;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
public class EmailUtil {

    // 1.必须要 @Component 将自己写的类先用springboot容器先管理起来
    // 2. @Autowired 注入到你要调用的类中再使用用，不能直接使用。
    @Autowired
    private JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String from;
    @Value("${fyq.mail}")
    private String[] sheMail;
    @Value("${fyq.wxx}")
    private String[] wxxMail;

    /**
     * 发送短信
     * @param subject
     * @param message
     */
    public void sendMessage(String subject,String message) throws Exception {

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            helper.setFrom(from);//发送者邮件邮箱
            helper.setTo(wxxMail);//收邮件者邮箱
            helper.setSubject(subject);//发件主题
            helper.setText(message);//发件内容
            mailSender.send(helper.getMimeMessage());//发送邮件
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new Exception("发送失败");
        }

    }


}
