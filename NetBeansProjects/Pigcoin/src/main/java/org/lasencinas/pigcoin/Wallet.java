
package org.lasencinas.pigcoin;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Wallet {
    
    private PublicKey address = null;
    private PrivateKey sKey = null;
    private int total_input = 0;
    private int total_output = 0;
    private int balance = 0;
    private List<Transaction> inputTransactions = null;
    private List<Transaction> outputTransactions = null;
    
//CONSTRUCTOR

    public void setAddress(PublicKey address) {
        this.address = address;
    }
    
    public void setSK(PrivateKey sKey) {
        this.sKey = sKey;
    }
    
    public void setTotalInput(int totalInput) {
        this.total_input = total_input;
    }
    
    public int getTotalInput() {
        return this.total_input;
    }
    
    public void setTotalOutput(int totalOutput) {
        this.total_output = total_output;
    }
    
    public int getTotalOutput() {
        return this.total_output;
    }
    
    public void setBalance(int balance) {
        this.balance = balance;
    }
    
    public int getBalance() {
        return this.balance;
    }
    
    public PrivateKey getSKey() {
        return this.sKey;
    } 
    
    public void setInputTransaction(List<Transaction> transactions) {
        this.inputTransactions = transactions;
    }
    
    public void setOutputTransaction(List<Transaction> transactions) {
        this.outputTransactions = transactions;
    }
    
    public List<Transaction> getInputTransactions() {
        return this.inputTransactions;
    }
    
    public List<Transaction> getOutputTransactions() {
        return this.outputTransactions;
    }
    
//MÉTODOS
        
    public PublicKey getAddress() {
        return this.address;
    }
    
    public void generateKeyPair() {
        setAddress(GenSig.generateKeyPair().getPublic());
    }
    
    @Override
    public String toString() {
        return "\n" + "Wallet = " + getAddress().hashCode() + "\n" + 
                      "Total input = " + getTotalInput() + "\n" +
                      "Total output = " + getTotalOutput() + "\n" +
                      "Balance = " + getBalance() + "\n";
    }
    
    public void updateBalance() {
        this.balance = this.getTotalOutput() - this.getTotalInput();
    }
    
    public void loadCoins(BlockChain bChain) {
        double[] pigcoins = {0, 0};
        pigcoins = bChain.loadWallet(getAddress());
        setTotalInput((int) pigcoins[0]);
        setTotalOutput((int) pigcoins[1]);
        updateBalance();
    }
    
    public void loadInputTransactions(BlockChain bChain) {
        setInputTransaction(bChain.loadInputTransactions(getAddress()));
    }
    
    public void loadOutputTransactions(BlockChain bChain) {
        setOutputTransaction(bChain.loadInputTransactions(getAddress()));
    }
    
    public Map<String, Double> collectCoins(double pigcoins) {
        
        Map<String, Double> collectedCoins = new LinkedHashMap<>();

        if (getInputTransactions() == null) {
            return null;
        }

        if (pigcoins > getBalance()) {
            return null;
        }

        Double achievedCoins = 0d;

        Set<String> consumedCoins = new HashSet<>();
        if (getOutputTransactions() != null) {
            for (Transaction transaction : getOutputTransactions()) {
                consumedCoins.add(transaction.getPrevHash());
            }   
        }             

        for (Transaction transaction : getInputTransactions()) {

            if (consumedCoins.contains(transaction.getHash())) {
                continue;
            }

            if (transaction.getPigCoins() == pigcoins) {
                collectedCoins.put(transaction.getHash(), transaction.getPigCoins());
                consumedCoins.add(transaction.getHash());
                break;
            } else if (transaction.getPigCoins() > pigcoins) {
                collectedCoins.put(transaction.getHash(), pigcoins);
                collectedCoins.put("CA_" + transaction.getHash(), transaction.getPigCoins() - pigcoins);
                consumedCoins.add(transaction.getHash());
                break;
            } else {
                collectedCoins.put(transaction.getHash(), transaction.getPigCoins());
                achievedCoins = transaction.getPigCoins();
                pigcoins = pigcoins - achievedCoins;
                consumedCoins.add(transaction.getHash());
            }

        }
        // getInputTransactions().removeAll(consumedCoins);
        return collectedCoins;
    }
    
    public void sendCoins(PublicKey pKey_recipient, Double coins, String message, BlockChain bChain) {
        
        Map<String, Double> consumedCoins   = new LinkedHashMap<>();
        
        consumedCoins = collectCoins(coins);
        
        if (consumedCoins != null) {
            bChain.processTransactions(getAddress(),pKey_recipient, consumedCoins, message, signTransactions(message));
        }
        this.loadCoins(bChain);
    }
    
    public byte[] signTransactions(String message) {
        return GenSig.sign(getSKey(), message);
    }
    
    

}
