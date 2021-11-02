package uk.co.falcona.fileprovider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        File mydir = getDir("mydirectory", Context.MODE_PRIVATE); //Creating an internal dir;
        File fileWithinMyDir = new File(mydir, "myAwesomeFile");
        boolean d=fileWithinMyDir.exists();
        boolean e=fileWithinMyDir.exists();

            fileWithinMyDir.mkdirs();
            mydir.mkdirs();

        Log.e("datatta",fileWithinMyDir.getPath()+""+d);
        Toast.makeText(this, fileWithinMyDir.getPath()+""+d, Toast.LENGTH_SHORT).show();
    }
}