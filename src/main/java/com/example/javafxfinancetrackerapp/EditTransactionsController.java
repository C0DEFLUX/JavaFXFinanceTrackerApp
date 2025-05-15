package com.example.javafxfinancetrackerapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import model.Transactions;
import utils.SceneSwitcher;
import utils.SelectedTransaction;

import java.io.IOException;

import static utils.Alerts.showAlert;

public class EditTransactionsController {

    @FXML private TextField editAmountField;
    @FXML private TextField editDescField;
    @FXML private ComboBox<String> typeBox;
    @FXML private Button saveEditButton;

    public void initialize()
    {
        typeBox.getItems().addAll("Income", "Expense");

        Transactions transaction = SelectedTransaction.getTransaction();

        if (transaction != null)
        {

            editAmountField.setText(String.valueOf(transaction.getAmount()));
            editDescField.setText(transaction.getDescription());
            typeBox.setValue(transaction.getType());
        }

        saveEditButton.setOnAction(event ->
        {
            //Check for if there is a transaction
            if (transaction == null)
            {
                showAlert("No transaction selected.");
                return;
            }

            try
            {
                //Retrieve the date from fields
                double amount = Double.parseDouble(editAmountField.getText());
                String description = editDescField.getText();
                String type = typeBox.getValue();

                //Simple validation
                if (description.isEmpty() || type == null || amount < 0) {
                    showAlert("Please fill out all fields.");
                    return;
                }

                //Set the new values
                transaction.setAmount(amount);
                transaction.setDescription(description);
                transaction.setType(type);

                //Add a success alert
                showAlert("Transaction updated!");

                //Switch back to dashboard
                try
                {
                    SceneSwitcher.switchScene(
                            (javafx.scene.Node) event.getSource(),
                            "dashboard-view.fxml",
                            "Dashboard"
                    );

                } catch (IOException e)
                {
                    e.printStackTrace();
                    showAlert("Failed to switch scene.");
                }

            } catch (NumberFormatException e) {
                showAlert("Amount must be a valid number.");
            }
        });

    }



    public void handleBack(ActionEvent event) throws IOException
    {
        SceneSwitcher.switchScene((javafx.scene.Node) event.getSource(), "dashboard-view.fxml", "Dashboard");
    }

}
