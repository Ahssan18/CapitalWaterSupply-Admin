package com.zebra.diabledarkmode.transaction;

import androidx.annotation.NonNull;

public class Transaction {

    @NonNull
    @Override
    public String toString() {
        return "Transaction{" +
                "time=" + time +
                '}';
    }

    long time;

    public Transaction(long time) {
        this.time = time;
    }
}
