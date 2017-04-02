package com.example.termtracker;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class TermTrackerCursorAdapter extends CursorAdapter {
    public TermTrackerCursorAdapter(Context context, Cursor c, int flags) { super(context, c, flags); }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.term_list_item, viewGroup, false );
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        String termTitle = cursor.getString( cursor.getColumnIndex(DataManager.TERM_TITLE));

        int position = termTitle.indexOf(10);
        if (position != -1) {
            termTitle = termTitle.substring(0, position) + " ...";
        }

        TextView tv = (TextView) view.findViewById(R.id.tvNote);
        tv.setText(termTitle);
    }
}