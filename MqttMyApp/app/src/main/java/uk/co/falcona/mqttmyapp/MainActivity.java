package uk.co.falcona.mqttmyapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    private NavController navController;
    private Toolbar toolbar;
    private NavHostFragment hostFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar=findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        hostFragment= (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        navController=hostFragment.getNavController();
//        NavigationUI.setupActionBarWithNavController(this,navController);
        NavigationUI.setupWithNavController(toolbar,navController);
    }

//    @Override
//    public boolean onSupportNavigateUp() {
//        return navController.navigateUp()
//                || super.onSupportNavigateUp();
//    }
}