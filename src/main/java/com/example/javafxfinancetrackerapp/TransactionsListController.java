package com.example.javafxfinancetrackerapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Transactions;
import utils.DBUtil;
import utils.SceneSwitcher;
import utils.SelectedTransaction;
import utils.Session;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static utils.Alerts.showAlert;

public class TransactionsListController {

    @FXML private Button editBtn;
    @FXML private Button deleteBtn;
    @FXML private TableView<Transactions> transactionsTable;
    @FXML private TableColumn<Transactions, String> typeColumn;
    @FXML private TableColumn<Transactions, String> descColumn;
    @FXML private TableColumn<Transactions, Double> amountColumn;

    private ObservableList<Transactions> transactionList;

    @FXML
    public void initialize()
    {
        //Set up table columns
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        descColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));

        //Load data from DB
        transactionList = FXCollections.observableArrayList(getTransactionsFromDB());
        transactionsTable.setItems(transactionList);

        // Edit button logic
        editBtn.setOnAction(event ->
        {
            Transactions selected = transactionsTable.getSelectionModel().getSelectedItem();
            if (selected != null)
            {
                SelectedTransaction.setTransaction(selected);
                try
                {
                    SceneSwitcher.switchScene(
                            (javafx.scene.Node) event.getSource(),
                            "transaction-edit-view.fxml",
                            "Edit Transaction"
                    );
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        });

        // Delete button logic
        deleteBtn.setOnAction(event ->
        {
            Transactions selected = transactionsTable.getSelectionModel().getSelectedItem();
            if (selected != null)
            {
                deleteTransactionFromDB(selected.getId());
                transactionList.remove(selected);
            }
        });
    }

    //Method for setting the rows in the table from DB
    private List<Transactions> getTransactionsFromDB()
    {
        List<Transactions> list = new ArrayList<>();
        //Sql query
        String sql = "SELECT * FROM transactions WHERE user_id = ?";

        try (Connection conn = DBUtil.connect();
             PreparedStatement stmt = conn.prepareStatement(sql))
        {

            stmt.setInt(1, Session.getUserId());
            ResultSet rs = stmt.executeQuery();

            //Add each row to list and the return it
            while (rs.next())
            {
                Transactions t = new Transactions(
                        rs.getInt("id"),
                        rs.getString("type"),
                        rs.getString("description"),
                        rs.getDouble("amount")
                );
                list.add(t);
            }

        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return list;
    }

    //Method for deleting transactions
    private void deleteTransactionFromDB(int id)
    {
        //Sql query
        String sql = "DELETE FROM transactions WHERE id = ?";

        try (Connection conn = DBUtil.connect();
             PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e)
        {
            e.printStackTrace();
           showAlert("Failed to Delete");
        }
    }

    public void handleBack(ActionEvent event) throws IOException
    {
        SceneSwitcher.switchScene((javafx.scene.Node) event.getSource(), "dashboard-view.fxml", "Dashboard");
    }
}
