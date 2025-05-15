package com.example.javafxfinancetrackerapp;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import model.Transactions;
import utils.DBUtil;
import utils.SceneSwitcher;
import utils.Session;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static utils.Alerts.showAlert;

public class AddTransactionsController
{
    @FXML private ComboBox<String> typeBox;
    @FXML private TextField descField;
    @FXML private TextField amountField;

    @FXML
    public void initialize()
    {
        typeBox.getItems().addAll("Income", "Expense");
    }

    @FXML
    public void handleSubmit()
    {
        String type = typeBox.getValue();
        String description = descField.getText();
        String amountText = amountField.getText();


        //Simple verification for the inputs
        if (type == null || description.isEmpty() || amountText.isEmpty())
        {
            showAlert("Please fill in all fields.");
            return;
        }

        try
        {
            double amount = Double.parseDouble(amountText);
            int userId = Session.getUserId();

            //Insert into database
            String sql = "INSERT INTO transactions (type, description, amount, user_id) VALUES (?, ?, ?, ?)";

            try (Connection conn = DBUtil.connect();
                 PreparedStatement stmt = conn.prepareStatement(sql))
            {

                stmt.setString(1, type);
                stmt.setString(2, description);
                stmt.setDouble(3, amount);
                stmt.setInt(4, userId);

                stmt.executeUpdate();
            }

            showAlert("Transaction added!");

            //Clear form
            descField.clear();
            amountField.clear();
            typeBox.setValue(null);

        } catch (NumberFormatException e)
        {
            showAlert("Invalid amount entered.");
        } catch (SQLException e)
        {
            e.printStackTrace();
            showAlert("Failed to add transaction to the database.");
        }
    }

    public void handleBack(ActionEvent event) throws IOException
    {
        SceneSwitcher.switchScene((javafx.scene.Node) event.getSource(), "dashboard-view.fxml", "Dashboard");
    }
}
