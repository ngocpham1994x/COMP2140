package com.company;

import java.util.*;

/*
Write a recursive method to compute the n-th Cado number, for n >= 0.
Then write an iterative method that uses a loop (instead of recursion) and an array (to store Cado numbers as you compute them)
to compute the n-th Cado number, for n >= 0.
The idea:  put C(i) into position i of the array, from 0 to n.
Finally,  write a main method that calls each of the above methods to find C(100).
The main method should report how much time each of the two methods take to compute C(100).
 */

public class Main {

    public static void main(String[] args) {
	// write your code here
        System.out.println("Enter your 'n' value: ");
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int result;

        long startTime, endTime, elapsedTime;

        //Recursive call:
        startTime = System.nanoTime();
        result = cadoR(n);
        endTime = System.nanoTime();
        elapsedTime = endTime - startTime;
        System.out.println("Recursive Cado result: " + result);
        System.out.println("Elapsed time: " + elapsedTime);

        //Non-recursive call:
        startTime = System.nanoTime();
        result = cado(n);
        endTime = System.nanoTime();
        elapsedTime = endTime - startTime;
        System.out.println("Non-recursive Cado result: " + result);
        System.out.println("Elapsed time: " + elapsedTime);
    }

    //Non-recursive method:
    public static int cado(int n){
        int result;
        ArrayList<Integer> cadoList = new ArrayList();

        for (int i=0; i<=n ; i++){
            if(i < 3)
                cadoList.add(i,1);
            else {
                result = cadoList.get(cadoList.get(i - 1)) + cadoList.get(i - cadoList.get(i - 1));
                cadoList.add(i,result);
            }
        }
        return cadoList.get(n);
    }

    //Recursive method:
    public static int cadoR(int n){
        if (n < 3)
            return 1;
        return cadoR(cadoR(n-1)) + cadoR(n- cadoR(n-1));
    }

}
