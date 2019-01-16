package com.travis.c196_project.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import com.travis.c196_project.Utilities.DBHelper;
import com.travis.c196_project.Models.Term;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

public class TermData {

    public static final String TERM_TABLE = "terms";
    private SQLiteDatabase database;
    private DBHelper dbHelper;

    public TermData(Context context) {
        dbHelper = new DBHelper(context);
    }

    public void open() throws SQLException { database = dbHelper.getWritableDatabase(); }

    public void close() { dbHelper.close(); }

    public static final String TERM_END_COLUMN = "termEnd";
    public static final String TERM_START_COLUMN = "termStart";
    public static final String TERM_NAME_COLUMN = "termName";
    public static final String TERM_ID_COLUMN = "termId";

    private static final String [] columns_terms = {
            TERM_END_COLUMN,
            TERM_ID_COLUMN,
            TERM_NAME_COLUMN,
            TERM_START_COLUMN
    };

    public Term createTerm(Term term){

        ContentValues values = new ContentValues();

        values.put(TERM_NAME_COLUMN,
                term.getTermName());
        values.put(TERM_START_COLUMN,
                term.getTermStart());
        values.put(TERM_END_COLUMN,
                term.getTermEnd());

        long insertId = database.insert(TERM_TABLE,
                null, values);

        term.setTermId(insertId);

        return term;
    }

    public void updateTerm(Term term) {

        ContentValues values = new ContentValues();

        values.put(TERM_ID_COLUMN,
                term.getTermId());
        values.put(TERM_NAME_COLUMN,
                term.getTermName());
        values.put(TERM_START_COLUMN,
                term.getTermStart());
        values.put(TERM_END_COLUMN,
                term.getTermEnd());

        database.update(TERM_TABLE,
                values,
                TERM_ID_COLUMN + "=" + term.getTermId(),
                null);
    }

    public void deleteTerm(long id) {

        database.delete(TERM_TABLE,
                TERM_ID_COLUMN + " = " + id,
                null);

    }

    public List<Term> getAllTerms() {

        List<Term> termList = new ArrayList<>();

        Cursor cursor = database.query(TERM_TABLE,
                columns_terms,
                null,
                null,
                null,
                null,
                null);

        cursorGetTerm(termList, cursor);

        return termList;
    }

    private void cursorGetTerm(List<Term> termList, Cursor cursor) {

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {

                Term term = new Term();

                term.setTermId(cursor.getLong(cursor.getColumnIndex(TERM_ID_COLUMN)));

                term.setTermName(cursor.getString(cursor.getColumnIndex(TERM_NAME_COLUMN)));
                term.setTermStart(cursor.getString(cursor.getColumnIndex(TERM_START_COLUMN)));
                term.setTermEnd(cursor.getString(cursor.getColumnIndex(TERM_END_COLUMN)));

                termList.add(term);
            }
        }
    }


}
