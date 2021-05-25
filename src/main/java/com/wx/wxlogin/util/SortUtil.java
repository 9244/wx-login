package com.wx.wxlogin.util;

import java.util.stream.Stream;

public class SortUtil {

    private static Integer[] initArray = {2,45,6,1,4,65,52,22,3};

    public static void bubbleSort( Integer[] array){

        for (int i = 0; i < array.length; i ++){
            for (int j =0; j< array.length - i - 1; j ++){
                if (array[j] > array[j+1]){
                    int temp = array[j+1];
                    array[j+1] = array[j];
                    array[j] = temp;
                }
            }
        }
    }


    public static void insertSort(Integer[] array){

        for (int i = 1; i < array.length; i++) {
            int maxValue = array[i];
            int j = i -1 ;
            for (; j >= 0 ; j--) {
                if (array[j] > maxValue){
                    array[j+1] = array[j];
                }else {
                    break;
                }
            }
            array[j+1] = maxValue;
        }
    }

    public static void main(String[] args) {
//        SortUtil.bubbleSort(SortUtil.initArray);
        SortUtil.insertSort(initArray);
        Stream.of(initArray).forEach(System.out::println);
    }
}
