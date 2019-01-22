package com.example.alex.upick.Models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "upickdb";
    private static final int DB_VERSION = 1;

    private static final String TABLE_FAVORITES = "favorites";
    private static final String ID_TRACKS_FAVORITES = "track_id";
    private static final String ID_USER_FAVORITES = "user_id";
    private static final String ADDED_AT = "added_at";
    private static final String ARTISTS_FAVORITES = "artists";
    private static final String NAME_FAVORITES = "name";
    private static final String IMAGE_FAVORITES = "image";


    SQLiteDatabase database;

    public DBHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);

        this.database = this.getWritableDatabase();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

            String createTableFavorites =  "CREATE TABLE " + TABLE_FAVORITES +
                    "( " + ID_TRACKS_FAVORITES + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    ID_USER_FAVORITES + " INTEGER, " +
                    ADDED_AT + " TEXT NOT NULL, " +
                    ARTISTS_FAVORITES + " TEXT NOT NULL, " +
                    NAME_FAVORITES + " Text NOT NULL," +
                    IMAGE_FAVORITES + " BLOB " +
                    ");";

            db.execSQL(createTableFavorites);



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITES);
             this.onCreate(db);
    }
}
