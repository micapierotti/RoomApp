package com.mpierotti.roomapp;
import androidx.room.*;
import static androidx.room.OnConflictStrategy.REPLACE;
import java.util.List;

@Dao
public interface MainDAO {

    //Insert query
    @Insert
    void insert(MainData mainData);
    //Delete query
    @Delete
    void delete(MainData mainData);
    //Delete all query
    @Delete
    void reset(List<MainData> mainData);

    //Update query
    @Query("UPDATE table_name SET text = :sText WHERE ID = :sID")
    void update(int sID, String sText);

    //Get all data query
    @Query("SELECT * FROM table_name")
    List<MainData> getAll();

}
