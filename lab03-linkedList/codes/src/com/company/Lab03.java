package com.company;

import java.util.Random;

/**
 * Lab03
 * <p>
 * COMP 2140   SECTION A01
 * INSTRUCTOR    Cuneyt Akcora
 * Lab           3
 *
 * @author your name, your student number
 * @version date of completion
 * <p>
 * PURPOSE: To implement sets as linked lists (with no dummy nodes),
 * providing add, isMember, union and intersection.
 */

public class Lab03 {
    public static void main(String[] args) {

        System.out.println("\n\nCOMP 2140 Lab 03 Sets implemented as linked lists\n");

        doTests();

        System.out.println("\n\nProgram ends.\n");
    } // end main

    /*************************************************************************
     *
     * doTests
     *
     * Create pairs of sets and test the union and intersection algorithms.
     *
     **************************************************************************/
    private static void doTests() {
        Set setA, setB, setUnion, setIntersection;
        Random generator = new Random(System.nanoTime());
        int randValue;

        // First, small tests.

        System.out.println("First, some small tests.\n");

        // Union and intersection when one set is empty

        System.out.println("(a) Union and intersection when one list is empty.");
        setA = new Set();
        setB = new Set();
        setA.add(9);
        System.out.println("    setA: " + setA);
        System.out.println("    setB: " + setB);
        setUnion = setA.union(setB);
        System.out.println("    setA.union( setB ): " + setUnion);
        setUnion = setB.union(setA);
        System.out.println("    setB.union( setA ): " + setUnion);
        setIntersection = setA.intersection(setB);
        System.out.println("    setA.intersection( setB ): " + setIntersection);
        setIntersection = setB.intersection(setA);
        System.out.println("    setB.intersection( setA ): " + setIntersection);

        // Insert the same values in both.

        System.out.println("(b) Union and intersection when the sets are identical.");
        setB.add(9);
        setA.add(1);
        setB.add(1);
        setA.add(13);
        setB.add(13);
        System.out.println("    setA: " + setA);
        System.out.println("    setB: " + setB);
        setUnion = setA.union(setB);
        setIntersection = setA.intersection(setB);
        System.out.println("    setA.union( setB ): " + setUnion);
        System.out.println("    setA.intersection( setB ): " + setIntersection);

        // Now work with sets that are entirely disjoint.

        System.out.println("(c) Union and intersection when the sets contain no items in common.");
        setB = new Set();
        setB.add(0);
        setB.add(4);
        setB.add(19);
        setB.add(33);
        setB.add(62);
        System.out.println("    setA: " + setA);
        System.out.println("    setB: " + setB);
        setUnion = setA.union(setB);
        System.out.println("    setA.union( setB ): " + setUnion);
        setUnion = setB.union(setA);
        System.out.println("    setB.union( setA ): " + setUnion);
        setIntersection = setA.intersection(setB);
        System.out.println("    setA.intersection( setB ): " + setIntersection);
        setIntersection = setB.intersection(setA);
        System.out.println("    setB.intersection( setA ): " + setIntersection);

        // Second, try two random sets (there will be very few elements in their intersection, maybe none)

        System.out.println("\n\nNow, a larger random test.");
        System.out.println("\n\n    Insert about 100 random numbers in each set.");

        setA = new Set();
        setB = new Set();
        for (int i = 0; i < 100; i++) {
            randValue = generator.nextInt(10000);
            setA.add(randValue);
            if (!setA.isMember(randValue))
                System.out.println("    ERROR: Added " + randValue + " to setA, but it\'s not there.");
            randValue = generator.nextInt(10000);
            setB.add(randValue);
            if (!setB.isMember(randValue))
                System.out.println("    ERROR: Added " + randValue + " to setB, but it\'s not there.");
        }
        System.out.println("\n    setA: " + setA);
        System.out.println("\n    setB: " + setB);
        setUnion = setA.union(setB);
        System.out.println("\n    setA.union( setB ): " + setUnion);
        setUnion = setB.union(setA);
        System.out.println("\n    setB.union( setA ): " + setUnion);
        setIntersection = setA.intersection(setB);
        System.out.println("\n    setA.intersection( setB ): " + setIntersection);
        setIntersection = setB.intersection(setA);
        System.out.println("\n    setB.intersection( setA ): " + setIntersection);

    } // end doTests

} // end class Lab03
