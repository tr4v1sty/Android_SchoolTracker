package com.travis.c196_project.Views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.travis.c196_project.R;

public class MainActivity extends Activity {

    //test gradle sync 3
    //test gradle sync 3

    public Button viewTermsButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_activity);

        viewTermsButton = findViewById(R.id.button_view_terms);
        viewTermsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent1 = new Intent(MainActivity.this, TermList.class);

                startActivity(intent1);

            }
        });

    }
}
