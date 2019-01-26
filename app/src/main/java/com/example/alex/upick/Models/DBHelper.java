package com.example.alex.upick.Models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "upickdb";
    public static final int DB_VERSION = 1;

    public static final String TABLE_FAVORITES = "favorites";
    public static final String ID = "id";
    public static final String ID_TRACKS_FAVORITES = "track_id";
    public static final String ID_USER_FAVORITES = "user_id";
    public static final String ADDED_AT = "added_at";
    public static final String ARTISTS_FAVORITES = "artists";
    public static final String NAME_FAVORITES = "name";
    public static final String IMAGE_FAVORITES = "image";


    SQLiteDatabase database;

    public DBHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);

        this.database = this.getWritableDatabase();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

            String createTableFavorites =  "CREATE TABLE " + TABLE_FAVORITES +
                    "( " + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    ID_TRACKS_FAVORITES + " VARCHAR(255) NOT NULL, " +
                    ID_USER_FAVORITES + " INTEGER NOT NULL, " +
                    ADDED_AT + " TIMESTAMP, " +
                    ARTISTS_FAVORITES + " VARCHAR(255) NOT NULL, " +
                    NAME_FAVORITES + " VARCHAR(255) NOT NULL"+ ");";

            db.execSQL(createTableFavorites);



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITES);
             this.onCreate(db);
    }
}
