/** Name of class or program (matches filename)
 * COMP 2140 SECTION A01
 * INSTRUCTOR Akcora
 * ASSIGNMENT Assignment #5
 * @author  Ngoc Pham - 7891063
 * @version 01
 * Date of complete: April 18
 *
 * PURPOSE: creates Merkle Tree & return topHash
 */

package com.company;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class MerkleTree {

    /**
     *
     * @param transactions array of current block's transactions
     * @return topHash as a String
     * @throws NoSuchAlgorithmException
     * This method uses a helper method. Pass in a String[] array instead of Transaction[] array for recursion.
     * The parentList array is the hash array of previous level, with length = half of previous level if even length,
     * or length = half + 1 of previous level if odd length.
     * If odd transaction is detected (last odd transaction), calculate hash of that transaction on each level and
     * store to hash array for next level.
     */
    public String buildFrom(Transaction[] transactions) throws NoSuchAlgorithmException {
        //the existing code here is given to help you learn how SHA is used etc.

        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        String[] hashList = new String[transactions.length];
        for (int i = 0; i < transactions.length; i = i + 1) {
            Transaction tx1 = transactions[i];
            String h1 = UtilityFunctions.getSHA256(digest, tx1.toString());
            hashList[i] = h1;
        }

        int level = 0;
        String topHash = buildFromHelper(hashList, level);
        return topHash;
    }

    public String buildFromHelper(String[] hashList, int level) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        String topHash = "";
        System.out.println("Merkle Tree, Bottom Up, Level: "+level+", number of hashes: "+hashList.length);

        if(hashList.length == 1) {
            topHash = UtilityFunctions.getSHA256(digest, hashList[0]);
            System.out.println( "Top hash is: "+topHash);
            return topHash;
        }
        else{
            int parentListLength;
            if(hashList.length % 2 == 0)
                parentListLength = hashList.length/2;
            else
                parentListLength = hashList.length/2 + 1;
            String[] parentHashList = new String[parentListLength];

            int parentHashListPosition = 0;

            for(int i=0; i< hashList.length && i+1 < hashList.length; i=i+2){
                String hashedString = UtilityFunctions.getSHA256(digest,UtilityFunctions.getSHA256(digest,hashList[i]) + (UtilityFunctions.getSHA256(digest,hashList[i+1])) );
                parentHashList[parentHashListPosition] = hashedString;
                parentHashListPosition++;
            }

            if(hashList.length % 2 == 1){
                String lastHash = hashList[hashList.length - 1];
                String lastHashString = UtilityFunctions.getSHA256(digest, lastHash);
                parentHashList[parentHashList.length - 1] = lastHashString;
            }

            return buildFromHelper(parentHashList,++level);
        }

    }

}
