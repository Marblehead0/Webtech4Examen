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

@Service
@Component
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
		block1.setPreviousHash(genesis.hash);
		block1.setTimeStamp();
		block1.calculateHash();

		try {
			block1.addTransaction(walletA.sendFunds(walletB.getPublicKey(),50f), bChain);
		} catch (Exception e) {
			//TODO: handle exception
		}
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