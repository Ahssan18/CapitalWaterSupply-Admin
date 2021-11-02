package telegram.free.pixabaypagingproject.Profile;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import telegram.free.pixabaypagingproject.Profile.Models.Datum;

public class DataFactory extends DataSource.Factory {

    public MutableLiveData<PageKeyedDataSource<Integer, Datum>> getMutableLiveData() {
        return mutableLiveData;
    }

    public MutableLiveData<PageKeyedDataSource<Integer, Datum>> mutableLiveData=new MutableLiveData<>();
    @Override
    public DataSource create() {
        DataResource dataSource=new DataResource();
        mutableLiveData.postValue(dataSource);
        return dataSource;
    }
}
