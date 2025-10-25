package com.bank.main_serv; 

import com.bank.domain.BankAccount;
import java.util.ArrayList;
import java.util.List;

public class Bank {

    private List<BankAccount> accs;

    private static int counter = 1;

    public Bank() {
        this.accs = new ArrayList<>();
    }
    // создание нового счета
    public BankAccount openAcc(String name, double money) {
        String newId = "ACC-" + counter;
        counter++;

        BankAccount newAcc = new BankAccount(newId, name, money);
        this.accs.add(newAcc);
        
        System.out.println("Счет открыт! Номер: " + newId);
        return newAcc;
    }

    public BankAccount find(String accNum) {
        String newAccNum = "ACC-" + accNum;
        for (BankAccount acc : accs) {
            if (acc.getAccNum().equals(newAccNum)) {
                return acc; // True
            }
        }
        
        return null; 
    }
    
    public List<BankAccount> getAll() {
        return this.accs; 
    }
}