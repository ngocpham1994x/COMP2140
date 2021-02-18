/**
 * Name of class or program (matches filename)
 *
 * COMP 2140 SECTION A01
 * INSTRUCTOR Akcora
 * ASSIGNMENT Assignment # 1
 * @author NGOC PHAM (7891063)
 * @version February 19 2021
 *
 * PURPOSE: what is the purpose of your program?
 */

package com.company;

import java.util.*;

public class A1PhamNgoc {

    public static void main(String[] args) {
        int[] array = new int[10];
        fill(array);
        randomize(array,2);
        System.out.println(Arrays.toString(array));
    }



    //Question 1: A recursive merge sort algorithm

    //
    public static void mergeSort(int[] array ) {
        //check the validity of array (non-empty array to be sorted)
        if(array.length > 0) {
            int[] temp = new int[array.length];
            mergeSort(array, 0, array.length, temp);
        }
        //else, do nothing
    }

    // end = length of the array
    // merge sort items a[start] to (& including) a[end-1]
    private static void mergeSort( int[] array, int start, int end, int[] temp) {
        int mid;

        // Recursive case: if > 1 item
        if ((end - start) > 1) {
            mid = start + (end-start)/2;
            mergeSort( array, start, mid, temp );
            mergeSort( array, mid, end, temp );
            merge( array, start, mid, end, temp );
        }
        // Base case: if 0 < items < 2, (item = 1), do nothing
    }

    private static void merge( int[] array, int start, int mid, int end, int [] temp ) {
        int currL = start; // index of current item in left half
        int currR = mid; // index of current item in right half
        int currT; // index in temp

        for ( currT = start; currT < end; currT++ ) {
            if ( currL < mid && ( currR >= end || array[currL] < array[currR] ) ){
                // copy from left half if that value is smaller
                // or if no values remain in the right half
                temp[currT] = array[currL++];
            }
            else   // copy from the right half
                temp[currT] = array[currR++];
        } // end for


        // Copy the merged items into the original array.
        System.arraycopy(temp,start,array,start,end-start);

    } // end merge



    //Question 2: A recursive merge sort algorithm that does not use a shared temp array
    public static void mergeSortInefficient( int[] array ){
        //check the validity of array (non-empty array to be sorted)
        if(array.length > 0)
            mergeSortInefficient( array, 0, array.length );
    }

    private static void mergeSortInefficient( int[] array, int start, int end ) {
        int mid;

        //Recursive case:
        if ((end - start) > 1) {
            mid = start + (end-start)/2;
            mergeSortInefficient( array, start, mid);
            mergeSortInefficient( array, mid, end);
            merge( array, start, mid, end);
        }
        //Base case:
    }

    private static void merge( int[] array, int start, int mid, int end){
        int[] temp = new int[end-start+1];
        System.arraycopy(array, start, temp, 0, end-start+1);  //copying the values of unsorted to a temp array

        int currL = 0; //index that will move through the first half of temp
        int midOfTemp = (end-start)/2;  //mid here is the middle of the temp array
        int currR = midOfTemp + 1; //index that will move through the second half of temp
        int currIndexInArr = 0;  //keeps track of the number of positions in original array

        while (currL <= midOfTemp && currR < temp.length) {
            if (temp[currL] <= temp[currR])
                array[start + currIndexInArr++] = temp[currL++]; //Overwriting the original array
            else
                array[start + currIndexInArr++] = temp[currR++]; //Overwriting the original array
        }
        while (currL <= midOfTemp)
            array[start + currIndexInArr++] = temp[currL++];  // if some numbers are left in the first half, copy all of them
    }


    //Question 3: A recursive quick sort algorithm



    //Question 4: A radix sort algorithm that sorts a positive integer array by starting from the biggest digits of numbers



    //Question 5:A method that verifies that an array is in sorted order
    // It should be passed an array and it must check that each item is no bigger than the next item in the array.
    // It should return true if the array is in sorted order and false if it is not.
    public static boolean isSorted(int[] array){
        for(int i = 0; i < array.length-1; i++){
            if(array[i] > array[i+1])
                return false;
        }
        return true;
    }


    public static void swap(int[] arr, int a, int b){
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    //Question 6: A method to fill an array with values to sort
    // It should be passed a non-null int array "array".
    // It should fill the array with the numbers 0 to array.length-1, in order.
    public static void fill(int[] array){
        for(int i = 0; i < array.length; i++)
            array[i] = i;
    }

    //Question 7: A method to randomize an array that is filled with numbers
    // It should be passed an array array and a number n. It should perform n random swaps in the array.
    // To perform a random swap, randomly choose two positions in the array and then swap the contents of those two positions.
    public static void randomize(int[] array, int n){
        for(int i = 0; i < n; i++){
            int firstIndex, secondIndex;
            firstIndex = (int) (Math.random()*array.length);
            do
                secondIndex = (int) (Math.random()*array.length);
            while(firstIndex == secondIndex);
            swap(array, firstIndex, secondIndex);
        }
    }


    //Question 8: A testing method that allows you to compare (and to verify) these sorting methods using a simple statistic



    //Question 9: The method that computes the arithmetic mean of timings
    /*******************************************************************
     * arithmeticMean
     * Purpose: Compute the average of long values.
     *          To avoid long overflow, use type double in the computation.
     ******************************************************************/
    public static double arithmeticMean( long data[] ) {
        double sum = 0;
        for (int i = 0; i < data.length; i++)
            sum += (double)data[i];
        return sum / (double)data.length;
    } // end arithmeticMean


}



/*Report:
1.
2.
3.
4.
5.
6.
 */