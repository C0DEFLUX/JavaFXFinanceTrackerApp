package com.example.javafxfinancetrackerapp;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.event.ActionEvent;
import model.Transactions;
import utils.SceneSwitcher;
import utils.TransactionStore;

import java.io.IOException;

public class DashboardController {

    @FXML
    public Label totalLabel;
    @FXML
    private Label incomeLabel;
    @FXML
    private Label expenseLabel;

    @FXML
    public void initialize()
    {
        double totalIncome = 0;
        double totalExpenses = 0;

        //Loop to calculate the total income and expenses by checking the transition type
        for (Transactions t : TransactionStore.getTransactions())
        {
            if ("Income".equalsIgnoreCase(t.getType()))
            {
                totalIncome += t.getAmount();
            } else {
                totalExpenses += t.getAmount();
            }
        }

        //Add the values to labels
        incomeLabel.setText(String.format("Total Income: $%.2f", totalIncome));
        expenseLabel.setText(String.format("Total Expenses: $%.2f", totalExpenses));
        totalLabel.setText(String.format("Total Transactions: $%.2f", totalIncome - totalExpenses));
    }

    public void handleAddTransaction(ActionEvent event) throws IOException
    {
        SceneSwitcher.switchScene((javafx.scene.Node) event.getSource(), "transaction-add-view.fxml", "Add Transaction");
    }

    public void handleViewTransactions(ActionEvent event) throws IOException
    {
        SceneSwitcher.switchScene((javafx.scene.Node) event.getSource(), "transaction-list-view.fxml", "Transactions");
    }

    public void handleLogout(ActionEvent event) throws IOException
    {
        SceneSwitcher.switchScene((javafx.scene.Node) event.getSource(), "login-view.fxml", "Login");
    }
}
