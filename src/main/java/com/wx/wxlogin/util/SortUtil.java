package com.wx.wxlogin.util;

import java.util.Arrays;
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

    /**
     * 堆排序
     * @param array
     * @param length
     */
    public static void heapSort(Integer[] array,int length){

        copueteNode(array,length);

        for (int i = length - 1; i > 0 ; i --){
            int temp = array[0];
            array[0] = array[i];
            array[i] = temp;
            copueteNode(array,i);
            System.out.println(Arrays.toString(array));
        }

    }
    public static void copueteNode(Integer[] array,int length){
        // 计算出最后一个非叶子结点
        for (int i = length/2 - 1; i >=0 ; i--) {
            heapAdjust(array,i,length);
        }
    }

    public static void heapAdjust(Integer[] array,Integer node,Integer length){

        int temp = array[node];
        //计算出做叶子结点
        int leftNode = node * 2 + 1;
       while (leftNode < length){
           // 判断是否有叶子结点，和左右两个叶子结点的大小
           if (leftNode + 1 < length && array[leftNode + 1] > array[leftNode]){
               leftNode++;
           }
           // 判断结点值和叶子结点的大小
           if (array[node] < array[leftNode]){
               array[node] = array[leftNode];
               array[leftNode] = temp;
               //结点交换
               node = leftNode;
               leftNode = node * 2 + 1;
           }else {
               break;
           }
       }

    }

    public static void main(String[] args) {
//        SortUtil.bubbleSort(SortUtil.initArray);
//        SortUtil.insertSort(initArray);
//        quickSort(initArray, 0, initArray.length - 1);
//        Stream.of(initArray).forEach(System.out::println);
        heapSort(initArray,initArray.length);
    }
}
