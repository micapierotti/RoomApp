package com.mpierotti.roomapp;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

//Agregar entidades de database
@Database(entities = {MainData.class}, version = 1, exportSchema = false)
public abstract class RoomDB extends RoomDatabase {

    //Crear instancia de database
    private static RoomDB database;

    //Definir nombre de bdd;
    private static String DATABASE_NAME = "database";

    public synchronized static RoomDB getInstance(Context context){
     //Chequear condición
        if(database == null){
            database = Room.databaseBuilder(context.getApplicationContext(), RoomDB.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }

    //Crear DAO
    public abstract MainDAO mainDAO();

}
