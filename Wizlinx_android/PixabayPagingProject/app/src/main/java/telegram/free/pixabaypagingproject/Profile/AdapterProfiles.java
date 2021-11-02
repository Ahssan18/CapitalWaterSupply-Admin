package telegram.free.pixabaypagingproject.Profile;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import telegram.free.pixabaypagingproject.Profile.Models.Datum;
import telegram.free.pixabaypagingproject.R;

public class AdapterProfiles extends PagedListAdapter<Datum,AdapterProfiles.CustomView> {

    public static DiffUtil.ItemCallback<Datum> callback=new DiffUtil.ItemCallback<Datum>() {
        @Override
        public boolean areItemsTheSame(@NonNull Datum oldItem, @NonNull Datum newItem) {
            return oldItem.getId()==newItem.getId();
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull Datum oldItem, @NonNull Datum newItem) {
            return oldItem.equals(newItem);
        }
    };
    public AdapterProfiles() {
        super(callback);
    }


    @NonNull
    @Override
    public CustomView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.custom_design, parent, false);
        return new CustomView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomView holder, int position) {
        Datum datum=getItem(position);
        assert datum != null;
        holder.tvNamel.setText(datum.getTitle()+" "+datum.getFirstName());
        holder.tvEmail.setText(datum.getEmail());
        Picasso.get()
                .load(datum.getPicture())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .into(holder.iv_profile);
    }

    public class CustomView extends RecyclerView.ViewHolder{
        private ImageView iv_profile;
        private TextView tvEmail,tvNamel;

        public CustomView(@NonNull View itemView) {
            super(itemView);
            iv_profile=itemView.findViewById(R.id.iv_profile);
            tvEmail=itemView.findViewById(R.id.tv_email);
            tvNamel=itemView.findViewById(R.id.tv_name);
        }
    }
}
