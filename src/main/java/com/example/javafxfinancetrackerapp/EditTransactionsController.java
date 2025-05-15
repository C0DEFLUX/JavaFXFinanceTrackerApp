package com.example.javafxfinancetrackerapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Transactions;
import utils.DBUtil;
import utils.SceneSwitcher;
import utils.SelectedTransaction;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static utils.Alerts.showAlert;

public class EditTransactionsController {

    @FXML private TextField editAmountField;
    @FXML private TextField editDescField;
    @FXML private ComboBox<String> typeBox;
    @FXML private Button saveEditButton;

    private Transactions transaction;

    public void initialize()
    {
        typeBox.getItems().addAll("Income", "Expense");

        transaction = SelectedTransaction.getTransaction();

        if (transaction != null)
        {
            editAmountField.setText(String.valueOf(transaction.getAmount()));
            editDescField.setText(transaction.getDescription());
            typeBox.setValue(transaction.getType());
        }

        saveEditButton.setOnAction(event -> handleSaveEdit(event));
    }

    private void handleSaveEdit(ActionEvent event)
    {
        if (transaction == null)
        {
            showAlert("No transaction selected.");
            return;
        }

        try
        {
            double amount = Double.parseDouble(editAmountField.getText());
            String description = editDescField.getText();
            String type = typeBox.getValue();

            if (description.isEmpty() || type == null || amount < 0)
            {
                showAlert("Please fill out all fields correctly.");
                return;
            }

            String sql = "UPDATE transactions SET type = ?, description = ?, amount = ? WHERE id = ?";

            try (Connection conn = DBUtil.connect();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, type);
                stmt.setString(2, description);
                stmt.setDouble(3, amount);
                stmt.setInt(4, transaction.getId());

                stmt.executeUpdate();

                showAlert("Transaction updated successfully!");

                transaction.setAmount(amount);
                transaction.setDescription(description);
                transaction.setType(type);

                //Go back to dashboard
                SceneSwitcher.switchScene(
                        (javafx.scene.Node) event.getSource(),
                        "dashboard-view.fxml",
                        "Dashboard"
                );

            } catch (SQLException e) {
                showAlert("Failed to edit transaction.");
            }

        } catch (NumberFormatException e)
        {
            showAlert("Amount must be a valid number.");
        } catch (IOException e)
        {
            e.printStackTrace();
            showAlert("Failed to switch scene.");
        }
    }

    public void handleBack(ActionEvent event) throws IOException {
        SceneSwitcher.switchScene((javafx.scene.Node) event.getSource(), "dashboard-view.fxml", "Dashboard");
    }
}
