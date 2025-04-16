package utils;

import model.Transactions;
import java.util.ArrayList;
import java.util.List;

public class TransactionStore
{
    private static final List<Transactions> transactions = new ArrayList<>();

    public static void addTransaction(Transactions transaction)
    {
        transactions.add(transaction);
    }

    public static List<Transactions> getTransactions()
    {
        return transactions;
    }
}
