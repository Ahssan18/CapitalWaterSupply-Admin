package telegram.free.multilocale;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ImageView ivchangeLanguage;
    private AlertDialog alertDialog;
    private String lang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        clickListener();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void clickListener() {
        ivchangeLanguage.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            ViewGroup viewGroup = findViewById(android.R.id.content);
            View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.cutom_language_popup, viewGroup, false);
            builder.setView(dialogView);
            RadioButton rb, rh, ra;
            rb = dialogView.findViewById(R.id.radioenglish);
            rh = dialogView.findViewById(R.id.radiohindi);
            ra = dialogView.findViewById(R.id.radioarabic);
            AlertDialog alertDialog = builder.create();
            RadioGroup radioGroup = dialogView.findViewById(R.id.radio_group);
            dialogView.findViewById(R.id.tv_choose).setOnClickListener(v1 -> {
                alertDialog.dismiss();
                int selectedId = radioGroup.getCheckedRadioButtonId();
                Log.e("datatta", lang.toString());

                if (lang.contains("en")) {
                    rh.setChecked(false);
                    radioGroup.check(R.id.radioenglish);
                    rb.setChecked(true);
                    ra.setChecked(false);
                } else if (lang.contains("hi")) {
                    radioGroup.check(R.id.radiohindi);
                    rh.setChecked(true);
                    rb.setChecked(false);
                    ra.setChecked(false);
                } else if (lang.contains("ar")) {
                    radioGroup.check(R.id.radioarabic);
                    ra.setChecked(true);
                    rh.setChecked(false);
                    rb.setChecked(false);
                }
                setDefaultLocale(selectedId);
                RadioButton radioSexButton = (RadioButton) dialogView.findViewById(selectedId);
//                Toast.makeText(MainActivity.this, radioSexButton.getText(), Toast.LENGTH_SHORT).show();
            });
            alertDialog.show();
        });
    }

    private void setDefaultLocale(int selectedId) {
        String languagePref = "en";

        switch (selectedId) {
            case R.id.radioenglish:
                languagePref = "en";
                break;
            case R.id.radiohindi:
                languagePref = "hi";
                break;
            case R.id.radioarabic:
                languagePref = "ar";
                break;


        }

        if (!languagePref.isEmpty()) {

            LocaleHelper.setLocale(MainActivity.this, languagePref);
            finish();
            startActivity(getIntent());
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
    }

    private void init() {
        lang = LocaleHelper.selectedLanguage(this);
        getSupportActionBar().hide();
        ivchangeLanguage = findViewById(R.id.iv_change_language);
    }
}