package com.bank.domain;

import com.bank.transac.Transaction;
import com.bank.transac.Transaction.TransactionType;

import java.util.ArrayList;
import java.util.List;

public class BankAccount {

    private final String accNum;
    private final String name;
    private double balance;

    private final List<Transaction> trans;
    // Конструктор
    public BankAccount(String accNum, String name, double money) {
        if (money < 0) {
            throw new IllegalArgumentException("Начальный депозит не может быть отрицательным.");
        }

        this.accNum = accNum;
        this.name = name;
        this.balance = money;
        this.trans = new ArrayList<>();
        
        if (money > 0) {
            this.addTransaction(Transaction.TransactionType.DEPOSIT, money); // остальные разы джава почему-то понимает, что тип транзакции взять из файла транзакций
        }
    }
    // Adder
    public void deposit(double money) {
        if (money <= 0) {
            throw new IllegalArgumentException("Сумма пополнения должна быть положительной.");
        }
        
        this.balance += money;
        this.addTransaction(TransactionType.DEPOSIT, money);
        System.out.printf("Успешно пополнено: %.2f%n", money);
    }
    // Minuser
    public boolean withdraw(double money) {
        if (money <= 0) {
            throw new IllegalArgumentException("Сумма снятия должна быть положительной.");
        }
        if (money > this.balance) {
            System.out.printf("Ошибка: Недостаточно средств. Запрошено: %.2f, Доступно: %.2f%n",
                    money, this.balance);
            return false;
        }

        this.balance -= money;
        this.addTransaction(TransactionType.WITHDRAW, money);
        System.out.printf("Успешно снято: %.2f%n", money);
        return true;
    }

    private void addTransaction(TransactionType type, double money) {
        Transaction t = new Transaction(type, money);
        this.trans.add(t);
    }
    
    public double getBalance() {
        return this.balance;
    }

    public String getAccNum() {
        return this.accNum;
    }

    public String getName() {
        return this.name;
    }

    public List<Transaction> getTransactions() {
        return this.trans;
    }
}