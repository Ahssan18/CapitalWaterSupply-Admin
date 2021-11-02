package com.zebra.diabledarkmode;

import android.util.Log;

import javax.inject.Inject;

public final class Piston {
    public int power;
    @Inject
    public Piston() {
    }

    @Inject
    public void attachToPiston(NetworkTester networkTester)
    {
        networkTester.attachToPiston(this);
    }
    void start()
    {
        Log.e("TAG","car is start successfully power = "+power);
    }

}
