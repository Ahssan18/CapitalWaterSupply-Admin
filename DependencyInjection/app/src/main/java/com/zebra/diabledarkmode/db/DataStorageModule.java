package com.zebra.diabledarkmode.db;


import com.zebra.diabledarkmode.transaction.Transaction;

import dagger.Module;
import dagger.Provides;

@Module
public class DataStorageModule {

    @Provides
    public DbHelper provideDbHelper(Transaction transaction)
    {
        DbHelper dbHelper=new DbHelper(transaction);
        return dbHelper;
    }

    @Provides
    public Transaction getTransaction()
    {
        return new Transaction(System.currentTimeMillis());
    }
}
