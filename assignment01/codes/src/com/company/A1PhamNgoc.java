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

    //Question 10:

    //a main method to call the above testing method (Question 8) for a given array size and number of swaps,
    // which in turn calls the sorting algorithms, and the methods that fill the array and verify that it is sorted.
    public static void main(String[] args) {
        int arraySize = 10000;
        int numSwaps = (int) (0.25 * arraySize);

        System.out.println("Array size: " + arraySize + "\nNumber of swaps: " + numSwaps + "\n");
        testing(arraySize, numSwaps);

        System.out.println("\nEnd of processing");
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
    private static void swap(int[] arr, int a, int b){
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    //The private non-recursive median-of-three method: It chooses a pivot from the items in positions start to end-1 (inclusive) in the array using the median-of-three method,
    // and swaps the chosen pivot into position start in the array. Consider the case when the array does not have three values to compute the pivot.
    private static int medianOfThree(int[] array, int start, int end){
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
    private static int partition(int[] array, int start, int end, int pivotIndex){
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
    } //end radixSortRecursive


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

    // the method takes two arguments: array size, and number of swapping
    // once the testing() method is called, it creates an array fills with index num.
    // It in turn calls and executes each of the sorting algorithm 100 times on this array.
    // every iteration, array's items are randomized swapping to ensure that we are not sorting a sorted array.
    // verify that the sorting method works each time — print a useful error message (including the name of the sorting algorithm that sorted the array) if the array is not sorted.
    // Elapsed time (in nanoseconds) is reported for each iteration of each algorithm, and is stored in Timings array of its specific algorithm it uses to sort.
    // Average/mean of algorithm's 100 times run-time is then calculated from given arithmeticMean() method
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
            randomize(array, numSwaps);
            startTime = System.nanoTime();
            mergeSort(array);
            endTime = System.nanoTime();
            if(isSorted(array)){
                elapsedTime = endTime - startTime;
                mergeSortTimings[i] = (long) (elapsedTime * Math.pow(10,-6));
            }
            else
                System.out.println("Array is not successfully sorted");


        }
        mergeSortMean = arithmeticMean( mergeSortTimings );
        System.out.println(mergeSortMean + " milliseconds");
        System.out.println("/***********************************************************/");


        System.out.println("Merge sort Inefficient algorithm");
        for( int i = 0; i < 100; i++ ){
            randomize(array, numSwaps);
            startTime = System.nanoTime();
            mergeSortInefficient(array);
            endTime = System.nanoTime();
            if(isSorted(array)) {
                elapsedTime = endTime - startTime;
                mergeSortInefficientTimings[i] = (long) (elapsedTime * Math.pow(10,-6));
            }
            else
                System.out.println("Array is not successfully sorted");
        }
        mergeSortInefficientMean = arithmeticMean( mergeSortInefficientTimings );
        System.out.println(mergeSortInefficientMean + " milliseconds");
        System.out.println("/***********************************************************/");


        System.out.println("Quick sort algorithm");
        for( int i = 0; i < 100; i++ ){
            randomize(array, numSwaps);
            startTime = System.nanoTime();
            quickSort(array);
            endTime = System.nanoTime();
            if(isSorted(array)) {
                elapsedTime = endTime - startTime;
                quickSortTimings[i] = (long) (elapsedTime * Math.pow(10,-6));
            }
            else
                System.out.println("Array is not successfully sorted");
        }
        quickSortMean = arithmeticMean( quickSortTimings );
        System.out.println(quickSortMean + " milliseconds");
        System.out.println("/***********************************************************/");


        System.out.println("Radix sort (MSD) algorithm");
        for( int i = 0; i < 100; i++ ) {
            randomize(array, numSwaps);
            startTime = System.nanoTime();
            radixSort(array);
            endTime = System.nanoTime();
            if (isSorted(array)) {
                elapsedTime = endTime - startTime;
                radixSortTimings[i] = (long) (elapsedTime * Math.pow(10,-6));
            } else
                System.out.println("Array is not successfully sorted");
        }
        radixSortMean = arithmeticMean( radixSortTimings );
        System.out.println(radixSortMean + " milliseconds");
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



    //Question 10: see main() at the top of page (line 19)

}



/**REPORT**:
1. In the main function, increase array size from arraySize= 5K, . . . , 60K by 5K at every step
and record sorting times (keep the randomization at 25%, i.e., numSwaps = 0.25×arraySize) for each algorithm.
In your report, create a table (in text, columns separated by the | character ) of times versus array size for each algorithm.
The columns of the table will be 1) alg name, 2) arraySize, 3) time (ms).

Algorithm           |    arraySize  |   time (ms)
mergeSort           |       5K      |       0.34
mergeSortInefficient|               |       0.58
quickSort           |               |       0.1
radixSort           |               |       8.13
------------------------------------------------------
mergeSort           |       10K     |       0.27
mergeSortInefficient|               |       2.17
quickSort           |               |       0.17
radixSort           |               |       13.01
------------------------------------------------------
mergeSort           |       15K     |       1.11
mergeSortInefficient|               |       3.32
quickSort           |               |       0.87
radixSort           |               |       63.35
------------------------------------------------------
mergeSort           |       20K     |       1.39
mergeSortInefficient|               |       4.49
quickSort           |               |       1.65
radixSort           |               |       89.57
------------------------------------------------------
mergeSort           |       25K     |       2.45
mergeSortInefficient|               |       5.63
quickSort           |               |       1.55
radixSort           |               |       104.01
------------------------------------------------------
mergeSort           |       30K     |       3.14
mergeSortInefficient|               |       6.29
quickSort           |               |       2.21
radixSort           |               |       131.48
------------------------------------------------------
mergeSort           |       35K     |       3.81
mergeSortInefficient|               |       6.99
quickSort           |               |       2.42
radixSort           |               |       141.1
------------------------------------------------------
mergeSort           |       40K     |       4.55
mergeSortInefficient|               |       8.42
quickSort           |               |       2.51
radixSort           |               |       169.57
------------------------------------------------------
mergeSort           |       45K     |       4.74
mergeSortInefficient|               |       9.57
quickSort           |               |       2.59
radixSort           |               |       170.94
------------------------------------------------------
mergeSort           |       50K     |       5.45
mergeSortInefficient|               |       10.04
quickSort           |               |       4.11
radixSort           |               |       204.78
------------------------------------------------------
mergeSort           |       55K     |       7.16
mergeSortInefficient|               |       11.01
quickSort           |               |       3.74
radixSort           |               |       217.27
------------------------------------------------------
mergeSort           |       60K     |       7.4
mergeSortInefficient|               |       12.46
quickSort           |               |       4.29
radixSort           |               |       244.15


2.Was merge sort (the efficient version) faster than quick sort? Why or why not? Answer the question for arraySize= 10000, number of swaps=2500.
Array size: 10000
Number of swaps: 2500

Merge sort algorithm
0.27 milliseconds
Quick sort algorithm
0.17 milliseconds

No. In general, for given setup, mergeSort (efficient) is slower than quickSort.
Because: mergeSort has time complexity of O(nlogn), and quickSort - if choosing good pivot - has average time complexity of O(nlogn) as well.
However, quickSort:
-linearly scans the input and linearly partitions the input
-is sensitive to input that happens to be in the right order (pick median as pivot, whereas mergeSort just take the middle index to partition), in which case it can skip some swaps
-make more recursive calls, but allocating stack space is cheap


3.What is the cost of using the inefficient merge sort vs the merge sort? Answer the question for arraySize= 10000, number of swaps=2500.
merSort is faster than mergeSortInefficient.

Array size: 10000
Number of swaps: 2500

Merge sort algorithm
0.27 milliseconds
Merge sort Inefficient algorithm
2.17 milliseconds


4.Was radix sort faster than merge sort (the efficient version)? Answer the question for arraySize= 10000, number of swaps=2500.
No. radixSort is slower than mergeSort (efficient).

Array size: 10000
Number of swaps: 2500

Merge sort algorithm
0.27 milliseconds
Radix sort (MSD) algorithm
13.01 milliseconds


5.Compared to comparison based sort functions, what are the operations that slow down radix sort?
Radix sort never directly compares one whole item to another whole item.
Radix sort looks at the same piece of all items, then at another piece in all items, and so on, until it has seen all of pieces of items.
=> more recursive calls than other comparison-based sorting algorithms.


6.Optional, not graded: Try to fit a curve to the sort times you will record in item number 1. Report the curve values for algorithms.
 **/