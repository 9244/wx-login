package com.wx.wxlogin.threadUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @description:
 * @author: WXxxx
 * @time: 2021/8/8 17:19
 */
public class ThreadLocalUtil {

    public ThreadLocal<String> randomThreadLocal = new ThreadLocal<String>(){

        @Override
        protected String initialValue() {
            return randomThreadLocal.get();
        }
    };

    public String getRandomStr(){
        List<Object> list = Collections.synchronizedList(new ArrayList<>());
        list.add("");
        randomThreadLocal.set("d");
        return randomThreadLocal.get();
    }


}
