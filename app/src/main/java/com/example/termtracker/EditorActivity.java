package com.example.termtracker;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EditorActivity extends AppCompatActivity {
    private String action;
    private EditText editor;
    private String termFilter;
    private String oldTermText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        editor = (EditText) findViewById(R.id.editText);

        Intent intent = getIntent();

        Uri uri = intent.getParcelableExtra(DataProvider.CONTENT_ITEM_TYPE);

        if (uri == null) {
            action = Intent.ACTION_INSERT;
            setTitle(getString(R.string.new_term));
        } else {
            action = Intent.ACTION_EDIT;
            termFilter = DataManager.TERM_ID + "=" + uri.getLastPathSegment();

            Cursor cursor = getContentResolver().query(uri, DataManager.ALL_TERM_COLUMNS, termFilter, null, null);
            cursor.moveToFirst();
            oldTermText = cursor.getString(cursor.getColumnIndex(DataManager.TERM_TITLE));
            editor.setText(oldTermText);
            editor.requestFocus();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (action.equals(Intent.ACTION_EDIT)) {
            getMenuInflater().inflate(R.menu.menu_editor, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (item.getItemId()) {
            case android.R.id.home:
                finishEditing();
                break;
            case R.id.action_delete:
                deleteTerm();
                break;
        }
        return true;
    }

    private void deleteTerm() {
        getContentResolver().delete(DataProvider.CONTENT_URI, termFilter, null);
        Toast.makeText(this, getString(R.string.term_deleted), Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
        finish();
    }

    private void finishEditing() {
        String newTerm = editor.getText().toString().trim();
        switch(action) {
            case Intent.ACTION_INSERT:
                if (newTerm.length() == 0 ) {
                    setResult(RESULT_CANCELED);
                } else {
                    insertTerm(newTerm);
                }
                break;
            case Intent.ACTION_EDIT:
                if (newTerm.length() == 0) {
                    deleteTerm();
                } else if (oldTermText.equals(newTerm)) {
                    setResult(RESULT_CANCELED);
                } else {
                    updateNote(newTerm);
                }
        }
        finish();
    }

    private void updateNote(String termText) {
        ContentValues values = new ContentValues();
        values.put(DataManager.TERM_TITLE, termText);
        getContentResolver().update(DataProvider.CONTENT_URI, values, termFilter, null);
        Toast.makeText(this, "Term updated", Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
    }

    private void insertTerm(String termText) {
        ContentValues values = new ContentValues();
        values.put(DataManager.TERM_TITLE, termText);
        getContentResolver().insert(DataProvider.CONTENT_URI, values);
        setResult(RESULT_OK);
    }

    @Override
    public void onBackPressed() {
        finishEditing();
    }
}