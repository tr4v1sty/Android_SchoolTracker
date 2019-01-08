package com.travis.c196_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        defineButtons();
    }

    public void defineButtons() {
        findViewById(R.id.btnTerms).setOnClickListener(buttonClickListener);
    }

    private View.OnClickListener buttonClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            int i = view.getId();
            if (i == R.id.btnTerms) {
                Intent openTerms = new Intent(MainActivity.this, TermList.class);
                startActivity(openTerms);

            }
        }
    };

}
