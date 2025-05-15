package com.example.javafxfinancetrackerapp;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import model.Transactions;
import utils.SceneSwitcher;
import utils.TransactionStore;

import java.io.IOException;

import static utils.Alerts.showAlert;

public class AddTransactionsController {

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

        //Simple validation
        if (type == null || description.isEmpty() || amountText.isEmpty())
        {
            showAlert("Please fill in all fields.");
            return;
        }

        try
        {
            //Make it a double
            double amount = Double.parseDouble(amountText);

            //Pass the values to the list
            Transactions transaction = new Transactions(type, description, amount);
            //Add the values to the transaction list
            TransactionStore.addTransaction(transaction);

            showAlert("Transaction added!");

            //Clear input fields
            descField.clear();
            amountField.clear();
            typeBox.setValue(null);

        } catch (NumberFormatException e)
        {
            showAlert("Invalid amount entered.");
        }
    }

    public void handleBack(ActionEvent event) throws IOException
    {
        SceneSwitcher.switchScene((javafx.scene.Node) event.getSource(), "dashboard-view.fxml", "Dashboard");
    }
}

