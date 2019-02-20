
package org.lasencinas.pigcoin;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.lasencinas.pigcoin.GenSig;
import org.lasencinas.pigcoin.Transaction;


public class BlockChain {
    List<Transaction> blockChain = new ArrayList<Transaction>();
    
    //CONSTRUCTOR
    
    public BlockChain (){
        
    }
    
    public List<Transaction>getBlockChain() {
        return this.blockChain;
    }
    
    //LÓGICA
    
    public void addOrigin(Transaction transaction) {
        this.getBlockChain().add(transaction);
    }
    
    public void summarize() {
        for (Transaction transaction : getBlockChain()) {
            System.out.println(transaction.toString());
        }
    }
    
    public void summarize (int index) {
        System.out.println(getBlockChain().get(index).toString());
    }
    
    public double[] loadWallet(PublicKey address) {
        double pigcoinsIn = 0;
        double pigcoinsOut = 0;
        
        for (Transaction transaction : getBlockChain()) {
           if (address.equals(transaction.get_PK_recipient())) {
               pigcoinsIn = pigcoinsIn + transaction.getPigCoins();
           }
           if (address.equals(transaction.get_PK_sender())) {
               pigcoinsOut = pigcoinsOut + transaction.getPigCoins();
           }
           
        }
        
        double[] pigcoins = {pigcoinsIn, pigcoinsOut};
        return pigcoins;
    }
    
    public List<Transaction> loadInputTransactions(PublicKey address) {
        List<Transaction> inputTransactions = getBlockChain().stream().filter(transaction -> transaction.get_PK_recipient().equals(address)).collect(Collectors.toCollection(ArrayList<Transaction>::new));
        
        return inputTransactions;
    
    }
    
    public boolean isSignatureValid(PublicKey pKey, String message, byte[] sigendTransactions) {
        return GenSig.verify(pKey, message, sigendTransactions);
    }
    
    public boolean isConsumedCoinValid(Map<String, Double> consumedCoins) {
        for (String hash : consumedCoins.keySet()) { 
            for (Transaction transaction : blockChain) {
                if (hash.equals(transaction.getPrevHash())) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public void createTransaction(PublicKey pKey_sender, PublicKey pKey_recipient, Map<String, Double> consumedCoins, String message, byte[] signedTransaction) {
        PublicKey address_recipient = pKey_recipient;
        Integer lastBlock = 0;
        
        for (String prev_hash : consumedCoins.keySet()) {
            
            if (prev_hash.startsWith("CA_")){
                pKey_recipient = pKey_sender;
            }
            
            lastBlock = blockChain.size() + 1;
            Transaction transaction = new Transaction("hash_" + lastBlock.toString(), prev_hash, pKey_sender,pKey_recipient,consumedCoins.get(prev_hash), message);
            getBlockChain().add(transaction);
            pKey_recipient = address_recipient;
        }
    }
    public void processTransactions(PublicKey pKey_sender, PublicKey pKey_recipient, Map<String, Double> consumedCoins,
            String message, byte[] signedTransaction) {
        
        if (isSignatureValid(pKey_sender, message, signedTransaction) && isConsumedCoinValid(consumedCoins)) {
            // crear las nuevas transacciones y añadirlas al blockchain
            createTransaction(pKey_sender, pKey_recipient, consumedCoins, message, signedTransaction);
        }
    }
}
