package com.travis.c196_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public TextView email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        defineButtons();

    }

    public void defineButtons() {
        findViewById(R.id.btnTerms).setOnClickListener(buttonClickListener);
        email = findViewById(R.id.textView2);
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

    public void shareEmail(View view) {

        email = findViewById(R.id.textView2);
        String emailString = email.getText().toString();

        Intent shareEmailIntent = new Intent();
        shareEmailIntent.setAction(Intent.ACTION_SEND);
        shareEmailIntent.putExtra(Intent.EXTRA_TEXT, emailString);
        shareEmailIntent.setType("text/plain");
        startActivity(shareEmailIntent);
    }
}
