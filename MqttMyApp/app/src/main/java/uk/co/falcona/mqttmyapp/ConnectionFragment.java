package uk.co.falcona.mqttmyapp;


import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import io.moquette.BrokerConstants;
import io.moquette.broker.Server;
import io.moquette.broker.config.IConfig;
import io.moquette.broker.config.MemoryConfig;

public class ConnectionFragment extends Fragment {
    private static MqttAndroidClient client;
    private View root;
    private Button btnNext;
    private io.moquette.broker.Server server;
    private NavController navController;
    private EditText serviceUri,clientid;
    public String TAG="ConnectionFragment";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root= inflater.inflate(R.layout.fragment_connection, container, false);
        init();
        startBroker();
        connectMqtt();
        getMessage();
        clickListener();
        return root;
    }

    private void getMessage() {

        try {
            client.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    try {
                        Log.e("MqttConnecter", "MqttConnecter connectionLost -> " + cause.getMessage());
                        Toast.makeText(root.getContext(), "" + cause.getMessage(), Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    Log.e("MqttConnecter", "MqttConnecter messageArrived -> " + message.toString());
                    Toast.makeText(getActivity(), " messageArrived "+message.toString(), Toast.LENGTH_SHORT).show();
//                Toast.makeText(context, "" + message.toString(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    private void connectMqtt() {
        try {
            client.connect(root.getContext(), new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Toast.makeText(root.getContext(), "connected", Toast.LENGTH_SHORT).show();

                    Log.e("MqttConnecter", "MqttConnecter Connection -> " + "connection Success");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                client.subscribe("topic/dummy", 2, null, new IMqttActionListener() {
                                    @Override
                                    public void onSuccess(IMqttToken asyncActionToken) {
                                        Log.e("MqttConnecter", "MqttConnecter subscribe onSuccess -> " + "subscribe success");
                                        Toast.makeText(root.getContext(), "subscribed topic/dummy", Toast.LENGTH_SHORT).show();
//                                        Toast.makeText(context, "Subscribe Succussfully!", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                                        Log.e("MqttConnecter", "MqttConnecter subscribe onFailure -> " + exception.getMessage());

//                                        Toast.makeText(context, exception.getMessage(), Toast.LENGTH_SHORT).show();

                                    }
                                });
                            } catch (MqttException e) {
                                e.printStackTrace();
                            }
                        }
                    },3000);

                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {

                }
            });
        }catch (Exception e) {
            e.printStackTrace();
        }
    }


                private void startBroker() {
        Log.e("startBroker", "Intiate startBroker");
        server = new Server();
        try {
            IConfig memoryConfig = new MemoryConfig(new Properties());
            memoryConfig.setProperty(BrokerConstants.PERSISTENT_STORE_PROPERTY_NAME, Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + BrokerConstants.DEFAULT_MOQUETTE_STORE_H2_DB_FILENAME);
            server.startServer(memoryConfig);

            // server.startServer();//is not working due to DEFAULT_MOQUETTE_STORE_MAP_DB_FILENAME;
            Log.e("startBroker", "Server Started");
        } catch (IOException e) {
            Log.e("startBroker", "IOException " + e.getMessage());

            e.printStackTrace();
        } catch (Exception e) {
            Log.e("startBroker", "Exception " + e.getMessage());

            e.printStackTrace();
        }

    }
    private void clickListener() {

        serviceUri.setText("tcp://broker.hivemq.com:1883");
        btnNext.setOnClickListener(v -> {
            String uri,id="";
            id=clientid.getText().toString();
            uri=serviceUri.getText().toString();
            NavDirections action =ConnectionFragmentDirections.actionConnectionFragmentToCommunicationFragment3(uri,id);
            navController.navigate(action);
        });
    }

    private void init() {
        String clientId = MqttClient.generateClientId();
        client = new MqttAndroidClient(root.getContext(), "tcp://broker.hivemq.com:1883", clientId);
        serviceUri=root.findViewById(R.id.et_topic);
        clientid=root.findViewById(R.id.et_message);
        navController= NavHostFragment.findNavController(this);
        btnNext=root.findViewById(R.id.button);
    }
}