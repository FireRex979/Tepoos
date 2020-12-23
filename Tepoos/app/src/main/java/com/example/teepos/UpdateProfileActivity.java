package com.example.teepos;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.teepos.api.RetrofitHelper;
import com.example.teepos.datasignup.UpdateProfile;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateProfileActivity extends AppCompatActivity {
    private EditText nama_et, tgl_lahir_et;
    private RadioGroup gender;
    private Button btn_update;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        preferences = getSharedPreferences("profile", Context.MODE_PRIVATE);
        String oldNama = preferences.getString("nama", null);
        String oldTglLahir = preferences.getString("tgl_lahir", null);
        String kelamin = preferences.getString("kelamin", null);
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        nama_et = (EditText) findViewById(R.id.nama_et);
        nama_et.setText(oldNama);
        gender = (RadioGroup) findViewById(R.id.kelamin);

        tgl_lahir_et = (EditText) findViewById(R.id.tgl_lahir_et);
        tgl_lahir_et.setText(oldTglLahir);
        if(kelamin.equals("l")){
            gender.check(R.id.male);
        }else if(kelamin.equals("p")){
            gender.check(R.id.female);
        }

        tgl_lahir_et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });

        btn_update = (Button) findViewById(R.id.btn_update);
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isNetworkConnected()){
                    showDialog();
                }else{
                    Toast.makeText(UpdateProfileActivity.this, "Tidak ada koneksi internet, update profile gagal", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void showDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title dialog
        alertDialogBuilder.setTitle("Update Profile");

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage("Anda yakin ingin mengupdate Profile?")
                .setCancelable(false)
                .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // jika tombol diklik, maka akan menutup activity ini
                        id = preferences.getInt("id", 0);
                        String nama = nama_et.getText().toString();
                        String tgl_lahir = tgl_lahir_et.getText().toString();
                        int selectedId = gender.getCheckedRadioButtonId();
                        RadioButton genderButton = (RadioButton) findViewById(selectedId);
                        String kelamin = genderButton.getText().toString();
                        asyncUpdateProfile(
                                id,
                                nama,
                                tgl_lahir,
                                kelamin
                        );
                    }
                })
                .setNegativeButton("Tidak",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // jika tombol ini diklik, akan menutup dialog
                        // dan tidak terjadi apa2
                        dialog.cancel();
                    }
                });

        // membuat alert dialog dari builder
        AlertDialog alertDialog = alertDialogBuilder.create();

        // menampilkan alert dialog
        alertDialog.show();
    }

    private void showDateDialog(){
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                tgl_lahir_et.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

    public void asyncUpdateProfile(int id, String nama, String tgl_lahir, String kelamin){
        RetrofitHelper.server(this).updateProfile(
                id,
                nama,
                tgl_lahir,
                kelamin
        ).enqueue(new Callback<UpdateProfile>() {
            @Override
            public void onResponse(Call<UpdateProfile> call, Response<UpdateProfile> response) {
                String nama = response.body().getName();
                String tgl_lahir = response.body().getTglLahir();
                String kelamin = response.body().getKelamin();
                editor = preferences.edit();
                editor.putString("nama", nama);
                editor.putString("tgl_lahir", tgl_lahir);
                editor.putString("kelamin", kelamin);
                editor.apply();
                finish();
                Toast.makeText(UpdateProfileActivity.this, "Data User berhasil diupdate", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<UpdateProfile> call, Throwable t) {
                Toast.makeText(UpdateProfileActivity.this, "Data User Gagal diupdate", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}