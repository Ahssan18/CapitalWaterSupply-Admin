package telegram.free.peertopeerapplication;

import android.content.Context;
import android.net.wifi.WpsInfo;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DevicesAdapter extends RecyclerView.Adapter<DevicesAdapter.CustomView> {
    private Context context;
    private List<WifiP2pDevice> list;
    Click click;

    public DevicesAdapter(Context context, List<WifiP2pDevice> list,Click click) {
        this.context = context;
        this.list = list;
        this.click=click;
    }

    @NonNull
    @Override
    public CustomView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.custom_device_info,parent,false);
        return new CustomView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomView holder, int position) {
        holder.tvdvname.setText(list.get(position).deviceName);
        holder.tvdvname.append(" "+list.get(position).isGroupOwner());
        holder.dev_serial.setText(list.get(position).deviceAddress);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WifiP2pDevice device = list.get(position);
                WifiP2pConfig config = new WifiP2pConfig();
                config.deviceAddress = device.deviceAddress;
                config.wps.setup = WpsInfo.PBC;
                click.getConfig(config);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CustomView extends RecyclerView.ViewHolder{

        private TextView tvdvname,dev_serial;
        private LinearLayout linearLayout;
        public CustomView(@NonNull View itemView) {
            super(itemView);
            tvdvname=itemView.findViewById(R.id.name);
            dev_serial=(TextView) itemView.findViewById(R.id.serial);
            linearLayout=(LinearLayout) itemView.findViewById(R.id.linear_custom);
        }
    }
}
