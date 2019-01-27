package com.example.alex.upick.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class DAO {

    public DBHelper dbHelper;
    SQLiteDatabase db;

    public DAO(Context context){
        dbHelper = new DBHelper(context);
    }


    public boolean existFav(String id_track){
        db = dbHelper.getWritableDatabase();
        String query = "SELECT * FROM " + DBHelper.TABLE_FAVORITES + " WHERE " + DBHelper.ID_TRACKS_FAVORITES + " = '" + id_track + "'" ;
        Cursor cursor = db.rawQuery(query,null);
        return cursor.getCount() > 0;
    }

    public void AddFavorites(Favorites favorites){
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.ID_TRACKS_FAVORITES,favorites.getTrack_id());
        values.put(DBHelper.ID_USER_FAVORITES,favorites.getUser_id());
        values.put(DBHelper.ADDED_AT,favorites.getAdded_at());
        values.put(DBHelper.ARTISTS_FAVORITES,favorites.getArtists());
        values.put(DBHelper.NAME_FAVORITES,favorites.getName());
        db.insert(DBHelper.TABLE_FAVORITES,null,values);
        db.close();

    }


    public ArrayList<Favorites> getFavorites(){

        db = dbHelper.getWritableDatabase();
        ArrayList<Favorites> favoritesList = new ArrayList<>();
        String query = "SELECT * FROM " + DBHelper.TABLE_FAVORITES;
        Cursor cursor = db.rawQuery(query,null);

        if(cursor.moveToFirst()){
            do{
                Favorites favorites = new Favorites();
                favorites.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.ID)));
                favorites.setTrack_id(cursor.getString(cursor.getColumnIndex(DBHelper.ID_TRACKS_FAVORITES)));
                favorites.setUser_id(cursor.getInt(cursor.getColumnIndex(DBHelper.ID_USER_FAVORITES)));
                favorites.setAdded_at(cursor.getString(cursor.getColumnIndex(DBHelper.ADDED_AT)));
                favorites.setArtists(cursor.getString(cursor.getColumnIndex(DBHelper.ARTISTS_FAVORITES)));
                favorites.setName(cursor.getString(cursor.getColumnIndex(DBHelper.NAME_FAVORITES)));

                favoritesList.add(favorites);
            }while(cursor.moveToNext());
        }
        db.close();

        return favoritesList;
    }

    public void deleteFavorites(String id_track){
        db = dbHelper.getWritableDatabase();
        db.execSQL("DELETE FROM "+ DBHelper.TABLE_FAVORITES+ " WHERE "+ DBHelper.ID_TRACKS_FAVORITES + " = '" + id_track + "' ;");

    }





}
