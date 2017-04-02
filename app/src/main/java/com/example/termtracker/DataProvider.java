package com.example.termtracker;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class DataProvider extends ContentProvider {

    private static final String AUTHORITY = "com.example.termtracker.dataprovider";
    private static final String BASE_PATH = "terms";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH );

    //constants
    private static final int TERM = 1;
    private static final int TERM_ID = 2;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    public static final String CONTENT_ITEM_TYPE = "Term";

    static {
        uriMatcher.addURI(AUTHORITY, BASE_PATH, TERM);
        uriMatcher.addURI(AUTHORITY, BASE_PATH + "/#", TERM_ID);
    }

    private SQLiteDatabase database;

    @Override
    public boolean onCreate() {
        DataManager manager = new DataManager(getContext());
        database = manager.getWritableDatabase();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        if (uriMatcher.match(uri) == TERM_ID) {
            s = DataManager.TERM_ID + "=" + uri.getLastPathSegment();
        }
        return database.query(DataManager.TABLE_TERMS, DataManager.ALL_TERM_COLUMNS, s, null, null, null, DataManager.TERM_ID + " DESC");
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        long id = database.insert(DataManager.TABLE_TERMS, null, contentValues);
        return Uri.parse(BASE_PATH + "/" + id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return database.delete(DataManager.TABLE_TERMS, s, strings);
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return database.update(DataManager.TABLE_TERMS, contentValues, s, strings);
    }
}