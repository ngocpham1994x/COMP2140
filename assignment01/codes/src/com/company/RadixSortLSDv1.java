package com.company;

import java.util.*;

public class RadixSortLSDv1 {

    public static void main(String[] args) {
        //radix sort using regular 2D-array as buckets (to be modified)
        //Note: below radixSort() only uses for POSITIVE integers

        int[] array = new int[]{7, 3, 2, 1, 0, 45,18,5,100,3,1,19,6,0,7,4,2,170, 45, 123456, 75, 90, 802, 24, 2, 66};
        radixSort(array);
        System.out.println(Arrays.toString(array));

    } // end main()

    public static void radixSort(int[] array){

        int max = getMax(array);
        int totalDigit = countDigit(max);


        for(int digitOrder = 1; digitOrder <= totalDigit; digitOrder++){
            int[][] buckets = new int[10][0];
            for(int item = 0; item < array.length; item++){
                int currNum = array[item];
                int currDigit = getDigit(currNum,digitOrder,totalDigit);
                buckets[currDigit] = placeIntoBucket(buckets[currDigit],currNum);
            }

            //overwrite original array with values in buckets, following bucket order 0 to 9
            int arrayItem = 0;
            for(int bucketNum = 0; bucketNum < 10; bucketNum++){
                if(buckets[bucketNum].length != 0){
                    for(int bucketItem = 0; bucketItem < buckets[bucketNum].length; bucketItem++)
                        array[arrayItem++] = buckets[bucketNum][bucketItem];
                }
            }

        }// end digitOrder

    }// end radixSort


    //placing the number into the corresponding bucket
    //in other words, adding new num to the bucket's array (bucket's list) by copying old bucket's array and add new num
    //this method changes the size of the bucket (replace its array with new array)
    private static int[] placeIntoBucket(int[] bucket, int currNum) {
        int[] bucketList = new int[bucket.length + 1];

        System.arraycopy(bucket, 0, bucketList, 0, bucket.length);
        bucketList[bucketList.length - 1] = currNum;

        return bucketList;
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



} //end RadixSortLSDv1
