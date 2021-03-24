// COMP 2140 SECTION A01
// Assignment 3
// @author Ngoc Pham, 7891063
// @version March 23, 2021
//
// PURPOSE: This file shows an example of how to use the class MyQueue.
//

package com.company;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class A3PhamNgoc {

	public static void main(String [] args) {



		MyQueue<Integer> q = new MyQueue<Integer>();

		int testSize = 5;

		for (int i = 0 ; i < testSize ; i++) {
			q.enqueue(new Integer(i));
			System.out.println("enqueued " + i);
		}

		for (int i = 0 ; i < testSize/2 ; i++) {
			System.out.println("dequeued " + q.dequeue());
		}

		for (int i = testSize ; i < 2*testSize ; i++) {
			q.enqueue(new Integer(i));
			System.out.println("enqueued " + i);
		}

		while(!q.isEmpty()) {
			System.out.println("dequeued " + q.dequeue());
		}

		//CORE JAVA: Collections
		//2 - ArrayLists
		List<String> store1 = new ArrayList<>();
		store1.add("https://www.amazon.ca/Coders-Work-Reflections-Craft-Programming/dp/1430219483");// interviews of famous coders
		store1.add("https://www.amazon.ca/Code-Complete-2nd-Steve-McConnell/dp/0735619670/"); // thick book about all you need to know about ADT
		store1.add("https://www.amazon.ca/Mythical-Man-Month-Software-Engineering-Anniversary/dp/0201835959");//Software development bible
		store1.add("https://www.amazon.ca/Clean-Code-Handbook-Software-Craftsmanship/dp/0132350882");//Advanced software practises
		store1.add("https://www.amazon.ca/Computer-Programming-Volumes-1-4A-Boxed/dp/0321751043/");//bible of computer science.
		// Solving the questions of the first volume will get you a job in any country, any company.

		//2a- search the ArrayList class to find the function that can be used to get the element at a given index.
		int index =2;
		String book = store1.get(index);// write a single line of code here to find the book at the index;
		System.out.println("Book is retrieved: "+(book.contains("Mythical")));
		//2b- search the ArrayList class to find a way (more than 1 way exist) to add all books in store1 to store2 with a single line of code.
		List store2 = new ArrayList<String>();
		store2.addAll(store1);
		System.out.println("Books are copied:"+(!store2.isEmpty()&&store2.size()==store1.size()));

		List store3 = new LinkedList<String>();
		LinkedList<String> store4 = new LinkedList<String>();

		//related to 2d
		String book2 = store4.peek();//possible
		// but this is not:
		//store3.peek();

		//related to 2e
		//we can change store1 to linkedlist
		store1 = new LinkedList<>();
		//but we cannot change store 4 to an arraylist
		//will not compile: store4 = new ArrayList<String>();
	}
}

/**REPORT:
 2c: write one sentence about the difference between linkedlist and arraylist

	arrayList has a regular array inside, elements are stored contiguous, if array is not big enough, larger array is created
 	to replace the old one; whereas linkedList stores its item using pointers, elements are not stored in contiguous locations,
 	and every node is a separate object with data part and address part.

 2d: write one sentence about the difference between store3 and store4.
 What are the methods that can be called on store4, but not on store3?

 	store3 is a "List" type, which implements an interface; whereas store4 is a "LinkedList" type, which implements a class.

 	Interface has abstract methods which are empty, only contain method signature & no body. It's a blueprint of the class.
 	Class has methods with body of codes. If a class implements an interface, its methods will have same interface's method signature,
	only the body codes are non-empty & specific.
 	So then store3 is an interface, which its methods have no body, therefore, any method in store3 should not be called,
 	because it will not be executed anything. User should not instantiate objects from an abstract structure.
 	store4 is a specific class implemented List interface, any method in store4 can be called because there are codes in methods' bodies.

 	Methods can be called on store4, but not on store3: peek(), addFirst(), addLast(), removeFirst(), removeLast(), getFirst(), getLast(), etc.

 2e: write two sentences about the difference between store3 and store4.
 What is the advantage of using the LinkedList declaration in store4? in turn, what is the disadvantage?

	store3 is an interface, its methods are all abstract & empty, store3 has methods' signatures only - with empty bodies;
 	store4 is a linkedList class, its methods are filled with specific codes to execute specific tasks.
 	store3 is a blueprint for store4.

 	linkedList store4, advantage:
 		ease of add and remove items from the beginning, middle or end of the list.
 		data has dynamic size.
 	disadvantage:
 		unlike arrayList, linkedList cannot access random items (has to iterate through the list from top node)
 		besides data, additional memory space to store next node pointer on each element of the list.

 **/