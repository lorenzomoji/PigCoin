
package org.lasencinas.pigcoin;

import java.security.PublicKey;
import org.lasencinas.pigcoin.App;
import org.lasencinas.pigcoin.GenSig;
import org.lasencinas.pigcoin.Wallet;

public class Transaction {
    private String hash = null;
    private String prev_hash = null;
    private PublicKey pKey_sender = null;
    private PublicKey pKey_recipient = null;
    private double pigcoins = 0;
    private String message = null;
    private byte[] signature = null;
    
    public Transaction() {
        
    }
    
    public Transaction (String hash, String prev_hash, PublicKey pKey_sender, PublicKey pKey_recipient, double pigcoins, String message) {
        this.hash = hash;
        this.prev_hash = prev_hash;
        this.pKey_sender = pKey_sender;
        this.pKey_recipient = pKey_recipient;
        this.pigcoins = pigcoins;
        this.message = message;
    }
    
    public double getPigCoins() {
        return this.pigcoins;
    }

    @Override
    public String toString() {
        return "\n" + "hash = "+ this.hash + "\n" + 
                "prev_hash = " + this.prev_hash + "\n" +
                "pKey_sender = " + get_PK_sender().hashCode() + "\n" +
                "pKey_recipient = " + get_PK_recipient().hashCode() + "\n" +
                "pigcoins = " + getPigCoins() + "\n" +
                "message = " + getMessage() +  "\n";
    }

    public PublicKey get_PK_sender() {
        return this.pKey_sender;
    }

    public PublicKey get_PK_recipient() {
        return this.pKey_recipient;
    }

    public String getHash() {
        return this.hash;
    }

    public String getPrevHash() {
        return this.prev_hash;
    }

    public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public byte[] getSignature() {
		return signature;
	}

	public void setSignature(byte[] signature) {
		this.signature = signature;
	}
}
