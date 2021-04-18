import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class MerkleTree {

    public String buildFrom(Transaction[] transactions) throws NoSuchAlgorithmException {
        //the existing code here is given to help you learn how SHA is used etc.
        //the code is not complete. It does not build a Merkle tree, just iterates over transactions and takes their hash
        //You can change this function how ever you want. You can delete the code, and rewrite your own.
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        int size=0;
        for(int i=0;i<transactions.length;i=i+1){
            Transaction tx1 = transactions[i];
            String h1 =UtilityFunctions.getSHA256(digest, tx1.toString());
            size++;
        }

        int level =2;
        String topHash="dummyString";
        System.out.println("Merkle Tree, Bottom Up, Level: "+level+", number of hashes: "+size);
        System.out.println( "Merkle top hash is: "+topHash);
        return topHash;

    }
}
