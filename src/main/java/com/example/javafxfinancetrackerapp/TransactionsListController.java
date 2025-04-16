package com.example.javafxfinancetrackerapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Transactions;
import javafx.scene.control.TableView;
import utils.SceneSwitcher;
import utils.SelectedTransaction;
import utils.TransactionStore;

import java.io.IOException;

public class TransactionsListController {

    @FXML
    private Button editBtn;
    @FXML
    private Button deleteBtn;
    @FXML
    private TableView<Transactions> transactionsTable;
    @FXML
    private TableColumn<Transactions, String> typeColumn;
    @FXML
    private TableColumn<Transactions, String> descColumn;
    @FXML
    private TableColumn<Transactions, Double> amountColumn;

    @FXML
    public void initialize() {

        //Connect values to tabled columns
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        descColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));

        transactionsTable.setItems(FXCollections.observableArrayList(TransactionStore.getTransactions()));

        editBtn.setOnAction(event -> {
            //Get selected table row
            Transactions selected = transactionsTable.getSelectionModel().getSelectedItem();

            if (selected != null)
            {
                //Store the single transaction selection
                SelectedTransaction.setTransaction(selected);
                try
                {
                    //Switch scene
                    SceneSwitcher.switchScene(
                            (javafx.scene.Node) event.getSource(),
                            "transaction-edit-view.fxml",
                            String.valueOf(transactionsTable.getScene())
                    );

                } catch (IOException e)
                {
                    throw new RuntimeException(e);
                }
            }
        });

        deleteBtn.setOnAction(event ->
        {
            //Get the selected table
            Transactions transaction = transactionsTable.getSelectionModel().getSelectedItem();

            if (transaction != null)
            {
                TransactionStore.getTransactions().remove(transaction);

                transactionsTable.setItems(FXCollections.observableArrayList(TransactionStore.getTransactions()));
            }
        });
    }

    public void handleBack(ActionEvent event) throws IOException
    {
        SceneSwitcher.switchScene((javafx.scene.Node) event.getSource(), "dashboard-view.fxml", "Dashboard");
    }
}
