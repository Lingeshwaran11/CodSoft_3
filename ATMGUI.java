package codSoftJava;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class BankAccount {
    private double balance;

    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public boolean withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            return true; // Withdrawal successful
        } else {
            return false; // Insufficient funds
        }
    }
}

class ATM {
    private BankAccount bankAccount;

    public ATM(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    public void deposit(double amount) {
        bankAccount.deposit(amount);
    }

    public boolean withdraw(double amount) {
        return bankAccount.withdraw(amount);
    }

    public double checkBalance() {
        return bankAccount.getBalance();
    }
}

public class ATMGUI {
    private JFrame frame;
    private JTextField amountField;
    private JTextArea displayArea;
    private ATM atm;

    public ATMGUI(ATM atm) {
        this.atm = atm;
        initialize();
    }

    private void initialize() {
        frame = new JFrame("ATM Machine");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JLabel amountLabel = new JLabel("Enter Amount:");
        amountLabel.setBounds(20, 20, 150, 20);
        frame.add(amountLabel);

        amountField = new JTextField();
        amountField.setBounds(180, 20, 150, 20);
        frame.add(amountField);

        JButton depositButton = new JButton("Deposit");
        depositButton.setBounds(20, 60, 100, 30);
        frame.add(depositButton);

        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.setBounds(140, 60, 100, 30);
        frame.add(withdrawButton);

        JButton balanceButton = new JButton("Check Balance");
        balanceButton.setBounds(260, 60, 120, 30);
        frame.add(balanceButton);

        displayArea = new JTextArea();
        displayArea.setBounds(20, 100, 360, 150);
        displayArea.setEditable(false);
        frame.add(displayArea);

        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performDeposit();
            }
        });

        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performWithdrawal();
            }
        });

        balanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayBalance();
            }
        });

        frame.setVisible(true);
    }

    private void performDeposit() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            atm.deposit(amount);
            displayMessage("Deposit successful. New balance: " + atm.checkBalance());
        } catch (NumberFormatException ex) {
            displayMessage("Invalid amount. Please enter a valid number.");
        }
    }

    private void performWithdrawal() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            if (atm.withdraw(amount)) {
                displayMessage("Withdrawal successful. New balance: " + atm.checkBalance());
            } else {
                displayMessage("Insufficient funds.");
            }
        } catch (NumberFormatException ex) {
            displayMessage("Invalid amount. Please enter a valid number.");
        }
    }

    private void displayBalance() {
        displayMessage("Current balance: " + atm.checkBalance());
    }

    private void displayMessage(String message) {
        displayArea.setText(message);
    }

    public static void main(String[] args) {
        BankAccount bankAccount = new BankAccount(1000.0); // Initial balance
        ATM atm = new ATM(bankAccount);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ATMGUI(atm);
            }
        });
    }
}

