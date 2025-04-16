package com.example.javafxfinancetrackerapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import utils.SceneSwitcher;

public class LoginController
{
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

    public void handleLogin(ActionEvent event) throws Exception
    {
        if ("admin".equals(usernameField.getText()) && "admin".equals(passwordField.getText()))
        {
            SceneSwitcher.switchScene((javafx.scene.Node) event.getSource(), "dashboard-view.fxml", "Dashboard");
        } else
        {
            showAlert("Invalid username or password");
        }
    }

    private void showAlert(String msg)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
