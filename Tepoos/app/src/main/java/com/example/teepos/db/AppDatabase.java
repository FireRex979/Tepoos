package com.example.teepos.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Postingan.class, PostinganBaru.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PostinganDao postinganDao();

    public abstract PostinganBaruDao postinganBaruDao();
}
    