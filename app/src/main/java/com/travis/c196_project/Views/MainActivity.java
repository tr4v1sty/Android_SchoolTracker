package com.travis.c196_project.Views;

import java.util.List;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.travis.c196_project.Data.TermData;
import com.travis.c196_project.Models.Term;
import com.travis.c196_project.R;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends ListActivity {

    public Button addNewTermButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity);

        addNewTermButton = findViewById(R.id.btnAddTerm);
        addNewTermButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent addTerm;
                addTerm = new Intent(MainActivity.this, TermView.class);

                startActivity(addTerm);
            }
        });

        ListView lv;
        lv = getListView();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                viewTermList((Term) parent.getItemAtPosition(position));
            }
        });
    }

    public void viewTermList(Term term){
        Intent intent;
        intent = new Intent(MainActivity.this, TermView.class);

        intent.putExtra("termId", term.getTermId());
        intent.putExtra("termName", term.getTermName());
        intent.putExtra("termStart", term.getTermStart());
        intent.putExtra("termEnd", term.getTermEnd());

        startActivity(intent);
    }


    public void onClick(View view) {

        ArrayAdapter<Term> adapter = (ArrayAdapter<Term>) getListAdapter();
        adapter.notifyDataSetChanged();

    }


    @Override
    protected void onResume() {

        super.onResume();

        TermData termData = new TermData(this);

        termData.open();

        List<Term> listValue;
        listValue = termData.getAllTerms();

        termData.close();

        ArrayAdapter<Term> adapter;
        adapter = new ArrayAdapter<>(this,
                R.layout.list_items,
                listValue);

        setListAdapter(adapter);
    }


    @Override
    protected void onPause() {
        super.onPause();
    }


}
