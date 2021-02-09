package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here
        QuickSort arr = new QuickSort();

        System.out.println("Testing array setups. Adding items to array: ");
        for(int i = 0; i < arr.getCapacity(); i++){
            arr.addToArray();
        }

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
        System.out.println(arr.toString());


        System.out.println("Sorted array");
        arr.quickSort();
        System.out.println(arr.toString());
        System.out.println("Test");

    }
}
