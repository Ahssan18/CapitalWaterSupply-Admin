package telegram.free.websocketimplementation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.pubnub.api.PNConfiguration;
import com.pubnub.api.PubNub;
import com.pubnub.api.callbacks.PNCallback;
import com.pubnub.api.callbacks.SubscribeCallback;
import com.pubnub.api.models.consumer.PNPublishResult;
import com.pubnub.api.models.consumer.PNStatus;
import com.pubnub.api.models.consumer.pubsub.PNMessageResult;
import com.pubnub.api.models.consumer.pubsub.PNPresenceEventResult;

import java.util.Arrays;

public class MainActivity2 extends AppCompatActivity {

    private PubNub pubnub;
    private TextView textView;
    private Button btnSubmit;
    private EditText etInput;
    private static String TAG="MainActivity2";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        init();
        initPubNub();
        clickListener();
    }

    private void clickListener() {
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message=etInput.getText().toString();
                publishMessage(message);
            }
        });
    }

    private void init() {
        btnSubmit=findViewById(R.id.btn_submit);
        etInput=findViewById(R.id.et_message);
    }

    public void initPubNub(){
        PNConfiguration pnConfiguration = new PNConfiguration();
        pnConfiguration.setPublishKey("pub-c-965dc6de-e3c4-42b5-bf38-2a103b65d809"); // REPLACE with your pub key
        pnConfiguration.setSubscribeKey("sub-c-cbb26f22-6c6e-11eb-a8a4-8af6467359f5"); // REPLACE with your sub key
        pnConfiguration.setSecure(true);
        pubnub = new PubNub(pnConfiguration);
        // Listen to messages that arrive on the channel
        pubnub.addListener(new SubscribeCallback() {
            @Override
            public void status(PubNub pub, PNStatus status) {
            }
            @Override
            public void message(PubNub pub, final PNMessageResult message) {
                // Replace double quotes with a blank space

                final String msg = message.getMessage().toString().replace("\"", "");
                textView = findViewById(R.id.tv_message);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            // Display the message on the app
//                            if(!sharedpreferences.getString("name","").equals(modelData.getName()))
                            textView.setText(msg);
                        } catch (Exception e){
                            System.out.println("Error");
                            e.printStackTrace();
                        }
                    }
                });
            }
            @Override
            public void presence(PubNub pub, PNPresenceEventResult presence) {
            }
        });
        // Subscribe to the global channel
        pubnub.subscribe()
                .channels(Arrays.asList("global_channel"))
                .execute();
    }
    public void publishMessage(String animal_sound){
        // Publish message to the global chanel
        pubnub.publish()
                .message(animal_sound)
                .channel("global_channel")
                .async(new PNCallback<PNPublishResult>() {
                    @Override
                    public void onResponse(PNPublishResult result, PNStatus status) {
                        // status.isError() to see if error happened and print status code if error
                        if(status.isError()) {
                            System.out.println("pub status code: " + status.getStatusCode());
                        }
                    }
                });
    }
}