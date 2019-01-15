package com.travis.c196_project.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.travis.c196_project.Utilities.DBHelper;
import com.travis.c196_project.Models.Term;

import java.util.ArrayList;
import java.util.List;

public class TermData {

    private SQLiteDatabase database;
    private DBHelper dbHelper;

    public TermData(Context context) { dbHelper = new DBHelper(context); }

    public void open() throws SQLException { database = dbHelper.getWritableDatabase(); }

    public void close() { dbHelper.close(); }

    private static final String [] columns_terms = {
            DBHelper.TERM_END_COLUMN,
            DBHelper.TERM_ID_COLUMN,
            DBHelper.TERM_NAME_COLUMN,
            DBHelper.TERM_START_COLUMN
    };

    public Term createTerm(Term term){

        ContentValues values;
        values = new ContentValues();

        values.put(DBHelper.TERM_NAME_COLUMN,
                term.getTermName());
        values.put(DBHelper.TERM_START_COLUMN,
                term.getTermStart());
        values.put(DBHelper.TERM_END_COLUMN,
                term.getTermEnd());

        long insertId;
        insertId = database.insert(DBHelper.TERM_TABLE,
                null, values);

        term.setTermId(insertId);

        return term;
    }

    public void updateTerm(Term term) {

        ContentValues values;
        values = new ContentValues();

        values.put(DBHelper.TERM_ID_COLUMN,
                term.getTermId());
        values.put(DBHelper.TERM_NAME_COLUMN,
                term.getTermName());
        values.put(DBHelper.TERM_START_COLUMN,
                term.getTermStart());
        values.put(DBHelper.TERM_END_COLUMN,
                term.getTermEnd());

        database.update(DBHelper.TERM_TABLE,
                values,
                DBHelper.TERM_ID_COLUMN + "=" + term.getTermId(),
                null);
    }

    public void deleteTerm(long id) {

        database.delete(DBHelper.TERM_TABLE,
                DBHelper.TERM_ID_COLUMN + " = " + id,
                null);

    }

    public List<Term> getAllTerms() {

        List<Term> termList = new ArrayList<>();

        Cursor cursor = database.query(DBHelper.TERM_TABLE,
                columns_terms,
                null,
                null,
                null,
                null,
                null);

        if (cursor.getCount() > 0) while (cursor.moveToNext()) {
            Term term = new Term();
            term.setTermId(cursor.getLong(cursor.getColumnIndex(DBHelper.TERM_ID_COLUMN)));
            term.setTermName(cursor.getString(cursor.getColumnIndex(DBHelper.TERM_NAME_COLUMN)));
            term.setTermStart(cursor.getString(cursor.getColumnIndex(DBHelper.TERM_START_COLUMN)));
            term.setTermEnd(cursor.getString(cursor.getColumnIndex(DBHelper.TERM_END_COLUMN)));
            termList.add(term);
        }

        return termList;
    }



}
