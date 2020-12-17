package com.example.teepos.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Postingan.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PostinganDao postinganDao();
}
    