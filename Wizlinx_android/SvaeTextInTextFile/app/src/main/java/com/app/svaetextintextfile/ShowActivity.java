package com.app.svaetextintextfile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ShowActivity extends AppCompatActivity {

    private TextView tvShow;
    private EditText etSearch;
    private Button btnSearch;
    private ScrollView scrollView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        tvShow=findViewById(R.id.tv_show);
        btnSearch=findViewById(R.id.btn_search);
        etSearch=findViewById(R.id.et_search);
        scrollView=findViewById(R.id.scrollview);
        tvShow.setText(readFile());
        clickListener();
    }

    private void clickListener() {
       btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String criteria = etSearch.getText().toString();
                String fullText = tvShow.getText().toString();
                if (fullText.contains(criteria)) {
                    int indexOfCriteria = fullText.indexOf(criteria);
                    int lineNumber = tvShow.getLayout().getLineForOffset(indexOfCriteria);
                    String highlighted = "<font color='red'>"+criteria+"</font>";
                    fullText = fullText.replace(criteria, highlighted);
                    tvShow.setText(Html.fromHtml(fullText));

                    scrollView.scrollTo(0, tvShow.getLayout().getLineTop(lineNumber));
                }
            }
        });
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) {
                    String fullText = tvShow.getText().toString();
                    fullText = fullText.replace("<font color='red'>", "");
                    fullText = fullText.replace("</font>", "");
                    tvShow.setText(fullText);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private String readFile() {
        File fileEvents = new File(ShowActivity.this.getFilesDir()+"/.SaveText/sample");
        StringBuilder text = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileEvents));
            String line;
            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            br.close();
        } catch (IOException e) { }
        String result = text.toString();
        return result;
    }
}