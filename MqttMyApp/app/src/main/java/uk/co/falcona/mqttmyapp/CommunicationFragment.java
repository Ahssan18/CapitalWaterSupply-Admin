package uk.co.falcona.mqttmyapp;

import android.os.Bundle;
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
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class CommunicationFragment extends Fragment {

    private MqttAndroidClient client;
    private View root;
    private NavController navController;
    String clientId, uri;
    private Button publish, subscribe, unsubscribe;
    private EditText ettopic, etmessage, topicsubUnsub;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_communication, container, false);
        navController = NavHostFragment.findNavController(this);

        init();
        connectionWithMqtt();
        clickListeners();
        getMessages();

        return root;
    }

    private void connectionWithMqtt() {
        try {
            MqttConnectOptions option = new MqttConnectOptions();
            option.setUserName("AhssanAkhtar");
            option.setPassword("aio_eBDa26w2tFsAxOCrJPxfF5ETdJEk".toCharArray());
            client.connect(option, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Toast.makeText(getActivity(), "Connection Succeded!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    NavDirections action = CommunicationFragmentDirections.actionCommunicationFragment3ToConnectionFragment();
                    NavHostFragment.findNavController(CommunicationFragment.this).navigate(action);
                    Toast.makeText(getActivity(), "Connection Failed!" + exception.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    private void init() {
        uri = getArguments().getString("uri");
        clientId = getArguments().getString("clientid");
        clientId = MqttClient.generateClientId();
        client = new MqttAndroidClient(getContext(), uri, clientId);
        publish = root.findViewById(R.id.button_Publish);
        subscribe = root.findViewById(R.id.btn_subscribe);
        unsubscribe = root.findViewById(R.id.btn_unsubscibe);
        ettopic = root.findViewById(R.id.et_topic);
        etmessage = root.findViewById(R.id.et_message);
        topicsubUnsub = root.findViewById(R.id.et_topic_sub_unsub);
    }

    private void getMessages() {
        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
                try {
                    Toast.makeText(getActivity(), "" + cause.getMessage(), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {

                Toast.makeText(getActivity(), "" + message.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });
    }

    private void clickListeners() {
        subscribe.setOnClickListener(v -> {
            String topic = topicsubUnsub.getText().toString();
            try {
                client.subscribe(topic, 2, null, new IMqttActionListener() {
                    @Override
                    public void onSuccess(IMqttToken asyncActionToken) {
                        Toast.makeText(requireActivity(), "Subscribe Succussfully!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                        Toast.makeText(requireActivity(), exception.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
            } catch (MqttException e) {
                e.printStackTrace();
            }
        });
        unsubscribe.setOnClickListener(v -> {
            String topic = topicsubUnsub.getText().toString();
            try {
                client.unsubscribe(topic, this, new IMqttActionListener() {
                    @Override
                    public void onSuccess(IMqttToken asyncActionToken) {
                        Toast.makeText(requireActivity(), "UnSubscribe Successfully!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                        Toast.makeText(requireActivity(), exception.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
            } catch (MqttException e) {
                e.printStackTrace();
            }
        });
        publish.setOnClickListener(v -> {
            String topic = ettopic.getText().toString();
            String message = etmessage.getText().toString();
            MqttMessage mqttMessage = new MqttMessage(message.getBytes());
            try {
                client.publish(topic, mqttMessage, this, new IMqttActionListener() {
                    @Override
                    public void onSuccess(IMqttToken asyncActionToken) {
                        Toast.makeText(requireActivity(), "Publish Successfully", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                        try {
                            Toast.makeText(requireActivity(), "" + exception.getMessage(), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
            } catch (MqttException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            client.disconnect(requireContext(), new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    /*NavDirections action = CommunicationFragmentDirections.actionCommunicationFragment3ToConnectionFragment();
                    NavHostFragment.findNavController(CommunicationFragment.this).navigate(action);*/
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Toast.makeText(getActivity(), "Discconnect Failed!" + exception.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

}