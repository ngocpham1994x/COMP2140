package com.company;

import java.util.*;

public class RadixSortMSD {

    public static void main(String[] args) {
        //recursive radixSort MSD with 2D-array as buckets
        //Note: below radixSort() only uses for POSITIVE integers

        int[] array = new int[]{170, 45, 3456, 575, 90, 802, 1234, 24, 50000, 2, 66};
        radixSort(array);
        System.out.println(Arrays.toString(array));

    } // end main()

    public static void radixSort(int[] array) {

        int max = getMax(array);
        int totalDigit = countDigit(max);

        radixSortRecursive(array, totalDigit, totalDigit);
    }

    //Recursive Radix Sort, aka Most Significant Digit (MSD) Radix Sort:
    //Take the most significant digit of each key.
    //Sort the list of elements based on that digit, grouping elements with the same digit into one bucket.
    //Recursively sort each bucket, starting with the next digit to the right.
    //Concatenate the buckets together in order.

    private static int[] radixSortRecursive(int[] array, int digitOrder, int totalDigit) {
        //Base case:
        if (array.length == 0 || digitOrder < 1)
            return array;

        //Recursive case:
        int[][] buckets = new int[10][0];

        //first pass (most significant digit)
        for (int item = 0; item < array.length; item++) {
            int currNum = array[item];
            int digitValue = getDigit(currNum, digitOrder, totalDigit);
            int bucketNum = digitValue;
            buckets[bucketNum] = placeIntoBucket(buckets[bucketNum], currNum);
        }

        //recursive for each-bucket's array
        for (int bucketNum = 0; bucketNum < 10; bucketNum++) {
            buckets[bucketNum] = radixSortRecursive(buckets[bucketNum], digitOrder-1 , totalDigit);
        }

        int bucketNum = 0;
        int bucketItem = 0;
        int arrayIndex = 0;

        while (bucketNum < 10) {
            int bucketLength = buckets[bucketNum].length;
            while (bucketItem < bucketLength)
                array[arrayIndex++] = buckets[bucketNum][bucketItem++];
            bucketItem = 0;
            bucketNum++;
        }

        return array;
    }

    //placing the number into the corresponding bucket
    //in other words, adding new num to the bucket's array (bucket's list) by copying old bucket's array and add new num
    private static int[] placeIntoBucket(int[] bucket, int currNum) {
        int[] bucketList = new int[bucket.length + 1];

        System.arraycopy(bucket, 0, bucketList, 0, bucket.length);
        bucketList[bucketList.length - 1] = currNum;

        return bucketList;
    }

    //finding maximum number in an array
    //this method is needed prior to calculate total digits of max num in array -> Most Significant Digit
    private static int getMax( int[] array){
        int max = array[0];
        for(int i = 1; i < array.length; i++)
            max = Math.max(array[i], max);
        return max;
    }

    //this method is to calculate total digits of max num in array
    //this helps to find the Most Significant Digit (MSD)
    private static int countDigit( int num){
        if (num == 0)
            return 0;
        return 1 + countDigit(num / 10);
    }


    //this method is to extract the desired digit of a number
    //the desired digit is "digitOrder", which is counted from Least Significant Digit (right most, order = 1)
    private static int getDigit(int num, int digitOrder, int totalDigit){
        int digitValue = 0;

        for(int i = totalDigit; i >= digitOrder; i--){
            digitValue = (int) (num / Math.pow(10,i-1));
            num = (int) (num%(Math.pow(10,i-1)));
        }

        return digitValue;
    }


} //end RadixSortMSD

//Credits:
//http://www.cs.toronto.edu/~david/courses/csc148_f14/week11/radix_sort.html
//https://stackoverflow.com/questions/41323554/implementing-radix-sort-recursively-how-to-print-the-elements-at-the-end
//https://www.geeksforgeeks.org/program-count-digits-integer-3-different-methods/