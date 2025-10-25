package com.bank.transac;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {

    public static enum TransactionType {
    DEPOSIT("Пополнение"),
    WITHDRAW("Снятие");

    private final String finDescription;

    TransactionType(String finDescription) {
        this.finDescription = finDescription;
    }

    public String getDescription() {
        return finDescription;
    }
}

    private final LocalDateTime date;
    private final TransactionType type;
    private final double money; 


    private static final DateTimeFormatter FORMAT = 
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Transaction(TransactionType type, double money) {
        this.date = LocalDateTime.now();
        this.type = type;
        this.money = money;
    }

    
    public LocalDateTime getDate() {
        return date;
    }

    public TransactionType getType() {
        return type;
    }

    public double getMoney() {
        return money;
    }


    @Override
    public String toString() {
        String fDate = date.format(FORMAT); 
        String desc = type.getDescription(); 

        return String.format("[%s] %-10s | Сумма: %.2f", 
                fDate, 
                desc, 
                money);
    }
}