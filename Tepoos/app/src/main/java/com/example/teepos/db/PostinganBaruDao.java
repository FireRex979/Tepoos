package com.example.teepos.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;
@Dao
public interface PostinganBaruDao {
    @Query("SELECT * FROM postinganbaru ORDER BY id_postingan DESC")
    List<PostinganBaru> getAll();

    @Insert
    void insert(PostinganBaru... postinganBarus);

    @Query("DELETE FROM postinganbaru")
    void truncate();
}
