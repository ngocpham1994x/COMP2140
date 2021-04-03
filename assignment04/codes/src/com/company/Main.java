package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

public class Main {

    public static void main(String[] args) throws Exception {

        final int dictionarySize = 94321, incorrectSize = 2797;

        //create Table dictionary
        TableWithSeparateChaining dictionary = new TableWithSeparateChaining(dictionarySize);

        //arguments checking:
        if(args==null||args.length==0) {
            throw new RuntimeException("File path must be given as a program argument.");
        }


        //Read in word.txt file (fileOne)
        String fileOnePath = args[0];

        BufferedReader fileOne; fileOne = new BufferedReader( new FileReader( fileOnePath) );

        //read in each line (each word in dictionary) and add to array dictionary
        try{
            String line = null;
            do{
                line = fileOne.readLine();
                if(line != null){
                    line = line.trim().toLowerCase();
                    dictionary.insert(line);
                }
            } while(line != null);
//            while( ( line = fileOne.readLine() ) != null){
//                line = line.trim().toLowerCase();
//                dictionary.insert(line);
//            }

        } catch ( IOException e){
            System.out.println("Error reading file: words.txt");
        }
        fileOne.close();
        System.out.println();




        //Read in GeorgeEliot SilasMarner.txt (fileTwo)
        String fileTwoPath = args[1];

        BufferedReader fileTwo; fileTwo = new BufferedReader( new FileReader( fileTwoPath) );

        //read in words in fileTwo, split and lower case each word
//        ArrayList test = new ArrayList();
        try{
            String line = null;
            String[] words = null;
            boolean incorrect = false;

            while( ( line = fileTwo.readLine() ) != null ){
                    words = line.trim().split( "[^a-zA-Z']+" ); // + means "match one or more of the previous expression"
                    for(int i = 0; i < words.length; i++) {
                        words[i] = words[i].toLowerCase();
                        incorrect = dictionary.search(words[i]);
                        if(!incorrect) {
                            System.out.println(words[i]);

                        }
                    }
            }


        } catch ( IOException e){
            System.out.println("Error reading file: words.txt");
        }
        fileTwo.close();




        System.out.println( "\nAssignment 04 program ends\n\n" );

    }
}


class TableWithSeparateChaining {

    //=====================================================
    // Class Node: An ordinary linked-list node
    //=====================================================
    private class Node {
        public String item;
        public Node next;

        public Node( String newItem, Node newNext ) {
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
     * insert(key)
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

}
