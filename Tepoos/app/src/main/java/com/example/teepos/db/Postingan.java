package com.example.teepos.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Postingan {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "id_postingan")
    public String id_postingan;

    @ColumnInfo(name = "id_user")
    public String id_user;

    @ColumnInfo(name = "caption")
    public String caption;

    @ColumnInfo(name = "tgl_postingan")
    public String tgl_postingan;

    @ColumnInfo(name = "foto")
    public String foto;

    public int getId(){
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public void setTgl_postingan(String tgl_postingan) {
        this.tgl_postingan = tgl_postingan;
    }

    public String getTgl_postingan() {
        return tgl_postingan;
    }

    public String getId_postingan() {
        return id_postingan;
    }

    public void setId_postingan(String id_postingan) {
        this.id_postingan = id_postingan;
    }
}
    