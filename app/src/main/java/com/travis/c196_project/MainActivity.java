package com.travis.c196_project;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends ListActivity {

    public Button btnAddTerm;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity);


        btnAddTerm = findViewById(R.id.btnAddTerm);
        btnAddTerm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent addTerm;
                addTerm = new Intent(MainActivity.this, TermDetails.class);
                startActivity(addTerm);
            }
        });


        ListView lv;
        lv = getListView();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                intent = new Intent(MainActivity.this, TermDetails.class);
                Term term;
                term = (Term) parent.getItemAtPosition(position);
                //get info
                intent.putExtra("termId", term.getTermId());
                intent.putExtra("termName", term.getTermName());
                intent.putExtra("termStart", term.getTermStart());
                intent.putExtra("termEnd", term.getTermEnd());

                startActivity(intent);
            }
        });
    }


    public void onClick(View view) {
        ArrayAdapter<Term> adapter;
        adapter = (ArrayAdapter<Term>) getListAdapter();
        adapter.notifyDataSetChanged();
    }


    @Override
    protected void onResume() {
        super.onResume();
        DatabaseConnection datasource;
        datasource = new DatabaseConnection(this);
        datasource.open();
        List<Term> listValue;
        listValue = datasource.getAllTerms();
        DatabaseConnection.databaseHelper.close();
        ArrayAdapter<Term> adapter;
        adapter = new ArrayAdapter<>(this,
                R.layout.list_items, listValue);
        setListAdapter(adapter);
    }


    @Override
    protected void onPause() {
        super.onPause();
    }


}
