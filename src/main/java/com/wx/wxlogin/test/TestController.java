package com.wx.wxlogin.test;

import com.wx.wxlogin.entity.Student;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TestController {


    @Test
    public void test1(){
        Student student = new Student();
        Student student1 = new Student();

        student.setName("wxx");
        student.setAge(19);

        student1.setName("lll");
        student1.setAge(22);

        List<Student> list = new ArrayList<Student>();
        list.stream().sorted((x1,x2) -> x1.getAge() -x2.getAge()).collect(Collectors.toList());
    }


}
