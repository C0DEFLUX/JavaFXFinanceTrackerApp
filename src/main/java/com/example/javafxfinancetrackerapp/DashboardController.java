package com.example.javafxfinancetrackerapp;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import model.Transactions;
import utils.DBUtil;
import utils.SceneSwitcher;
import utils.Session;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DashboardController
{

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

        //Set transactions into the model
        List<Transactions> transactions = getTransactionsForUser(Session.getUserId());

        //Set the amount from the db into variables
        for (Transactions t : transactions)
        {
            if ("Income".equalsIgnoreCase(t.getType()))
            {
                totalIncome += t.getAmount();
            } else {
                totalExpenses += t.getAmount();
            }
        }

        //Set the date in the UI
        incomeLabel.setText(String.format("Total Income: $%.2f", totalIncome));
        expenseLabel.setText(String.format("Total Expenses: $%.2f", totalExpenses));
        totalLabel.setText(String.format("Balance: $%.2f", totalIncome - totalExpenses));
    }

    private List<Transactions> getTransactionsForUser(int userId)
    {
        List<Transactions> transactions = new ArrayList<>();
        //Select sql query
        String sql = "SELECT * FROM transactions WHERE user_id = ? ORDER BY id DESC";

        try (Connection conn = DBUtil.connect();
             PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next())
            {
                //Set the retrieved rows
                Transactions t = new Transactions(
                        rs.getInt("id"),
                        rs.getString("type"),
                        rs.getString("description"),
                        rs.getDouble("amount")
                );
                transactions.add(t);
            }

        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return transactions;
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

