package uk.co.falcona.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.TextView;

import uk.co.falcona.myapplication.Model.EmployeeViewModel;
import uk.co.falcona.myapplication.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    private ActivityMainBinding binding;
    public static String TAG=ActivityMainBinding.class.getName();
    private static EmployeeViewModel employeeViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        employeeViewModel=new ViewModelProvider(this).get(EmployeeViewModel.class);
        // Example of a call to a native method
        TextView tv = binding.sampleText;
        binding.etText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                employeeViewModel.setName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        employeeViewModel.getName().observe(this, tv::setText);
//        tv.setText(sumFromJNI(10,5)+""+stringFromJNI());



//        Log.e(TAG,"My name is : "+getNameFromJNI(e));
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    public native int sumFromJNI(int a,int b);

    public native int getAgeFromJNI(EmployeeViewModel e);

//    public native String getNameFromJNI(Employee e);

}