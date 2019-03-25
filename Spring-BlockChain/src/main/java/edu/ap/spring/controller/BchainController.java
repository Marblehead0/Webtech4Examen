package edu.ap.spring.controller;

//import java.security.KeyFactory;
//import java.security.PublicKey;
//import java.security.spec.X509EncodedKeySpec;
//import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.ap.spring.service.BChainInit;
import edu.ap.spring.service.Wallet;

@Controller
public class BchainController {

    @Autowired
    private BChainInit bChain;

    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }

    @PostMapping("/init")
    public String init(Model model){
        bChain.setUp();
        return "index";
    }

    @RequestMapping("/balance/walletA")
    @ResponseBody
    public String balanceA(){
        Wallet wallet = bChain.walletA;
        float balance = wallet.getBalance();

        StringBuilder b = new StringBuilder();
        b.append("<html><body><table>");
        b.append("<tr><th>Balance</th></tr>");

        b.append("<tr>");
            b.append("<td>");
                b.append(balance);
            b.append("</td>");
        b.append("</tr>");

        b.append("</table></body></html>");

        return b.toString();
    }

    @RequestMapping("/balance/walletB")
    @ResponseBody
    public String balanceB(){
        Wallet wallet = bChain.walletB;
        float balance = wallet.getBalance();

        StringBuilder b = new StringBuilder();
        b.append("<html><body><table>");
        b.append("<tr><th>Balance</th></tr>");

        b.append("<tr>");
            b.append("<td>");
                b.append(balance);
            b.append("</td>");
        b.append("</tr>");

        b.append("</table></body></html>");

        return b.toString();
    }

    @PostMapping("/transaction")
    public String sendFunds(@RequestParam("amount") Float amount) {
        Wallet senderWallet = bChain.walletA;
        Wallet receiverWallet = bChain.walletB;
        try{
            bChain.block1.addTransaction(senderWallet.sendFunds(receiverWallet.getPublicKey(), amount), bChain.bChain);
        }catch(Exception e){}
        bChain.bChain.addBlock(bChain.block1);
        return "redirect:/";
    }
}