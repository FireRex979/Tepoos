package com.example.teepos.db;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;

import com.example.teepos.db.AppDatabase;

public class App extends Application {
    private static AppDatabase db = null;
    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static AppDatabase db(Context context) {
        if(db == null){
            db = Room.databaseBuilder(context,
                    AppDatabase.class, "db_tepos").build();
        }
        return db;
    }
}
