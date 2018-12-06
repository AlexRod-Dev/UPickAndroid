package com.example.alex.upick.Models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "dbupick";
    private static final int DB_VERSION = 1;

    private static final String TABLE_FAVORITES = "favorites";
    private static final String ID_FAVORITES = "id";
    private static final String ID_USER_FAVORITES = "id";
    private static final String NAME_FAVORITES = "";
    private static final String GENRE_FAVORITES = "";
    private static final String DURATION_FAVORITES = "";
    private static final String IMAGE_FAVORITES = "";


    private  final SQLiteDatabase database;

    public DBHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);

        this.database = this.getWritableDatabase();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

            String createTableFavorites =  "CREATE TABLE " + TABLE_FAVORITES +
                    "( " + ID_FAVORITES + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    ID_USER_FAVORITES + " INTEGER, " +
                    NAME_FAVORITES + " TEXT NOT NULL, " +
                    GENRE_FAVORITES + " TEXT NOT NULL, " +
                    DURATION_FAVORITES + " INTEGER NOT NULL," +
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
