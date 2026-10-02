package org.example.proyectofinalprogramacion2.Model;

import org.example.proyectofinalprogramacion2.Exception.InsufficientBalanceException;
import org.example.proyectofinalprogramacion2.Exception.InactiveCardException;

import java.util.ArrayList;
import java.util.List;

public class VirtualCard {
    private String number;
    private double balance;
    private CardStatus status;
    private List<Transaction> transactions;

    public VirtualCard(String number, double initialBalance) {
        this.number = number;
        this.balance = initialBalance;
        this.status = CardStatus.ACTIVE;
        this.transactions = new ArrayList<>();
    }

    public void validateBalance(double amount) {
        if (status != CardStatus.ACTIVE) throw new InactiveCardException("Card is not active");
        if (balance < amount) throw new InsufficientBalanceException("Insufficient balance: " + balance);
    }

    public void debit(double amount, String description) {
        validateBalance(amount);
        balance -= amount;
        transactions.add(new Transaction(TransactionType.DEBIT, amount, description));
    }

    public void recharge(double amount) {
        balance += amount;
        transactions.add(new Transaction(TransactionType.RECHARGE, amount, "Recharge"));
    }

    public double getBalance() { return balance; }
    public List<Transaction> getTransactions() { return transactions; }
    public CardStatus getStatus() { return status; }
}