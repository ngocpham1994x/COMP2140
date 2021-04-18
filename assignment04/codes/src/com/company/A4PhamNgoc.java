/** Name of class or program (matches filename)
 * COMP 2140 SECTION A01
 * INSTRUCTOR Akcora
 * ASSIGNMENT Assignment #4
 * @author  Ngoc Pham - 7891063
 * @version 01
 * Date of complete: April 3
 *
 * PURPOSE: reads in two text files and find incorrect spelling words.
 * words.txt is a dictionary which is used to verify words in GeorgeEliot_SilasMarner.txt
 * An invalid-word table is created and printed in console if the words in GeorgeEliot_SilasMarner.txt file are not in words.txt file.
 *
 * Use args[0] for words.txt, args[1] for GeorgeEliot_SilasMarner.txt
 *
 * words.txt has 6 duplicated words (printed in console) which are not added to hash table because it already exists in the table.

 * When an apostrophe acts as punctuation in THE WORD: THE WORD is trimmed to have no apostrophe before and after, punctuation is handled.
 * Examples: 'abc = abc, abc' = abc, 'abc' = s
 * "raincoat" is a valid word. "'raincoat'" = "'raincoat" = "raincoat'" = raincoat - is a valid word & in dictionary.
 *
 * When apostrophe acts as a part of THE WORD:
 * "raincoat's" = raincoat's - is a valid word because it's in dictionary.
 * "stephen's" = stephen's - is an invalid word because it's not in dictionary.
 *
 * A single apostrophe "'" is not considered as a word. It's considered as a punctuation & needs to be handled.
 *
 * Total words in dictionary: 47154
 * Total incorrect words: 1327
 */

package com.company; 

import java.io.*;
import java.util.*;


public class A4PhamNgoc {

    public static void main(String[] args) throws FileNotFoundException,IOException {

        final int DICTIONARY_SIZE = 94321, INCORRECT_TABLE_SIZE = 2797;

        //create Table dictionary
        TableWithSeparateChaining dictionary = new TableWithSeparateChaining(DICTIONARY_SIZE);
        TableWithSeparateChaining incorrectWordTable = new TableWithSeparateChaining(INCORRECT_TABLE_SIZE);

        //input file paths:
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the file path for .\\words.txt: ");
        String fileOnePath = scanner.next();
        System.out.println("Enter the file path for .\\GeorgeEliot_SilasMarner.txt: ");
        String fileTwoPath = scanner.next();
        scanner.close();
        System.out.println();

/**
 * Task 1: read in the dictionary words.txt
 */
        //Read in word.txt file (fileOne)

        BufferedReader fileOne; fileOne = new BufferedReader( new FileReader( fileOnePath ) );

        //read in each line (each word in dictionary) and add to array dictionary
        try{
            String line = null;

            //first way to read in line
            System.out.println("Duplicated words in dictionary: \n");
            do{
                line = fileOne.readLine();
                if(line != null){
                    line = line.trim().toLowerCase();
                    dictionary.insert(line);
                }
            } while(line != null);

            //second way to read in line
//            while( ( line = fileOne.readLine() ) != null){
//                line = line.trim().toLowerCase();
//                dictionary.insert(line);
//            }

        } catch ( FileNotFoundException e1){
            throw new FileNotFoundException ("FileNotFound: words.txt");
        } catch ( IOException e2){
            System.out.println("Error reading file: words.txt");
        }
        fileOne.close();
        System.out.println("\nThere are total of " + dictionary.numberItems + " words in dictionary words.txt \n");


/**
 * Task 2: read in the document GeorgeEliot_SilasMarner.txt
 * cross check with the dictionary.
 */
        //Read in GeorgeEliot SilasMarner.txt (fileTwo)

        BufferedReader fileTwo; fileTwo = new BufferedReader( new FileReader( fileTwoPath ) );

        //read in words in fileTwo, split and lower case each word
        try{
            int lineNum = 0;
            String line = null;
            String[] words = null;

            //second way to read in line
            while( ( line = fileTwo.readLine() ) != null ){
                words = line.trim().split( "[^a-zA-Z']+" ); // + means "match one or more of the previous expression"
                lineNum++;
                for(int i = 0; i < words.length; i++) {
                    String theWord = words[i].toLowerCase();
                    if(theWord.length() > 0) {
                        //cases with apostrophe acts as punctuation:
                        //case 1: single apostrophe ' is not considered a word
                        if( theWord.length() == 1 && theWord.charAt(0) == '\'' )
                            theWord = "";
                        //case 2: 'abc = abc
                        else if( theWord.charAt(0) == '\'' && (theWord.charAt(theWord.length()-1) != '\'') )
                            theWord = theWord.substring(1);
                        //case 3: abc' = abc
                        else if( theWord.charAt(0) != '\'' && (theWord.charAt(theWord.length()-1) == '\'') )
                            theWord = theWord.substring(0, theWord.length()-1);
                        //case 4: 'abc' = abc
                        else if( (theWord.charAt(0) == '\'') && (theWord.charAt(theWord.length()-1) == '\'') )
                            theWord = theWord.substring(1,theWord.length()-1); // substring(beginIndex,endIndex) where endIndex is not included
                    }

                    if( theWord.length() != 0 && !dictionary.search(theWord) )
                        incorrectWordTable.insert(theWord,lineNum);

                }
            }


/**
 * Task 3: traverse incorrectTable, print key & stack
  */
            System.out.println("There are total of " + incorrectWordTable.numberItems + " invalid words: \n");
            System.out.println(incorrectWordTable.toString());

        } catch ( FileNotFoundException e1){
            throw new FileNotFoundException ("FileNotFound: GeorgeEliot_SilasMarner.txt");
        } catch ( IOException e2){
            System.out.println("Error reading file: GeorgeEliot_SilasMarner.txt");
        }
        fileTwo.close();

        System.out.println( "\nAssignment 04 program ends\n" );

    }
}





/******************************************************************
 * TableWithSeparateChaining Class
 *****************************************************************/

class TableWithSeparateChaining {

    //=====================================================
    // Class Node: An ordinary linked-list node
    //=====================================================
    private class Node {
        public String item;
        public Stack stack;
        public Node next;

        //Node constructor for dictionary table
        public Node( String newItem, Node newNext ) {
            item = newItem;
            next = newNext;
        } // end Node constructor

        //Node constructor for incorrect-word table
        public Node( String newItem, int num, Node newNext ) {
            stack = new Stack();
            stack.addTop(num);
            item = newItem;
            next = newNext;
        } // end Node constructor

    } // end class Node
    //=====================================================


    private static final int A = 13; // For the hash function

    private Node[] hashArray; // The array of linked lists.
    int numberItems; // The number of items currently stored in the table.

    /******************************************************************
     * TableWithSC constructor
     * Assumption: tableSize is a prime number
     *****************************************************************/
    public TableWithSeparateChaining (int tableSize ) {
        hashArray = new Node[ tableSize ];
        for ( int i = 0; i < hashArray.length; i++ )
            hashArray[ i ] = null;
        numberItems = 0;
    } // end TableWithSC constructor


    /******************************************************************
     * hash(key)
     * Hashes key and returns the resulting array index.
     * The hash function uses the polynomial hash code
     * implemented using Horner's method.
     *****************************************************************/
    private int hash( String key ) {
        int hashIndex = 0;

        for ( int i = 0; i < key.length(); i++ ) {
            hashIndex = (hashIndex * A ) % hashArray.length + (int) key.charAt(i);
            hashIndex = hashIndex % hashArray.length;
        } // end for

        return hashIndex;
    } // end hash

    /******************************************************************
     * search(key)
     * Searches the table for key, and returns true if key is found,
     * false if it isn't.
     *****************************************************************/
    public boolean search( String key ) {
        int hashIndex = hash( key );
        return inLinkedList( key, hashArray[ hashIndex ] );
    } // end search

    /******************************************************************
     * inLinkedList(key,top)
     * Searches the linked list pointed at by top for a node containing
     * key, and returns true if key is found and false if it isn't.
     *****************************************************************/
    private boolean inLinkedList( String key, Node top ) {
        Node curr = top;
        boolean foundKey = false;

        while ( curr != null && !foundKey ) {
            foundKey = curr.item.equals( key );
            curr = curr.next;
        } // end while

        return foundKey;
    } // end inLinkedList

    /******************************************************************
     * insert(key)
     * THIS insert(key) METHOD IS FOR DICTIONARY TABLE.
     * Inserts key into the table (except if key is already in the table,
     * in which case, this method prints an error message).
     * Also, this method increments numberItems if the key is inserted.
     *****************************************************************/
    public void insert( String key ) {
        if(search(key))
            System.out.println("Duplicates are not allowed: \"" + key + "\"");
        else{
            int hashIndex = hash(key);
            hashArray[hashIndex] = new Node(key,hashArray[hashIndex]);
            numberItems++;
        }
    } // end insert

    /******************************************************************
     * insert(key)
     * THIS insert(key) METHOD IS FOR INCORRECT-WORD TABLE.
     * Inserts key into the table (except if key is already in the table,
     * in which case, this adds the line number to the top of stack).
     * Also, this method increments numberItems if the key is inserted.
     *****************************************************************/
    public void insert( String key, int lineNum ) {
        int hashIndex = hash(key);
        if(search(key))
            hashArray[hashIndex].stack.addTop(lineNum);
        else {
            hashArray[hashIndex] = new Node(key, lineNum, hashArray[hashIndex]);
            numberItems++;
        }
    } // end insert


    /******************************************************************
     * toString()
     * Function: This method returns a String giving information about the
     * non-empty entries of the incorrect-word table
     * CAN PRINT "hashIndex" TO output TO VERIFY THE LINKED LIST IN "hashArray"
     *****************************************************************/
    public String toString() {
        String output = "";
        int hashIndex;
        Node curr;

        for ( int i = 0; i < hashArray.length; i++ ) {
            if ( hashArray[i] != null ) {
                hashIndex = hash(hashArray[i].item);

                curr = hashArray[i];
                while ( curr != null ) {
                    output += "Invalid word \"" + curr.item + "\" found on lines " + curr.stack.toString() + "\n";
                    curr = curr.next;
                } // end while
            } //end if
        } // end for

        return output;
    } // end toString

}








/******************************************************************
 * Stack Class  (for incorrectWordTable)
 *****************************************************************/

class Stack {

    //=====================================================
    // Class Node: An ordinary linked-list node
    //=====================================================
    private class  Node {
        public int lineNum;
        public Node next;

        public Node( int newLine, Node newNext ) {
            lineNum = newLine;
            next = newNext;
        } // end Node constructor
    } // end Node class
    //=====================================================

    private Node top;

    /******************************************************************
     * Stack constructor
     * Assumption: Stack is implemented as a linked list
     *****************************************************************/
    public Stack(){
        top = null;
    }

    /******************************************************************
     * addTop(line)
     * Function: add to top of Stack the line number where the word occurs.
     *****************************************************************/
    public void addTop(int line){
        Node newNode = new Node(line, top);
        top = newNode;
    }

    /******************************************************************
     * toString()
     * Function: traverse the Stack and print all the lineNum.
     *****************************************************************/
    public String toString(){
        String result = "";
        Node curr = top;

        while(curr != null) {
            result += curr.lineNum + " ";
            curr = curr.next;
        }
        return result;
    }

}

