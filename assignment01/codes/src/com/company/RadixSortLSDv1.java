package com.company;

import java.util.*;

public class RadixSortLSDv1 {

    public static void main(String[] args) {
        //radix sort using regular 2D-array as buckets (to be modified)

        int[] array = new int[]{170, 45, 123456, 75, 90, 802, 24, 2, 66};
        radixSort(array);
        System.out.println(Arrays.toString(array));

    } // end main()

    public static void radixSort(int[] array){

        int max = getMax(array);
        int totalDigit = countDigit(max);
        ArrayList outputArray = new ArrayList();
        outputArray = copyArrayToArrayList(array,outputArray);

        ArrayList<ArrayList> buckets = new ArrayList();
        for(int i=0; i < 10; i++)
            buckets.add(new ArrayList());

        for(int i = 1; i <= totalDigit; i++){  //placement: 10th, 100th
            for(int j = 0; j < outputArray.size(); j++){ // iterate through each item in array
                int currNum = (int) outputArray.get(j);
                int currDigit = getDigit(currNum,i,totalDigit);
                buckets.get(currDigit).add(currNum);
            }
            outputArray = flatten(buckets);
            for(int k = 0; k < buckets.size(); k++){
                buckets.get(k).clear();
            }
        }

        //copy back to original array
        for(int i=0; i < outputArray.size(); i++)
            array[i] = (int) outputArray.get(i);

    }

    public static int getMax( int[] array){
        int max = array[0];
        for(int i = 1; i < array.length; i++)
            max = Math.max(array[i], max);
        return max;
    }

    public static int countDigit( int num){
        if (num == 0)
            return 0;
        return 1 + countDigit(num / 10);
    }

    public static int getDigit(int num, int digitOrder, int totalDigit){
        int digitValue = 0;

        for(int i = totalDigit; i >= digitOrder; i--){
            digitValue = (int) (num / Math.pow(10,i-1));
            num = (int) (num%(Math.pow(10,i-1)));
        }

        return digitValue;
    }

    public static ArrayList flatten(ArrayList array) {
        ArrayList flatArray = new ArrayList();


        for (int i = 0; i < array.size(); i++) {
            Object element = array.get(i);
            if (element instanceof ArrayList)
                flatArray.addAll(flatten((ArrayList)(element)));    //recursion of flatten()
            else
                flatArray.add(element);
        }

        return flatArray;
    }


    public static ArrayList copyArrayToArrayList(int[] array, ArrayList arrayList){
        ArrayList returnArrayList = new ArrayList();

        for(int i=0; i < array.length; i++)
            returnArrayList.add(array[i]);

        return returnArrayList;
    }

} //end RadixSortLSDv1
