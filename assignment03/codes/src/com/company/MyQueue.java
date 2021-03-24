// COMP 2140 SECTION A01
// Assignment 3
// @author Ngoc Pham, 7891063
// @version March 23, 2021
//
// PURPOSE: This file implements a queue using the generic type <E>.
//
package com.company;

public class MyQueue<E> {

	// INSTANCE VARIABLES
	//declarations for two protected instance variables stack1 and stack2 of type MyStack<E>
	protected MyStack<E> stack1;
	protected MyStack<E> stack2;

	// CONSTRUCTOR
	//initialize stack1 and stack2 by assigning them respective references to two new instances
	// of the class MyStack<E>, each of which is a new empty stack
	public MyQueue() {
		this.stack1 = new MyStack<>();
		this.stack2 = new MyStack<>();
	}

	// ACCESSOR METHODS
   
	// METHOD: isEmpty
	// PURPOSE: Return true if the queue is empty.
	//it sets the value of result to true if and only if the queue is empty
	public boolean isEmpty() {
		return stack1.isEmpty() && stack2.isEmpty();
	}
    
	// ACTION METHODS

	// METHOD: transferStacks
	// PURPOSE: Move the content from stack1 to stack2 such that its
	// order on stack2 is reverse from what it was on stack1.
	protected void transferStacks() {
		while(!stack1.isEmpty())
			stack2.push(stack1.pop());
	}
	
	// METHOD: enqueue
	// PURPOSE: Add elem to the front of the queue.
	public void enqueue(E elem) {
		stack1.push(elem);
	}

	// METHOD: dequeue
	// PURPOSE: Return the element at the front of the queue and remove
	// it from the queue.
	// a) check whether stack2 is empty and, if so, to call transferStacks, and
	// b) to pop the top item from stack2 and store it in result
	public E dequeue() throws EmptyStackQueueException {
		if(stack2.isEmpty())
			transferStacks();
		return stack2.pop();
	}
}


