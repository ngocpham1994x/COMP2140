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
        int arraySize = 3;
        int numSwaps = (int) (0.25 * arraySize);

        System.out.println("Array size: " + arraySize + "\nNumber of swaps: " + numSwaps);
        testing(arraySize, numSwaps);
    }



    //Question 1: A recursive merge sort algorithm

    //The public driver mergeSort method: It simply calls the private recursive method with the array and the extra parameters
    // (the start and end indices and the extra array temp) that the recursive method needs.
    // This method must check the validity (null? empty?) of the array to be sorted.
    public static void mergeSort(int[] array ) {
        //check the validity of array
        if(array.length == 0)
            System.out.println("The array is empty - invalid");
        else if(array == null)
            System.out.println("The array is null - invalid");
        else {   // the array is not null, length > 0
            int[] temp = new int[array.length];
            mergeSort(array, 0, array.length, temp);
        }
    }

    //The private recursive helper mergeSort method: It does the recursive merge sort.
    // It receives the array, indices start and end, and the temporary array as parameters.
    // Its task is to merge sort positions start to end-1 (inclusive) in the array — and it must not touch any other positions in the array.
    // This is the method that should have the new base case added to it.

    // end = length of the array
    // merge sort items a[start] to (& including) a[end-1]
    private static void mergeSort( int[] array, int start, int end, int[] temp) {
        int mid;

        // Recursive case: if array has > 1 item
        if ((end - start) > 1) {
            mid = start + (end-start)/2;
            mergeSort( array, start, mid, temp );
            mergeSort( array, mid, end, temp );
            merge( array, start, mid, end, temp );
        }
        // Base case: if 0 < items < 2, (item = 1), do nothing
    }

    //The non-recursive helper merge method: It merges two sorted sublists (defined by three indices start, mid, and end) into one sorted list, using the extra array temp.
    // Make sure that you use the indices to define the sublists in a way that is consistent with how sublists are defined by indices in all other parts of the code:
    // from some index up to, but not including, some other index.
    // Only the public driver method should create an array. All the other methods used by merge sort must use the arrays and positions (indices)
    // that they are passed in their parameters without creating any other arrays.
    private static void merge( int[] array, int start, int mid, int end, int [] temp ) {
        int currL = start; // index of current item in left half
        int currR = mid; // index of current item in right half
        int currT; // index in temp

        for ( currT = start; currT < end; currT++ ) {
            if ( currL < mid && ( currR >= end || array[currL] < array[currR] ) )
                temp[currT] = array[currL++];  // copy from left half if that value is smaller, or if no values remain in the right half
            else
                temp[currT] = array[currR++];  // copy from the right half
        } // end for

        // Copy the merged items into the original array.
        System.arraycopy(temp,start,array,start,end-start);

    } // end merge



    //Question 2: A recursive merge sort algorithm that does not use a shared temp array

    //The public driver mergeSortInefficient method: It simply calls the private recursive method with the array and the extra parameters (the start and end indices)
    // that the recursive method needs. This method must check the validity (null? empty?) of the array to be sorted.
    public static void mergeSortInefficient( int[] array ){
        //check the validity of array
        if(array.length == 0)
            System.out.println("The array is empty - invalid");
        else if(array == null)
            System.out.println("The array is null - invalid");
        else    // the array is not null, length > 0
            mergeSortInefficient( array, 0, array.length );
    }

    //The private recursive helper mergeSortInefficient method: It does the recursive merge sort.
    // It receives the array, indices start and end, but does not receive a temporary array as a parameter.
    // Calls will be in the form mergeSortInefficient(array,start,end).
    // Its task is to merge sort positions start to end-1 (inclusive) in the array — and it must not touch any other positions in the array.
    private static void mergeSortInefficient( int[] array, int start, int end ) {
        int mid;

        //Recursive case: if array has > 1 item
        if ((end - start) > 1) {
            mid = start + (end-start)/2;
            mergeSortInefficient( array, start, mid);
            mergeSortInefficient( array, mid, end);
            int[] combinedArray = merge( array, start, mid, end);
            System.arraycopy(combinedArray, 0, array, start, combinedArray.length);
        }
        //Base case: if 0 < items < 2, (item = 1), do nothing
    }

    //The non-recursive helper merge method: It merges two sorted sublists (defined by three indices start, mid, and end) into one sorted list and return a sorted array.
    // This function will explicitly return a merged sorted array. Make sure that you use the indices to define the sublists in a way that is consistent with
    // how sublists are defined by indices in all other parts of the code: from some index up to, but not including, some other index.
    private static int[] merge( int[] array, int start, int mid, int end) {
        int[] leftArray = new int[mid - start];
        int[] rightArray = new int[end - mid];
        System.arraycopy(array, start, leftArray, 0, mid - start);  //copying the values of unsorted to a "left" array
        System.arraycopy(array, mid, rightArray, 0, end - mid);  //copying the values of unsorted to a "right" array

        int[] combinedArray = new int[end - start];

        int currL = 0;  //index that will move through the first half
        int currR = 0;  //index that will move through the second half
        int currIndexInReturnArray = 0; //keeps track of the number of positions in return array, aka the combinedArray

        while (currL < leftArray.length && currR < rightArray.length) {
            if (leftArray[currL] <= rightArray[currR])
                combinedArray[currIndexInReturnArray++] = leftArray[currL++]; //Overwriting the return array
            else
                combinedArray[currIndexInReturnArray++] = rightArray[currR++]; //Overwriting the return array
        }
        while (currL < leftArray.length)
            combinedArray[currIndexInReturnArray++] = leftArray[currL++];  // If some numbers are left in the first half, copy all of them.

        while (currR < rightArray.length)
            combinedArray[currIndexInReturnArray++] = rightArray[currR++]; //If some numbers are left in right half, copy all of them


        return combinedArray;
    }


    //Question 3: A recursive quick sort algorithm

    //The public driver quickSort method: It simply calls the private recursive method, passing it the extra parameters (the start and end indices) the recursive method needs.
    public static void quickSort( int[] array) {
        quickSort(array, 0, array.length);
    }

    //The private recursive helper quickSort method: It is the recursive quick sort method, which receives the array, and indices start and end as parameters.
    // Its task is to quick sort positions start to end-1 (inclusive) in the array — and it must not touch any other positions in the array.
    // If there are just two items to be sorted, swap them if necessary
    private static void quickSort(int[] array, int start, int end){
        int pivotPosition;
        if (2 == (end-start) ) {  //2 items in the list
            if (array[start+1] < array[start])
                swap(array, start, start+1);
        }
        else if (2 < (end-start) ) {   //more than 2 items in the list
            pivotPosition = medianOfThree(array, start, end);
            pivotPosition = partition(array, start, end, pivotPosition);

            quickSort(array,start,pivotPosition);
            quickSort(array,pivotPosition+1,end);
        }

        //Base case = 0 or 1 item in the array, do nothing

    }

    //swap method: swapping values at two indices of an array
    public static void swap(int[] arr, int a, int b){
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    //The private non-recursive median-of-three method: It chooses a pivot from the items in positions start to end-1 (inclusive) in the array using the median-of-three method,
    // and swaps the chosen pivot into position start in the array. Consider the case when the array does not have three values to compute the pivot.
    public static int medianOfThree(int[] array, int start, int end){
        int theMiddle,theLeft,theRight, thePivot, pivotIndex = start;
        if(end - start >= 3){
            theLeft = array[start];
            theRight = array[end-1];
            theMiddle = array[(start+end)/2];

            //bubble sort the Median List
            int[] medianList = {theLeft,theMiddle,theRight};
            for(int i = 0; i < medianList.length; i++){
                for(int j = 0; j < medianList.length - i - 1;j++){
                    if(medianList[j] > medianList[j+1]){
                        int temp = medianList[j];
                        medianList[j] = medianList[j+1];
                        medianList[j+1] = temp;
                    }
                    //else: do nothing
                }
            }

            //finding pivot position
            thePivot = medianList[medianList.length/2];
            if(thePivot == theLeft)
                pivotIndex = start;
            else if (thePivot == theMiddle)
                pivotIndex = (start+end)/2;
            else
                pivotIndex = end-1;

        }
        return pivotIndex;


        //else: if array.length < 3 (only two items in array), do nothing. pivotIndex = start
    }


    //The private non-recursive partition method: It partitions the items in positions start to end-1 (inclusive) in the array using the chosen pivot
    // (which it assumes is already in position start), and returns the final position of the pivot after the partition is complete. It should use one simple for-loop.
    public static int partition(int[] array, int start, int end, int pivotIndex){
        swap(array, start, pivotIndex);

        int bigStart = start + 1;
        int pivot = array[start];

        for(int curr = start+1; curr < end; curr++){
            if(array[curr] < pivot){
                swap(array, bigStart, curr);
                bigStart++;
            }
            //else do nothing if belongs in the big group
        }

        swap(array, start, bigStart-1);

        return bigStart-1;
    }


    //Question 4: A radix sort algorithm that sorts a positive integer array by starting from the biggest digits of numbers
    //In the class example where the biggest value was in hundreds, radix sort used 1s first, then 10s and finally 100s to sort values.
    // In this assignment, radix sort should start from the biggest digit to sort the array. For example, if the biggest number is 3289, it should first consider 3 to assign the number to a bucket.


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
    public static void randomize(int[] array, int numSwaps){
        for(int i = 0; i < numSwaps; i++){
            int firstIndex, secondIndex;
            firstIndex = (int) (Math.random()*array.length);
            do
                secondIndex = (int) (Math.random()*array.length);
            while(firstIndex == secondIndex);
            swap(array, firstIndex, secondIndex);
        }
    }


    //Question 8: A testing method that allows you to compare (and to verify) these sorting methods using a simple statistic
    public static void testing(int arraySize, int numSwaps){
        long[] mergeSortTimings = new long[100];
        long[] mergeSortInefficientTimings = new long[100];
        long[] quickSortTimings = new long[100];
        long[] radixSortTimings = new long[100];

        int[] array = new int[arraySize];
        fill(array);

        long startTime, endTime, elapsedTime;
        double mergeSortMean, mergeSortInefficientMean, quickSortMean, radixSortMean;


        System.out.println("Merge sort algorithm");
        for( int i = 0; i < 100; i++ ){
//            System.out.println("Testing record #" + i);
            randomize(array, numSwaps);
//            System.out.println(Arrays.toString(array));
//            System.out.println("\nSorting...");
            startTime = System.nanoTime();
            mergeSort(array);
            endTime = System.nanoTime();
//            System.out.println(Arrays.toString(array));
            if(isSorted(array)){
                elapsedTime = endTime - startTime;
                mergeSortTimings[i] = elapsedTime;
            }
            else
                System.out.println("Array is not successfully sorted");


        }
        mergeSortMean = arithmeticMean( mergeSortTimings );
//        System.out.println(Arrays.toString(mergeSortTimings));
        System.out.println(mergeSortMean + " nanoseconds");
        System.out.println("/***********************************************************/");


        System.out.println("Merge sort Inefficient algorithm");
        for( int i = 0; i < 100; i++ ){
//            System.out.println("Testing record #" + i);
            randomize(array, numSwaps);
//            System.out.println(Arrays.toString(array));
//            System.out.println("\nSorting...");
            startTime = System.nanoTime();
            mergeSortInefficient(array);
            endTime = System.nanoTime();
//            System.out.println(Arrays.toString(array));
            if(isSorted(array)) {
                elapsedTime = endTime - startTime;
                mergeSortInefficientTimings[i] = elapsedTime;
            }
            else
                System.out.println("Array is not successfully sorted");
        }
        mergeSortInefficientMean = arithmeticMean( mergeSortInefficientTimings );
//        System.out.println(Arrays.toString(mergeSortInefficientTimings));
        System.out.println(mergeSortInefficientMean + " nanoseconds");
        System.out.println("/***********************************************************/");


        System.out.println("Quick sort algorithm");
        for( int i = 0; i < 100; i++ ){
//            System.out.println("Testing record #" + i);
            randomize(array, numSwaps);
//            System.out.println(Arrays.toString(array));
//            System.out.println("\nSorting...");
            startTime = System.nanoTime();
            quickSort(array);
            endTime = System.nanoTime();
//            System.out.println(Arrays.toString(array));
            if(isSorted(array)) {
                elapsedTime = endTime - startTime;
                quickSortTimings[i] = elapsedTime;
            }
            else
                System.out.println("Array is not successfully sorted");
        }
        quickSortMean = arithmeticMean( quickSortTimings );
//        System.out.println(Arrays.toString(quickSortTimings));
        System.out.println(quickSortMean + " nanoseconds");
        System.out.println("/***********************************************************/");



    }//end testing()




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
1. In the main function, increase array size from arraySize= 5K, . . . , 60K by 5K at every step
and record sorting times (keep the randomization at 25%, i.e., numSwaps = 0.25×arraySize) for each algorithm.
In your report, crate a table (in text, columns separated by the | character ) of times versus array size for each algorithm.
The columns of the table will be 1) alg name, 2) arraySize, 3) time (ms).



2.Was merge sort (the efficient version) faster than quick sort? Why or why not? Answer the question for arraySize= 10000, number of swaps=2500.


3.Was merge sort (the efficient version) faster than quick sort? Why or why not? Answer the question for arraySize= 10000, number of swaps=2500.


4.Was merge sort (the efficient version) faster than quick sort? Why or why not? Answer the question for arraySize= 10000, number of swaps=2500.


5.Compared to comparison based sort functions, what are the operations that slow down radix sort?


6.Optional, not graded: Try to fit a curve to the sort times you will record in item number 1. Report the curve values for algorithms.
 */