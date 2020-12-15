package com.mpierotti.roomapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

//Definir tabla nombre
@Entity(tableName = "table_name")
public class MainData implements Serializable {

    //Crear id de columna
    @PrimaryKey(autoGenerate = true)
    private int ID;

    //Crear columna de texto
    @ColumnInfo(name = "text")
    private String text;

    //Generar getters y setters

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
