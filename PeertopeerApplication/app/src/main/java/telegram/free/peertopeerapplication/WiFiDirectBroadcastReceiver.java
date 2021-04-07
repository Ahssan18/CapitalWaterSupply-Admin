package telegram.free.peertopeerapplication;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

@SuppressWarnings("ALL")
public class WiFiDirectBroadcastReceiver extends BroadcastReceiver {

    private WifiP2pManager manager;
    private WifiP2pManager.Channel channel;
    private Context ref;
    private MainActivity activity;
    WifiP2pManager.PeerListListener myPeerListListener = new WifiP2pManager.PeerListListener() {
        @Override
        public void onPeersAvailable(WifiP2pDeviceList peers) {
            List peer = new ArrayList();
            peer.addAll(peers.getDeviceList());
            Toast.makeText(ref, "onPeersAvailable" + peer, Toast.LENGTH_SHORT).show();
            Log.e("datatattat", peer.toString());
            Intent intent = new Intent();
            Paper.book().write("list", peer);
            intent.setAction("com.list");
            ref.sendBroadcast(intent);
        }
    };

    public WiFiDirectBroadcastReceiver(WifiP2pManager manager, WifiP2pManager.Channel channel, MainActivity activity) {
        super();
        this.manager = manager;
        this.channel = channel;
        this.activity = activity;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        ref = context;
        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
            int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
            if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
                // Wifi P2P is enabled
                Toast.makeText(context, "Wifi P2P is enabled", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Wi-Fi P2P is not enabled", Toast.LENGTH_SHORT).show();
                // Wi-Fi P2P is not enabled
            }
            // Check to see if Wi-Fi is enabled and notify appropriate activity
        } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {
            // Call WifiP2pManager.requestPeers() to get a list of current peers
            if (manager != null) {
                manager.requestPeers(channel, myPeerListListener);
            } else {
                Toast.makeText(context, "Manager is null", Toast.LENGTH_SHORT).show();
            }
        } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {
            Toast.makeText(context, "" + action, Toast.LENGTH_SHORT).show();

            // Respond to new connection or disconnections
        } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {
            Toast.makeText(context, "This device Changed Action", Toast.LENGTH_SHORT).show();

            // Respond to this device's wifi state changing
        }
    }
}
