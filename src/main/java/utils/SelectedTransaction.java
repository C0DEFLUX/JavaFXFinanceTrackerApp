package utils;

import model.Transactions;

public class SelectedTransaction {
    private static Transactions transaction;

    //Getter for stored selected transaction
    public static Transactions getTransaction()
    {
        return transaction;
    }

    //Setter for the selected transaction
    public static void setTransaction(Transactions transaction)
    {
        SelectedTransaction.transaction = transaction;
    }
}

