package com.example.javafxfinancetrackerapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import utils.DBUtil;
import utils.SceneSwitcher;
import utils.Session;

import java.io.IOException;
import java.sql.*;

import static utils.Alerts.showAlert;

public class LoginController
{
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

    public boolean handleLogin(ActionEvent event) throws Exception
    {
        if(usernameField.getText().isEmpty() || passwordField.getText().isEmpty())
        {
            showAlert("Username and Password are Required");
            return false;
        }

        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection conn = DBUtil.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usernameField.getText());
            stmt.setString(2, passwordField.getText());

            ResultSet rs = stmt.executeQuery();

            if(!rs.next())
            {
                showAlert("Invalid Username or Password");
                return false;
            }

            //Save user data into a session
            int userId = rs.getInt("id");
            String username = rs.getString("username");
            Session.login(userId, username);

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

            return true;


        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void showRegisterView(ActionEvent event)
    {
        try
        {
            SceneSwitcher.switchScene(
                    (javafx.scene.Node) event.getSource(),
                    "register-view.fxml",
                    "Finance Tracker - Register"
            );

        } catch (IOException e)
        {
            e.printStackTrace();
            showAlert("Failed to switch scene.");
        }
    }



}
