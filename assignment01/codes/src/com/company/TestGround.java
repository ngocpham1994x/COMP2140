package com.company;

import java.util.*;

public class TestGround {
    public static void main(String[] args) {
//        ArrayList<ArrayList> buckets = new ArrayList();
//        buckets.add(new ArrayList());
//        buckets.get(0).add("test again");
//        buckets.get(0).add(56);
//
//        buckets.add(new ArrayList<Integer>());
//        buckets.get(1).add(0, 50);
//        buckets.get(1).add(1, 88);

//        System.out.println(buckets);
//
//        ArrayList<ArrayList> list = new ArrayList();
//        list.add(new ArrayList<String>());
//        list.get(0).add(0,"test");
//
//        System.out.println(list);

        int digitValue = getDigit(156473, 10, 6);
        System.out.println(digitValue);


    } // end main()

    public static int getDigit(int num, int digitOrder, int totalDigit){
        int digitValue = 0;

        for(int i = totalDigit; i >= digitOrder; i--){
            digitValue =  (num / digitOrder);
            num =  (num%digitOrder);
        }
        
        return digitValue;
    }


}
