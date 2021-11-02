package com.zebra.diabledarkmode;

import android.util.Log;

import com.zebra.diabledarkmode.db.DbHelper;

import javax.inject.Inject;

public class NetworkTester {

    @Inject
    public NetworkTester() {
    }

    @Inject
    public void  insertData(DbHelper dbHelper)
    {
       dbHelper.insertData();
    }

    public void attachToPiston(Piston piston)
    {
        Log.e("TAG","pisten attch to NetworkTester");
    }
}
