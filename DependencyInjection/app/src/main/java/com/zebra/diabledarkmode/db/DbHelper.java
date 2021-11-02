package com.zebra.diabledarkmode.db;

import android.util.Log;

import com.zebra.diabledarkmode.transaction.Transaction;

public class DbHelper {
    Transaction transaction;
    public DbHelper(Transaction transaction) {
        Log.e("TAG","DbHelper call");
        this.transaction=transaction;
    }

    public void insertData()
    {
        Log.e("TAG","Data inserted successfully");
    }
}
