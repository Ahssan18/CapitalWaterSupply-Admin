package telegram.free.peertopeerapplication;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity implements WifiP2pManager.ConnectionInfoListener, Click {
    WifiP2pManager manager;
    WifiP2pManager.Channel channel;
    BroadcastReceiver receiver;
    IntentFilter intentFilter, intentfilter1;
    DataReceviver dataReceviver;
    private RecyclerView recyclerView;
    private DevicesAdapter adapter;

    @Override
    public void onConnectionInfoAvailable(WifiP2pInfo info) {
        String groupOwnerAddress = info.groupOwnerAddress.getHostAddress();
        if (info.groupFormed && info.isGroupOwner) {
            Toast.makeText(this, "this is the group owner", Toast.LENGTH_SHORT).show();
        } else if (info.groupFormed) {
            Toast.makeText(this, "this is the cliend", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void getConfig(WifiP2pConfig config) {
        Log.d("connection_Elements",channel+"\n"+config);
        manager.connect(channel, config, new WifiP2pManager.ActionListener() {

            @Override
            public void onSuccess() {
                Toast.makeText(MainActivity.this, "onSuccess()", Toast.LENGTH_SHORT).show();
                // WiFiDirectBroadcastReceiver notifies us. Ignore for now.
            }

            @Override
            public void onFailure(int reason) {
                Toast.makeText(MainActivity.this, "Connect failed. Retry."+reason,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    public class DataReceviver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            setAdapter(Paper.book().read("list"));
        }
    }

    private void setAdapter(List<WifiP2pDevice> user) {
        adapter = new DevicesAdapter(this, user, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    WifiP2pManager.ChannelListener channelListener = new WifiP2pManager.ChannelListener() {
        @Override
        public void onChannelDisconnected() {
            Toast.makeText(MainActivity.this, "Disconnected", Toast.LENGTH_SHORT).show();
        }
    };

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
            manager.discoverPeers(channel, new WifiP2pManager.ActionListener() {
                @Override
                public void onSuccess() {
                    Toast.makeText(MainActivity.this, "Peer Discovered", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(int reasonCode) {
                    Toast.makeText(MainActivity.this, "onFailure Discovered"+reasonCode, Toast.LENGTH_SHORT).show();

                }
            });


    }

    private void init() {
        recyclerView = findViewById(R.id.recycleview);
        dataReceviver = new DataReceviver();
        manager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        channel = manager.initialize(this, getMainLooper(), channelListener);
        receiver = new WiFiDirectBroadcastReceiver(manager, channel, this);

        intentFilter = new IntentFilter();
        intentfilter1 = new IntentFilter();
        intentfilter1.addAction("com.list");
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, intentFilter);
        registerReceiver(dataReceviver, intentfilter1);
    }

    /* unregister the broadcast receiver */
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
        unregisterReceiver(dataReceviver);
    }
}