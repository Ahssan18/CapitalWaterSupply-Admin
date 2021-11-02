package telegram.free.pixabaypagingproject.Profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import android.os.Bundle;
import android.util.Log;

import telegram.free.pixabaypagingproject.Profile.Models.Datum;
import telegram.free.pixabaypagingproject.R;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ProfileViewModel profileViewModel;
    AdapterProfiles adapterProfiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        getData();
    }

    private void getData() {
        profileViewModel.liveData.observe(this, new Observer<PagedList<Datum>>() {
            @Override
            public void onChanged(PagedList<Datum> data) {
                Log.e("data___",data.size()+"_");
                adapterProfiles.submitList(data);
            }
        });
        recyclerView.setAdapter(adapterProfiles);
    }

    private void init() {
        adapterProfiles=new AdapterProfiles();
        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setHasFixedSize(true);
        profileViewModel= ViewModelProviders.of(this).get(ProfileViewModel.class);
    }
}