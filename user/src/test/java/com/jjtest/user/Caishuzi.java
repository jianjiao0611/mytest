package com.jjtest.user;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Caishuzi {


    public static void main(String[] args) {
//        int zNum = (int) (Math.random()*100 + 1);
//        while (true) {
//            String str = JOptionPane.showInputDialog(null, "请输入1-100数字");
//            int num = Integer.parseInt(str);
//            if(num == zNum){
//                JOptionPane.showMessageDialog(null, "答对了！");
//                break;
//            } else if(num < zNum){
//                JOptionPane.showMessageDialog(null, "小了！");
//            } else {
//                JOptionPane.showMessageDialog(null, "大了！");
//            }
//        }

        int arr1[] = new int[35];
        int arr2[] = new int[12];
        for(int i=0; i<35;i++){
            arr1[i] = i+1;
        }
        for(int i=0; i<12;i++){
            arr2[i] = i+1;
        }
        System.out.println(Arrays.toString(arr1));
        System.out.println(Arrays.toString(arr2));
        StringBuilder str = new StringBuilder();
        Set<Integer> list1 = new HashSet<>();
        for(int i=0;i<5;i++){
            int num = getNum(arr1, 0, 35, list1);
            str.append(num).append(" ");
        }
        Set<Integer> list2 = new HashSet<>();
        for(int i=0;i<2;i++){
            int num = getNum(arr2, 0, 12, list2);
            str.append(num).append(" ");
        }
        System.out.println(str);

    }

    public static int getNum(int[] arr, int min, int max, Set<Integer> list){
        while (true) {
            int anInt = getInt(min, max);
            int num = arr[anInt];

            if(!list.contains(num)){
                list.add(num);
                return num;
            }
            System.out.println("重复num:" + num +" ,list:" + list);
        }
    }

    public static int getInt(int min, int max){
        int num = (int) (Math.random()*max + min);
        return num;
    }

}
