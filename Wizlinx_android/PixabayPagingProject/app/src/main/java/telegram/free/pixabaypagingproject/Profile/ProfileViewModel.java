package telegram.free.pixabaypagingproject.Profile;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.RecyclerView;

import telegram.free.pixabaypagingproject.Profile.Models.Datum;

public class ProfileViewModel extends androidx.lifecycle.ViewModel{
    LiveData<PagedList< Datum>> liveData;
    public ProfileViewModel() {
        DataFactory dataFactory=new DataFactory();
        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPageSize(10).build();

        liveData = (new LivePagedListBuilder(dataFactory, pagedListConfig))
                .build();
    }
}
