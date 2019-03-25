package edu.ap.spring.service;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import edu.ap.spring.transaction.Transaction;
//import edu.ap.spring.transaction.TransactionOutput;
import edu.ap.spring.service.Block;

@Scope("prototype")
public class BChainInit {
    @Autowired
	public BlockChain bChain;
	@Autowired
	public Wallet coinbase, walletB;
	@Autowired
	public Wallet walletA;
	@Autowired
	public Block genesis, block1;
	@Autowired
    public Transaction genesisTransaction;
    
	private Map<String, Wallet> map = new HashMap<String, Wallet>();
	
	@PostConstruct
    public void setUp() {
		bChain.setSecurity();
		coinbase.generateKeyPair();
		walletA.generateKeyPair();
		walletB.generateKeyPair();

		//create genesis transaction, which sends 100 coins to walletA:
		genesisTransaction.setSender(coinbase.getPublicKey());
		genesisTransaction.setRecipient(walletA.getPublicKey());
		genesisTransaction.setValue(100f);
		//genesisTransaction.setInputs(null);
		genesisTransaction.generateSignature(coinbase.getPrivateKey()); // manually sign the genesis transaction
		genesisTransaction.transactionId = "0"; //manually set the transaction id
		//genesisTransaction.outputs.add(new TransactionOutput(genesisTransaction.recipient, genesisTransaction.value, genesisTransaction.transactionId)); //manually add the Transactions Output
		//bChain.getUTXOs().put(genesisTransaction.outputs.get(0).id, genesisTransaction.outputs.get(0)); //it's important to store our first transaction in the UTXOs list.
				
		//creating and Mining Genesis block
		genesis.setPreviousHash("0");
		genesis.setTimeStamp();
		genesis.calculateHash();
		genesis.addTransaction(genesisTransaction, bChain);
        bChain.addBlock(genesis);
        
        //IS GOOD?
        block1.setPreviousHash(genesis.hash);
		block1.setTimeStamp();
        block1.calculateHash();

        map.put("A",walletA);
        map.put("B", walletB);
    }
    public Wallet getWalletFromKey(String wallet){
        return this.map.get(wallet);
    }

}