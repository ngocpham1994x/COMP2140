package com.company;

import java.util.*;

/*****************************************************************************
 *
 *  Set: a mathematical set of integers
 *
 *  Implementation:
 *  - A linked list
 *  - The list has a last pointer (pointing to the last node in the list),
 *    _and_ a top pointer (pointing to the first node in the list).
 *  - Singly-linked (i.e., each node has only a next pointer)
 *  - The list is ordered: the first node (top) contains the smallest element
 *    in the list, and the last node (last) contains the largest element.
 *
 ******************************************************************************/

class Set
{

    private class Node
    {
        public int item;
        public Node next;

        public Node( int i, Node n )
        {
            item = i;
            next = n;
        }

        // The usual accessors and mutators (in case you really want to use them).
        // These methods are unnecessary because you can directly access item and next
        // for any node that you have a pointer to without using accessors or mutators
        // anywhere in the LinkedList class (because item and next are declared public
        // in the private Node class inside the LinkedList class).

        public int getItem()
        {
            return item;
        }

        public Node getNext()
        {
            return next;
        }

        public void setNext( Node newNext )
        {
            next = newNext;
        }


    } // end class Node


    private Node top;  // a pointer to the first node in the linked list
    private Node last; // a pointer to the last node in the linked list

    // Creates an empty set
    public Set()
    {
        top = last = null;
    }

    public boolean isEmpty()
    {
        return top == null;
    }

    /*************************************************************************
     *
     * toString
     *
     * PURPOSE: Create string representing the current state of the set.
     *
     * METHOD: If the set contains no more than 20 elements, it simply catenates
     * all elements in the set into a string.
     * Otherwise, if the set contains more than 20 elements, it catenates
     * the first 10 and the last 10 elements (separated by "...") into a string.
     *
     **************************************************************************/
    public String toString()
    {
        String result = "";
        Node curr, ahead10;
        int count10;
        boolean moreThan20 = false;

        if ( top != null )
        {
            // Add the first 10 elements to the output String
            count10 = 0;
            curr = top;
            while ( curr != null && count10 < 10 )
            {
                result = result + curr.item + " ";
                curr = curr.next;
                count10++;
            }

            // Find the start of the last 10 elements
            // First, move ahead10 10 elements ahead of curr.
            ahead10 = curr;
            count10 = 0;
            while ( ahead10 != null && count10 < 10 )
            {
                ahead10 = ahead10.next;
                count10++;
            }
            if ( ahead10 != null )
                result = result + " . . . ";
            // Second, move ahead10 and curr in tandem until ahead10 is null.
            // (curr will then be pointing at the 10th-last element.)
            while ( ahead10 != null )
            {
                curr = curr.next;
                ahead10 = ahead10.next;
            }

            // Add the last 10 elements to the output String
            while ( curr != null )
            {
                result = result + curr.item + " ";
                curr = curr.next;
            }
        }
        return result;
    }


    /**************************************************************************
     *
     * add
     *
     * Purpose:  Insert newItem into correct position in the ordered list.
     *           If the list is empty, insert the new (and only) node --- the
     *           new node's next pointer must point at the new node.
     *           If the list is NOT empty, call a recursive method (in the
     *           Node class) on the first node in the list --- the recursive
     *           method does the insertion and tells us what node is the first
     *           node in the list so we can make sure that the last node points
     *           at the correct first node.
     *
     ***************************************************************************/
    public void add( int newItem )
    {
        Node prev, curr;

        prev = null;
        curr = top;
        while ( curr != null && curr.item < newItem )
        {
            prev = curr;
            curr = curr.next;
        }

        // Insert if:
        // (a) we went all the way to the end without finding a larger (or equal) item or
        // (b) we didn't go all the way to then end, but we didn't find a duplicate
        if ( curr == null || curr.item != newItem )
        {
            if ( prev != null ) // the new node isn't the new first node
            {
                prev.next = new Node( newItem, curr );
                if ( curr == null ) // the new node is the new _last_ node
                    last = prev.next;
            }
            else // the new node _is_ the new first node
            {
                top = new Node( newItem, top );
                if ( curr == null ) // the new node is also the new last node
                    last = top;
            }
        } // end if ( curr == null || curr.item != newItem )
    }


    /*************************************************************************
     *
     * isMember
     *
     * PURPOSE: Return true if key is in the set; return false otherwise.
     *          (ordered search)
     *
     **************************************************************************/
    public boolean isMember( int key )
    {
        boolean foundKey = false;
        Node curr;

        curr = top;
        while ( curr != null && curr.item < key )
        {
            curr = curr.next;
        }

        if ( curr != null && curr.item == key )
            foundKey = true;

        return foundKey;
    }


    /*************************************************************************
     *
     * union
     *
     * PURPOSE: Return a new set containing copies of all the elements in
     *          set this and in parameter set otherSet.
     *
     * METHOD: "Merge" the two sets --- they are ordered lists.
     *          Loop through both sets at the same time.
     *          Add the smaller of the two elements (the current one in each set)
     *          to the end of the new union set.
     *          If the two elements are equal, only put one copy into the union
     *          (because mathematical sets never contain duplicates).
     *
     * This method is nondestructive (neither of the input sets are changed by
     * this method).
     *
     **************************************************************************/
    public Set union( Set otherSet )
    {
        Node tcurr, ocurr; // curr for this and curr for otherSet, respectively.
        Set result = new Set();

        tcurr = this.top;
        ocurr = otherSet.top;

        while ( tcurr != null || ocurr != null )
        {
            if ( tcurr != null && ( ocurr == null || tcurr.item < ocurr.item ) )
            {
                result.addLast( tcurr.item );
                tcurr = tcurr.next;
            }
            else if ( tcurr == null || ( ocurr != null && ocurr.item < tcurr.item ) )
            {
                result.addLast( ocurr.item );
                ocurr = ocurr.next;
            }
            else if ( tcurr != null && ocurr != null && tcurr.item == ocurr.item )
            { // the union should contain only one copy of an item that is in both sets
                result.addLast( tcurr.item );
                tcurr = tcurr.next;
                ocurr = ocurr.next;
            }
        } // end while
        return result;
    }

    /*************************************************************************
     *
     * intersection
     *
     * PURPOSE: Return a new set containing copies of the elements that both
     *          set this and parameter set otherSet have in common.
     *
     * METHOD: "Merge" the two sets --- they are ordered lists.
     *          That is, loop through both sets at the same time, and
     *          add an element to the end of the new intersection set only
     *          if the current elements in the two sets are the same element.
     *
     * This method is nondestructive (neither of the input sets are changed by
     * this method).
     **************************************************************************/
    public Set intersection( Set otherSet )
    {

/********************************** YOUR CODE GOES HERE *********************************/
    Node thisCurr, otherCurr; // curr for this and curr for otherSet, respectively.
    Set result = new Set();

    thisCurr = this.top;
    otherCurr = otherSet.top;

    while (thisCurr != null && otherCurr != null){
        if (thisCurr.item > otherCurr.item)
            otherCurr = otherCurr.next;
        else if (thisCurr.item < otherCurr.item)
            thisCurr = thisCurr.next;
        else {
            result.addLast(thisCurr.item);
            thisCurr = thisCurr.next;
            otherCurr = otherCurr.next;
        }
    }

    return result;

    }

    /*************************************************************************
     *
     * addLast (a private helper method for union and intersection)
     *
     * PURPOSE: Add newItem to the end of set this.
     *
     * ASSUMES: newItem is larger than all the other elements in set this.
     *
     **************************************************************************/
    private void addLast( int newItem )
    {
        if ( last != null )
        {
            last.next = new Node( newItem, last.next );
            last = last.next;
        }
        else
        {
            top = last = new Node( newItem, null );
        }
    }

} // end class Set

