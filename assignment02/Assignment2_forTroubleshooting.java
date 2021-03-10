package com.company;

import java.util.Arrays;
import java.util.Random;

/**
 * COMP 2140   SECTION A01
 * INSTRUCTOR    Cuneyt Akcora
 * Assignment           2
 * @author       your name, your student number
 * @version      date of completion
 *
 * PURPOSE: To implement Link list merge, fix and conversion methods.
 */

public class Assignment2
{

  public static void main(String[] args )
  {

    //Send input parameters to your method. In run/edit configurations, use program arguments and enter "1500 1300"
    int list1Size = Integer.parseInt(args[0]);//1500;
    int list2Size = Integer.parseInt(args[1]);//1300;

    Random r1 = new Random();
    LinkedList list1 = new LinkedList();
    for(int i = 0; i< list1Size; i++){
      int randomInt = r1.nextInt(list1Size);
      list1.insertValue(randomInt);
    }
    LinkedList list2 = new LinkedList();
    for(int i = 0; i< list2Size; i++){
      int randomInt = r1.nextInt(list2Size);
      list2.insertValue(randomInt);
    }
    list1.quickSort();
    list2.quickSort();
    String l1Test1 =  list1.printListCircular();
    System.out.println(l1Test1 + "original circular");
    String l2Test1 =  list2.printListCircular();
    System.out.println(l2Test1 + "original circular");


//    System.out.println( "\nCOMP 2140 Assignment 2 linked list questions" );
//    System.out.println( "\nCurrently both List1 and List2 are circular." );
//    System.out.println( "\nWe insert nodes or values to the circular version only." );
//    System.out.println( "\nOtherwise we would need to rewrite the insert function." );
    list1.add(list2);
    int size = list1Size + list2Size;
    String l1Add = list1.printListCircular();
    System.out.println(l1Add + "addition circular");
    System.out.println("List1 is expanded: "+(list1.getSize()== size));
    System.out.println("List1 is in order: "+ list1.isSorted());
    System.out.println("List2 is in order: "+ list2.isSorted());
    System.out.println("Type of List1 is Circular:"+list1.isCircular());
    System.out.println();


    list1.convertCircularToOrdinary();
    System.out.println("Type of List1 is Ordinary:"+list1.isOrdinary());
    String l1Test2 =  list1.printListOrdinary();
    System.out.println(l1Test2 + "before delete odd, ordinary");
    list1.deleteOddNodes();
    System.out.println("Odd nodes are deleted:"+list1.getSize()+" nodes remain");
    String l1After = list1.printListOrdinary();
    System.out.println(l1After + "after delete odd, ordinary");
    System.out.println();


    list1.convertOrdinaryToCircular();
    String l1Test3 = list1.printListCircular();
    System.out.println(l1Test3 + "converted to circular");
    System.out.println("Type of List1 is Circular:"+list1.isCircular());
    list1.convertCircularToOrdinary();//again, in order to add dummies.
    String l1Test4 = list1.printListOrdinary();
    System.out.println(l1Test4 + "converted to ordinary for dummies addition");
    list1.addDummies();
    System.out.println("List1 has dummies:"+list1.hasDummies());
    String l1After1 = list1.printListOrdinary();
    System.out.println(l1After1 + "after adding dummies, ordinary");
    System.out.println();



    list1.reset();//back to a circular list
    int[] arr = new int[size];

    //testInsertion below is overloaded. It has multiple
    // versions each with a different signature.
    //In your IDE, if you click on the function name while holding the CTRL key
    //the cursor will move to the function declaration
    long secondsList = testInsertion(size, list1);
    String l1Test5 = list1.printListCircular();
    System.out.println(l1Test5 + "test cicular");
    long secondsArray = testInsertion(size, arr);
    System.out.println(Arrays.toString(arr));
    System.out.println("test array" + "test array");
    System.out.println("Insertions take "+secondsArray+" (array) "+(secondsList)+"(list) nsecs");
    System.out.println();



    list1.reset();
    long t1 = System.nanoTime();
    int runs = 20; //1000000
    for(int i = 0; i< runs; i++){
      insertValue(list1,i);
    }
    t1 = System.nanoTime()-t1;
    String l1Test6 = list1.printListCircular();
    System.out.println(l1Test6 + "test circular");
    System.out.println("Linked list new values take "+(t1)+" nsecs");
    arr = new int[size];


    //insertvalue is overloaded.
    t1= System.nanoTime();
    for(int i = 0; i< runs; i++){
      arr= insertValue(arr,i,i);
    }
    t1 = System.nanoTime()-t1;
    System.out.println(Arrays.toString(arr) + "test array");
    System.out.println("Array new values take "+(t1)+" nsecs");
    System.out.println();




    //corrupt the linked list at one position
    int index = r1.nextInt(list1.getSize()/2);// corrupted point is in the first half of the list1
    list1.convertCircularToOrdinary();//ordinary linked list, with no duplicates (even the values are in sorted order).
    String l1Test7 = list1.printListOrdinary();
    System.out.println(l1Test7 + "before corrupted, ordered Ordinary");
    System.out.println("Type of List1 is Ordinary:"+list1.isOrdinary());
    System.out.println("List is originally corrupt: "+list1.findCorruption());
    int valueAtCorruption = list1.corrupt(index);

    String l1Test8 = list1.printListCircular();
    System.out.println(l1Test8 + "after corrupted, circular");
    boolean foundValue = list1.findCorruption();

    System.out.println("Corruption at index "+index+" (value:"+valueAtCorruption+") was found: "+(foundValue));
    System.out.println( "Program ends." );

  } // end main



  /**
   * Insert new integers into the list (you can insert 1 every time) 'size' times, and return the time
   * it takes to complete these insertions.
   **/
  private static long testInsertion(int size, LinkedList list1) {
    long nsecs=0;
    //write your code here
    long startTime = System.nanoTime();
    for(int iteration = 0; iteration < size; iteration++ )
      list1.insertValue(1);
    long endTime = System.nanoTime();

    nsecs = endTime - startTime;
    return nsecs;
  }

  /**
   * Insert new integers into the array (you can insert 1 every time) 'size' times, and return the time
   * it takes to complete these insertions.
   **/
  private static long testInsertion(int size, int[] arr) {
    long nsecs=0;
    //write your code here
    long startTime = System.nanoTime();
    for(int iteration = 0; iteration < size; iteration++ )
      arr[iteration]=1;
    long endTime = System.nanoTime();

    nsecs = endTime - startTime;
    return nsecs;
  }
  /**
   * insertValue: insert the given value into the array. If the array size is exceeded,
   * you must create a new array (1.5 size of the current array, if arr size is 100,
   * create a new arr for 150 values) and copy the old array into the new array.
   * Afterwards, insert the new item value to the new array
     **/
  private static int[] insertValue(int[]arr, int i, int index) {
    //write your code here
    //everytime you create a new array, printout a log with arr.size and newarray.size
    if(index < arr.length) {
      arr[index] = i;
    }
    else {
      int[] newArray = new int[(int) (arr.length*1.5)];
      System.arraycopy(arr,0,newArray,0,arr.length);
      System.out.println("arr.size = " +arr.length+ ", newArray.size = " +newArray.length);
      newArray[index] = i;
      arr = newArray; //switch arr pointer to newArray pointer on the heap
    }

    return arr;
  }
  /**
   * insertValue: insert the given value into the link list. We could use the list.insert()
   * but the goal of this exercise is to show the dynamic nature of lists.
     **/
  private static void insertValue(LinkedList list1, int i) {
    list1.insertValue(i);
  }

} // end class Assignment2

