package com.wx.wxlogin.util;

import java.util.stream.Stream;

/**
 * 排序工具类
 */
public class SortUtil {

    private static Integer[] initArray = {2, 45, 6, 1, 4, 65, 52, 22, 3};

    /**
     * 冒泡排序
     *
     * @param array
     */
    public static void bubbleSort(Integer[] array) {

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j + 1];
                    array[j + 1] = array[j];
                    array[j] = temp;
                }
            }
        }
    }


    /**
     * 插入排序
     *
     * @param array
     */
    public static void insertSort(Integer[] array) {

        for (int i = 1; i < array.length; i++) {
            int maxValue = array[i];
            int j = i - 1;
            for (; j >= 0; j--) {
                if (array[j] > maxValue) {
                    array[j + 1] = array[j];
                } else {
                    break;
                }
            }
            array[j + 1] = maxValue;
        }
    }

    /**
     * 快排
     */
    public static void quickSort(Integer[] array, Integer low, Integer height) {

        if (low > height) return;

        int left = low, right = height, temp = array[low];

        while (left < right) {

            while (left < right && array[right] >= temp) {
                right--;
            }

            while (left < right && array[left] <= temp) {
                left++;
            }
            //调换位置
            if (left < right) {
                int original = array[left];
                array[left] = array[right];
                array[right] = original;
            }
        }

        // 和基准位调换
        array[low] = array[left];
        array[left] = temp;

        quickSort(array, low, right - 1);
        quickSort(array, right + 1, height);
    }

    public static void main(String[] args) {
//        SortUtil.bubbleSort(SortUtil.initArray);
//        SortUtil.insertSort(initArray);
        quickSort(initArray, 0, initArray.length - 1);
        Stream.of(initArray).forEach(System.out::println);
    }
}
