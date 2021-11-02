package com.app.bluetoothpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private BluetoothAdapter BA;
    private Set<BluetoothDevice> pairedDeveices;
    private ListView listView;
    List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        clickListener();
    }

    private void clickListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                unpairDevice(list.get(position));
                Toast.makeText(MainActivity.this, ""+list.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void init() {
       list = new ArrayList<>();
        BA = BluetoothAdapter.getDefaultAdapter();
        listView = findViewById(R.id.listView);
    }

    public void on(View v) {

        if (!BA.isEnabled()) {
            Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnOn, 0);
            Toast.makeText(getApplicationContext(), "Turned on", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "Already on", Toast.LENGTH_LONG).show();
        }
    }

    public void off(View v) {
        BA.disable();
        Toast.makeText(this, "Off Bluetooth", Toast.LENGTH_SHORT).show();
    }

    public void visible(View view) {
        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        startActivityForResult(intent, 0);
        Toast.makeText(this, "Visible", Toast.LENGTH_SHORT).show();
    }

    public void list(View view) {
        pairedDeveices = BA.getBondedDevices();
        for (BluetoothDevice bt : pairedDeveices) {
            Log.d("blueTooth", bt + "_");
            list.add(bt.getName());
        }
        if (list.size() > 0) {
            ArrayAdapter arrayAdapter = new ArrayAdapter<>(MainActivity.this, R.layout.support_simple_spinner_dropdown_item, list);
            listView.setAdapter(arrayAdapter);
            Toast.makeText(this, "list of paired deveices", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "No paired device yet!", Toast.LENGTH_SHORT).show();
        }

    }

    private void pairDevice(BluetoothDevice device) {
        try {
            Log.d("pairDevice()", "Start Pairing...");
            Method m = device.getClass().getMethod("createBond", (Class[]) null);
            m.invoke(device, (Object[]) null);
            Log.d("pairDevice()", "Pairing finished.");
        } catch (Exception e) {
            Log.e("pairDevice()", e.getMessage());
        }
    }

    public void pairing(View view) {
        BluetoothDevice device = BA.getRemoteDevice("C0:EE:FB:D5:1F:EF");
        pairDevice(device);
    }
    private void unpairDevice(BluetoothDevice device) {
        try {
            Log.d("unpairDevice()", "Start Un-Pairing...");
            Method m = device.getClass().getMethod("removeBond", (Class[]) null);
            m.invoke(device, (Object[]) null);
            Log.d("unpairDevice()", "Un-Pairing finished.");
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }
}