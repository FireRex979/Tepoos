package com.example.teepos.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PostinganDao {
    @Query("SELECT * FROM postingan")
    List<Postingan> getAll();

    @Query("SELECT * FROM postingan WHERE id IN (:id)")
    List<Postingan> show(int id);

    @Insert
    void insert(Postingan... postingan);

    @Query("UPDATE postingan SET caption = :caption, foto = :foto WHERE id_postingan = :id_postingan")
    void update(String caption, String foto, String id_postingan);

    @Query("DELETE FROM postingan WHERE id_postingan = :id_postingan")
    void delete(String id_postingan);

}
    