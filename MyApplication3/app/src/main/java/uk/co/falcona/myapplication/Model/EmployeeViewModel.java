package uk.co.falcona.myapplication.Model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class EmployeeViewModel extends ViewModel {
    private final MutableLiveData<String>  name=new MutableLiveData<>();

    public MutableLiveData<String> getName() {
        return name;
    }

    public void setName(String name2) {
        name.setValue(name2);
    }
    int age;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


}
