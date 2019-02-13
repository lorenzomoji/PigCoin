
package org.lasencinas.pigcoin;

import java.security.PrivateKey;
import java.security.PublicKey;

public class Wallet {
    
    private PublicKey address = null;
    private PrivateKey sKey = null;
    private int total_input = 0;
    private int total_output = 0;
    private int balance = 0;
    
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
    
    
    

}
