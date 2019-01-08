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

public class TermList extends ListActivity {

    public Button btnAddTerm;


    public void configAddTerm() {
        btnAddTerm = findViewById(R.id.btnAddTerm);
        btnAddTerm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent addTerm = new Intent(TermList.this, TermDetails.class);
                startActivity(addTerm);
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);
        configAddTerm();


        ListView lv = getListView();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(TermList.this, TermDetails.class);
                Term term = (Term) parent.getItemAtPosition(position);
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
        ArrayAdapter<Term> adapter = (ArrayAdapter<Term>) getListAdapter();
        adapter.notifyDataSetChanged();
    }


    @Override
    protected void onResume() {
        super.onResume();
        DatabaseConnection datasource = new DatabaseConnection(this);
        datasource.open();
        List<Term> listValue = datasource.getAllTerms();
        DatabaseConnection.databaseHelper.close();
        ArrayAdapter<Term> adapter = new ArrayAdapter<>(this,
                R.layout.list_items, listValue);
        setListAdapter(adapter);
    }


    @Override
    protected void onPause() {
        super.onPause();
    }
}
