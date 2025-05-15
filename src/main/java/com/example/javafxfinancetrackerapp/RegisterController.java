package com.example.javafxfinancetrackerapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import utils.DBUtil;
import utils.SceneSwitcher;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static utils.Alerts.showAlert;

public class RegisterController
{
    @FXML
    private PasswordField passwordRegField;

    @FXML
    private TextField usernameRegField;

    public void register(ActionEvent event) throws SQLException
    {
        //Simple validation for empty input fields
        //Possible to add more validations to make the password more secure
        if (usernameRegField.getText().isEmpty() || passwordRegField.getText().isEmpty())
        {
            showAlert("Password and Username cannot be empty");
            return;
        }

        //Create SQL query
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";

        try (Connection conn = DBUtil.connect();
             PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setString(1, usernameRegField.getText());
            stmt.setString(2, passwordRegField.getText());
            stmt.executeUpdate();

        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void showLoginView(ActionEvent event)
    {
        try
        {
            SceneSwitcher.switchScene(
                    (javafx.scene.Node) event.getSource(),
                    "login-view.fxml",
                    "Finance Tracker - Login"
            );

        } catch (IOException e)
        {
            e.printStackTrace();
            showAlert("Failed to switch scene.");
        }
    }

}
