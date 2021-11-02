package telegram.free.peertopeerapplication;

import android.app.Application;

import io.paperdb.Paper;

public class PeerToPeerApllication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Paper.init(getApplicationContext());
    }
}
