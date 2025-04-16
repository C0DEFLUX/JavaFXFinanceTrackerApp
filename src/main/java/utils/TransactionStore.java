package utils;

import model.Transactions;
import java.util.ArrayList;
import java.util.List;

public class TransactionStore
{
    private static final List<Transactions> transactions = new ArrayList<>();

    //Add a transaction to the list
    public static void addTransaction(Transactions transaction)
    {
        transactions.add(transaction);
    }

    //Retrieve a transaction
    public static List<Transactions> getTransactions()
    {
        return transactions;
    }
}
