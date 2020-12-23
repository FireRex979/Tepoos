package com.example.teepos.fragmentProfile;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.teepos.LandingPage;
import com.example.teepos.R;
import com.example.teepos.UpdateProfileActivity;
import com.example.teepos.App;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class TentangFragment extends Fragment {
    private SharedPreferences data_profile, credential_login;
    private SharedPreferences.Editor editor;
    private Button btn_logout, btn_update_profile;
    private TextView email_et, nama_et, tgl_lahir_et, kelamin_et;
    private ImageView foto_profile_iv;
    CompositeDisposable mDisposable;
    public TentangFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tentang, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        data_profile = getContext().getSharedPreferences("profile", Context.MODE_PRIVATE);
        credential_login = getContext().getSharedPreferences("credential_login", Context.MODE_PRIVATE);
        String email = data_profile.getString("email", null);
        String nama = data_profile.getString("nama", null);
        String tgl_lahir = data_profile.getString("tgl_lahir", null);
        String kelamin = data_profile.getString("kelamin", null);
        mDisposable = new CompositeDisposable();
        email_et = (TextView) getView().findViewById(R.id.email_et);
        nama_et = (TextView) getView().findViewById(R.id.nama_et);
        tgl_lahir_et = (TextView) getView().findViewById(R.id.tgl_lahir_et);
        kelamin_et = (TextView) getView().findViewById(R.id.kelamin);
        email_et.setText(email);
        nama_et.setText(nama);
        tgl_lahir_et.setText(tgl_lahir);
        if (kelamin.equals("p")){
            kelamin_et.setText("Perempuan");
        }else{
            kelamin_et.setText("Laki - Laki");
        }
        btn_logout = (Button) getView().findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogLogout();
//                logout();
            }
        });

        btn_update_profile = (Button) getView().findViewById(R.id.btn_edit_profile);
        btn_update_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile();
            }
        });
    }

    private void showDialogLogout() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                getContext());

        // set title dialog
        alertDialogBuilder.setTitle("Yakin ingin Logout?");

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage("Klik Ya untuk keluar!")
                .setCancelable(false)
                .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // jika tombol diklik, maka akan menutup activity ini
                        logout();
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

    public void updateProfile(){
        Intent toUpdateProfile = new Intent(getContext(), UpdateProfileActivity.class);
        startActivity(toUpdateProfile);
    }

    public void logout(){
        String token = null;
        editor = credential_login.edit();
        editor.putString("token_login", token);
        editor.apply();
        truncate();
        String nama = null;
        String email = null;
        String kelamin = null;
        String tgl_lahir = null;
        editor = data_profile.edit();
        editor.putString("nama", nama);
        editor.putString("email", email);
        editor.putString("kelamin", kelamin);
        editor.putString("tgl_lahir", tgl_lahir);
        editor.apply();
        truncate();
        Intent toLandingPage = new Intent(getContext(), LandingPage.class);
        toLandingPage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(toLandingPage);
    }

    public void truncate(){
        Single.just(0)
                .subscribeOn(Schedulers.io())
                .map(new Function<Integer, Object>() {
                    @Override
                    public Object apply(Integer integer) throws Throwable {
                        App.db(getContext()).postinganDao().truncate();
                        return true;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Object>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                        mDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull Object o) {
                        Toast.makeText(getContext(), "Sukses Logout", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Toast.makeText(getContext(), "Gagal Logout", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}