package com.company;

import java.util.*;

public class QuickSort {
    private int capacity;  //the capacity of the array (aka arr.length), unchangeable
    private int size;  //current number of elements in the array, changeable
    private int[] theArray;
    private final int max = 100;

    //constructor
    public QuickSort(){
        this.size = 0;
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Enter array capacity: ");
        this.capacity = keyboard.nextInt();
        theArray = new int[capacity];
    }

    public void addToArray(){
        theArray[size] = (int) (Math.random()*max+1);
        size++;
    }


    public int getSize(){
        return this.size;
    }

    public int getCapacity(){
        return this.capacity;
    }

    public void clear(){
        for(int i = 0; i < capacity; i++)
            theArray[i] = 0;
        size = 0;
    }

    public String toString(){
        String s;
        s = Arrays.toString(theArray);
        return s;
    }

    public static void swap(int[] arr, int a, int b){
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    public int medianOfThree(int start, int end){
        int theMiddle,theLeft,theRight, thePivot, pivotIndex;
        if(0 <= start && start < end  && end <= capacity){
            theLeft = theArray[start];
            theRight = theArray[end-1];
            theMiddle = theArray[(start+end)/2];

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

            return pivotIndex;
        }

        else
            return -1;
    }


    public int partition(int start, int end, int pivotIndex){
        swap(theArray, start, pivotIndex);

        int bigStart = start + 1;
        int pivot = theArray[start];

        for(int curr = start+1; curr < end; curr++){
            if(theArray[curr] < pivot){
                swap(theArray, bigStart, curr);
                bigStart++;
            }
            //else do nothing if belongs in the big group
        }

        swap(theArray, start, bigStart-1);

        return bigStart-1;
    }

    public void quickSort(){
        quickSort(theArray, 0, capacity);
    }

    private void quickSort(int[] array, int start, int end){
        int pivotPosition;
        if (2 == (end-start) ) {  //2 items in the list
            if (array[start+1] < array[start])
                swap(array, start, start+1);
        }
        else if (2 < (end-start) ) {   //more than 2 items in the list
            pivotPosition = medianOfThree(start, end);
            pivotPosition = partition(start, end, pivotPosition);

            quickSort(array,start,pivotPosition);
            quickSort(array,pivotPosition+1,end);
        }

        //Base case = 0 or 1 item in the array, do nothing

    }


    public static void main(String[] args) {
        // write your code here
        QuickSort arr = new QuickSort();

        System.out.println("Testing array setups. Adding items to array: ");
        arr.addToArray();
        arr.addToArray();
        arr.addToArray();
        arr.addToArray();
        arr.addToArray();


        System.out.println("Capacity: " + arr.getCapacity());
        System.out.println("Current size: " + arr.getSize());
        System.out.println(arr.toString());
        arr.clear();
        System.out.println("Clear array");
        System.out.println(arr.toString());


        System.out.println("\n \nNew array");
        for(int i = 0; i < arr.getCapacity(); i++){
            arr.addToArray();
        }
        System.out.println("Capacity: " + arr.getCapacity());
        System.out.println("Current size: " + arr.getSize());
        System.out.println(arr.toString());


        System.out.println("Sorted array");
        arr.quickSort();
        System.out.println(arr.toString());


    }

}//end QuickSort class
