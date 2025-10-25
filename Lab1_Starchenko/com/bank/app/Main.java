package com.bank.app; 

import com.bank.domain.BankAccount;
import com.bank.transac.Transaction;
import com.bank.transac.Transaction.TransactionType;
import com.bank.main_serv.Bank;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static final int MENU_EXIT = 0;
    private static final int MENU_OPEN_ACC = 1;
    private static final int MENU_DEPOSIT = 2;
    private static final int MENU_WITHDRAW = 3;
    private static final int MENU_BALANCE = 4;
    private static final int MENU_TRANSACTIONS = 5;
    private static final int MENU_SEARCH = 6;

// Запуск меню
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Bank bank = new Bank();
        
        while (true) {
            printMenu();

            int choice = -1;

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: Введите число от 0 до 6.");
                continue;
            }

            if (choice == MENU_EXIT) {
                System.out.println("Спасибо за использование нашего банка. До свидания!");
                break;
            }

            handleMenu(choice, scanner, bank);
        }
        
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n--- Банковское Меню ---");
        System.out.println(MENU_OPEN_ACC + ". Открыть счёт");
        System.out.println(MENU_DEPOSIT + ". Положить деньги");
        System.out.println(MENU_WITHDRAW + ". Снять деньги");
        System.out.println(MENU_BALANCE + ". Показать баланс");
        System.out.println(MENU_TRANSACTIONS + ". Вывести список транзакций");
        System.out.println(MENU_SEARCH + ". Искать по атрибутам");
        System.out.println("-------------------------");
        System.out.println(MENU_EXIT + ". Выход");
        System.out.print("Ваш выбор: ");
    }

    private static void handleMenu(int choice, Scanner scanner, Bank bank) {
        BankAccount acc = null;
        if (choice > MENU_OPEN_ACC && choice <= MENU_SEARCH) {
            System.out.print("Введите номер счёта (например, 1): ");
            String accNum = scanner.nextLine();
            
            acc = bank.find(accNum);
            
            if (acc == null) {
                System.out.println("Ошибка: Счёт '" + accNum + "' не найден.");
                return;
            }
        }
        // Основа и обработка ошибок
        switch (choice) {
            case MENU_OPEN_ACC:
                System.out.print("Введите имя владельца: ");
                String name = scanner.nextLine();
                System.out.print("Введите начальный депозит (0, если нет): ");
                double money = 0;
                try {
                     money = Double.parseDouble(scanner.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Ошибка: неверная сумма. Установлено 0.0");
                }
                bank.openAcc(name, money);
                break;

            case MENU_DEPOSIT:
                System.out.print("Введите сумму для пополнения: ");
                try {
                    double depMoney = Double.parseDouble(scanner.nextLine());
                    acc.deposit(depMoney);
                } catch (Exception e) {
                    System.out.println("Ошибка операции: " + e.getMessage());
                }
                break;

            case MENU_WITHDRAW:
                 System.out.print("Введите сумму для снятия: ");
                try {
                    double widMoney = Double.parseDouble(scanner.nextLine());
                    acc.withdraw(widMoney);
                } catch (Exception e) {
                    System.out.println("Ошибка операции: " + e.getMessage());
                }
                break;

            case MENU_BALANCE:
                System.out.printf("Текущий баланс: %.2f%n", acc.getBalance());
                break;

            case MENU_TRANSACTIONS:
                System.out.println("--- История транзакций для счёта " + acc.getAccNum() + " ---");
                List<Transaction> trans = acc.getTransactions();
                if (trans.isEmpty()) {
                    System.out.println("Транзакций пока нет.");
                } else {
                    for (Transaction t : trans) {
                        System.out.println(t);
                    }
                }
                break;

            case MENU_SEARCH:
                System.out.print("По какому типу искать? (1 - Пополнения, 2 - Снятия): ");
                int typeChoice = Integer.parseInt(scanner.nextLine());
                Transaction.TransactionType searchType = null;
                
                if(typeChoice == 1) searchType = TransactionType.DEPOSIT;
                else if (typeChoice == 2) searchType = TransactionType.WITHDRAW;
                else {
                    System.out.println("Неверный тип.");
                    break;
                }

                System.out.println("--- Результаты поиска (" + searchType.getDescription() + ") ---");
                int count = 0;
                for (Transaction t : acc.getTransactions()) {
                    if (t.getType() == searchType) {
                        System.out.println(t);
                        count++;
                    }
                }
                if (count == 0) {
                    System.out.println("Ничего не найдено.");
                }
                break;

            default:
                if (choice != MENU_EXIT) {
                    System.out.println("Неверный пункт меню. Попробуйте снова.");
                }
                break;
        }
    }
}