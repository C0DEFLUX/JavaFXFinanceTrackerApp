package utils;

import model.Transactions;

public class SelectedTransaction {
    private static Transactions transaction;

    public static Transactions getTransaction() {
        return transaction;
    }

    public static void setTransaction(Transactions transaction) {
        SelectedTransaction.transaction = transaction;
    }
}

