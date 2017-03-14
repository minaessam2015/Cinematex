package com.example.android.gridview.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mina essam on 24-Nov-16.
 */
public class MovieDbHelper extends SQLiteOpenHelper {
    public final static String DATABASE_NAME="Movies.db";
    public static final int DATABASE_VERSION=1;
    public MovieDbHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
      db.execSQL(MovieContract.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
      db.execSQL(MovieContract.DELETE_TABLE);
        onCreate(db);
    }
}
