package com.zebra.contentproviderapplications;

import static com.zebra.contentproviderapplications.DataBase.TABLE_NAME;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Provider extends ContentProvider {
    SQLiteDatabase sqLiteDatabase;
    static final String PROVIDER_NAME = "com.zebra.contentproviderapplications";
    static final String URL = "content://" + PROVIDER_NAME + "/students";
    public static final Uri CONTENT_URI = Uri.parse(URL);
    static final int STUDENTS = 1;
    @Override
    public boolean onCreate() {
        Context context=getContext();
        DataBase db=new DataBase(context);
        sqLiteDatabase=db.getWritableDatabase();
        return sqLiteDatabase != null;
    }
    static final UriMatcher uriMatcher;
    static{
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "students", STUDENTS);
    }
    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        long id=sqLiteDatabase.insert(TABLE_NAME,"",values);
        if(id>0)
        {
            Uri uri1= ContentUris.withAppendedId(CONTENT_URI,id);
            getContext().getContentResolver().notifyChange(uri1,null);
            return uri1;
        }
       throw new SQLException("Failed to add record into "+uri);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
