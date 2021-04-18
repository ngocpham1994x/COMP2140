/** Name of class or program (matches filename)
* COMP 2140 SECTION A01
* INSTRUCTOR Akcora
* ASSIGNMENT Assignment #5
* @author  Ngoc Pham - 7891063
* @version 01
* Date of complete: April 18
*
* PURPOSE: creates and validates block-chain using Merkle Tree
*/

package com.company;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class A5PhamNgoc {

    static private final int MINUTE = 60*1000;//in milliseconds
    static final long SATOSHI = 100000000;// satoshi is the subunit of bitcoins (like 100 cents in a  dollar)
    private static int reward = 50;//bitcoins per each block
    static private BigInteger blockReward = new BigInteger(String.valueOf(reward * SATOSHI)); // block reward in satoshis.
    static int interBlockTime = 10* MINUTE;//one block every ten minutes
    private int currentHeight=0;
    static private String addressOfSatoshi = "16cou7Ht6WjTzuFyDBnht9hmvXytg6XdVT";
    static BigInteger difficulty;
    private ArrayList<Block>  blocks;
    static private final int diffFreq =2016;
    static  private final int numOfSimulatedBlocks =10;


    public A5PhamNgoc()  {
        blocks = new ArrayList<>();
        int maxPower =77;//a 256 bit number can have a max value around 10^77
        int initialOffset=5; //we want initial difficulty to be 10^5 only
        //set initial block mining difficulty
        difficulty = new BigInteger("9".repeat(maxPower - initialOffset));// 9 is the biggest digit

    }

    public static void main(String[] args) throws NoSuchAlgorithmException {

        A5PhamNgoc bitcoin = new A5PhamNgoc();
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        System.out.println("The Bitcoin blockchain is created.");
        System.out.println("Block reward is "+blockReward+" satoshis per block paid to the block's miner.");
        System.out.println("There will be (approx.) "+interBlockTime+" miliseconds between two blocks");
        System.out.println("Block reward halves every 210K blocks (takes around 4 years)");
        System.out.println("The Peer-to-Peer network will be fake; we will simulate it.");
        //genesis block starts the blockchain. It contains just one transaction that gives the block reward to the first miner.
        Transaction coinbaseTransaction = new Transaction("","",blockReward);
        Block genesisBlock = new Block(addressOfSatoshi,null);
        genesisBlock.addTx(coinbaseTransaction);
        genesisBlock.setBlockHash(UtilityFunctions.getSHA256(digest,coinbaseTransaction.toString()));
        bitcoin.addBlock(genesisBlock);
        //genesis block creation ends.

        System.out.println("The blockchain will end once the block "+numOfSimulatedBlocks+" is mined.");
        //anyone in the network can be  a miner. Let's say Cuneyt has the address Cuneyt1357912
        String miner = "Cuneyt1357912";

        PeerToPeerNetwork p2p = new PeerToPeerNetwork();
        Block prevBlock=genesisBlock;
        while(bitcoin.currentHeight<numOfSimulatedBlocks){

            Block currentBlock = new Block(miner,prevBlock);
            // Coinbase transaction pays the miner for its effort.
            Transaction currCoinbase = new Transaction("",miner,blockReward);
            //end of the coinbaseTransaction transaction
            //ordinary transaction from users
            Transaction[] transactions = p2p.collectNewTransactions();
            System.out.println("MemPool contained "+transactions.length+" transactions.");
            transactions[0]=currCoinbase;//insert the coinbaseTransaction transaction as the very first in a block
            System.out.println("The coinbaseTransaction transaction is created by the miner, and added to the block.");
            for(Transaction transaction:transactions){
                currentBlock.addTx(transaction);
            }
            MerkleTree tree =  new MerkleTree();
            String topHash = tree.buildFrom(transactions);
            String hash = prevBlock.getBlockHash()+topHash;
            Map<String,String> result= mineTheBlock(digest,hash, difficulty);
            long nonce = Long.parseLong(result.get("nonce"));
            String blockHash = result.get("blockHash");
            if(nonce!=-1) {//we found an appropriate nonce.
                currentBlock.setBlockHash(blockHash);
                System.out.println("\r\nBlock "+bitcoin.currentHeight+" is mined. "+
                        prevBlock.getBlockHash()+"->"+currentBlock.getBlockHash());

                currentBlock.setNonce(nonce);
                bitcoin.addBlock(currentBlock);
                prevBlock=currentBlock;

                //optional: write code to update the difficulty level every (2016 blocks) 2 weeks.
                if(bitcoin.currentHeight%diffFreq==0){
                    // difficulty needs to be halved.
                    // https://en.bitcoin.it/wiki/Difficulty
                    //
                }
                //optional: write code to update block reward every (210K blocks) 4 years.
                //https://www.bitcoinblockhalf.com/

            }

        }
        // corrupt a transaction in a block
        int blockToCorrupt= new Random().nextInt(numOfSimulatedBlocks-1);
        blockToCorrupt+=1;//so that the zeroth block will not be selected
        Block b= bitcoin.getBlock(blockToCorrupt);
        int txToCorrupt= new Random().nextInt(b.numOfTx);
        Transaction tx = b.getTransaction(txToCorrupt);
        tx.setAmount("35");
        //end of the corruption code

        //detecting the induced corruption
        int height= bitcoin.validate(digest);
        System.out.println("Block "+blockToCorrupt+ " was chosen to be corrupted");
        System.out.println("The code found the corruption:"+(height==blockToCorrupt));
    }

    /**
     * Method: validate()
     * The validation starts from the second block. The first block (genesis block) is mined by the creator of the Bitcoin: Satoshi Nakamoto.
     * calculate the current block's hash = prev block's hash + current block's topHash + nonce.
     * The current block's topHash is calculated by creating a Merkle tree from this block's transaction & find topHash.
     * then pass to mineTheBlock so that nonce is accounted for to calculate final current block's hash.
     * compare this calculated current block's hash vs stored current block's hash variable (blockhash).
     * If not match, call it a corruption.
     */
    private int validate(MessageDigest digest) throws NoSuchAlgorithmException {
        //digest is used to compute the SHA256 hash

        Block curr;
        Block prev;
        MerkleTree tree = new MerkleTree();

        int height=-1;
        int blockIndex = 1;

        while (height == -1 && blockIndex < this.blocks.size()){
            // Take the list of transactions stored in a block, and create a Merkle tree from them again
            // to find topHash
            curr = blocks.get(blockIndex);
            ArrayList<Transaction> blockTx = curr.getTransactions();
            Transaction[] transactionList = new Transaction[blockTx.size()];
            for(int i=0; i<blockTx.size(); i++)
                transactionList[i] = blockTx.get(i);
            String topHash = tree.buildFrom(transactionList);

            // Link the previous block and nonce
            // Compute the Block hash and compare it to the block hash stored in the block.
            // if they do not match, call it a corruption
            prev = blocks.get(blockIndex - 1);
            String hash = prev.getBlockHash()+topHash;
            Map<String,String> result= mineTheBlock(digest,hash, difficulty);
            String blockHash = result.get("blockHash");


            // compare calculated blockHash to the block hash stored in the block.
            // if they do not match, call it a corruption
            if(!blockHash.equals(curr.getBlockHash()))
                height = blockIndex;

            blockIndex++;
        }

        return height;
    }

    private Block getBlock(int blockToCorrupt) {
        return this.blocks.get(blockToCorrupt);
    }

    /**
     *
     * @param digest is the hashing algorithm helper
     * @param hash hash of block content
     * @param difficulty a level that we must satisfy with a nonce
     * @return hash of the block that we found with the appropriate nonce
     */

    private static Map<String, String> mineTheBlock(MessageDigest digest, String hash, BigInteger difficulty) {
        long result=-1;
        String blockHash="";
        for(long nonce=0;(nonce<Long.MAX_VALUE&&result==-1);nonce++){
            String hexString= UtilityFunctions.getSHA256(digest,(hash+nonce));

            BigInteger bigInt = new BigInteger(hexString, 16);
            if(bigInt.compareTo(difficulty)<0){
                String blockHash1 = bigInt.toString();
                System.out.println("Nonce: "+nonce+" satisfies the difficulty."+ blockHash1 +" < "+difficulty);
                result=nonce;
                blockHash = hexString;
            }
        }
        Map m= new HashMap<String,String>();
        m.put("nonce",result+"");
        m.put("blockHash",blockHash);
        return m;
    }

    private void addBlock(Block block) {
        this.blocks.add(block);
        this.currentHeight++;

    }
}

/**REPORT**:
 *
 * 1. What is the depth of the Merkle tree in a block?
 *      Since Merkle tree is similar to complete Binary Search Tree,
 *      the number of transactions (n) is found by 2^depth=n, the depth of the merkle tree of a single block is approximately:
 *      depth=log(n)/log(2)
 *
 * 2. If we want to detect corruption at the transaction level, how many comparisons should we make in the Merkle tree?
 *      To find corruption at transaction level, the code would have to do a maximum of n comparisons (corruption at the end)
 *      and an average of log(n) comparisons (corruption at half of the tree, binary search).
 *
 * 3. Is there a way to corrupt any part of the blockchain without being detected?
 *      There is no way to corrupt a block at the transaction level without being detected, unless the merkle trees end
 *      up being equivalent, which is unlikely.
 */
