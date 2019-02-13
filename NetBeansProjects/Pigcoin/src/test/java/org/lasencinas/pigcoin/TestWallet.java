package org.lasencinas.pigcoin;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class TestWallet {
    
    @Test
    public void constructor_test() {
        Wallet wallet = new Wallet();
        assertNotNull(wallet);
    }
    
    @Test
    public void generate_key_pair_test() {
        Wallet wallet = new Wallet();
        assertNotNull(wallet);
        wallet.generateKeyPair();
        assertNotNull(wallet.getAddress());
        assertNotNull(wallet.getSKey());
    }
    
    
}
