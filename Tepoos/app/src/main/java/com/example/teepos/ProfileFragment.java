package com.example.teepos;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.teepos.api.RetrofitHelper;
import com.example.teepos.datasignup.UpdateFotoProfile;
import com.example.teepos.fragmentProfile.NotifikasiFragment;
import com.example.teepos.fragmentProfile.PostinganFragment;
import com.example.teepos.fragmentProfile.TentangFragment;
import com.tbruyelle.rxpermissions3.RxPermissions;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import pl.aprilapps.easyphotopicker.MediaFile;
import pl.aprilapps.easyphotopicker.MediaSource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {
    private Button btn_tentang, btn_postingan, btn_notifikasi, btn_ganti_profile;
    public ImageView foto_profile_iv;
    private File file;
    EasyImage easyImage;
    RxPermissions rxPermissions;
    SharedPreferences preferences;
    ProgressDialog progressDoalog;
    public ProfileFragment() {
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
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Drawable btn_left_active = ContextCompat.getDrawable(requireContext(), R.drawable.btn_profile_left_active);
        Drawable btn_right_active = ContextCompat.getDrawable(requireContext(), R.drawable.btn_profile_rigth_active);
        Drawable btn_left = ContextCompat.getDrawable(requireContext(), R.drawable.btn_left_corners);
        Drawable btn_right = ContextCompat.getDrawable(requireContext(), R.drawable.btn_rigth_corners);

        easyImage = new EasyImage.Builder(getContext())
                .setCopyImagesToPublicGalleryFolder(false)
                .setFolderName("EasyImage sample")
                .allowMultiple(true)
                .build();

        rxPermissions = new RxPermissions(ProfileFragment.this);

        progressDoalog = new ProgressDialog(getContext());
        progressDoalog.setCancelable(false);
        progressDoalog.setMessage("Foto Sedang Diupload....");

        preferences = getContext().getSharedPreferences("profile", Context.MODE_PRIVATE);
        String foto_profile = preferences.getString("foto_profile", null);
        foto_profile_iv = (ImageView) getView().findViewById(R.id.profileImage);
        Glide.with(getContext()).load(foto_profile).into(foto_profile_iv);
        btn_tentang = (Button) getView().findViewById(R.id.btn_tentang);
        btn_postingan = (Button) getView().findViewById(R.id.btn_postingan);
        btn_notifikasi = (Button) getView().findViewById(R.id.btn_notif);
        btn_ganti_profile = (Button) getView().findViewById(R.id.btn_ganti_foto_profile);
        loadFragment(new TentangFragment());
        btn_tentang.setBackground(btn_left_active);
        btn_tentang.setTextColor(Color.parseColor("#4682B4"));
        btn_tentang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_postingan.setBackgroundColor(Color.parseColor("#4682B4"));
                btn_postingan.setTextColor(Color.parseColor("#FFFFFF"));
                btn_notifikasi.setBackground(btn_right);
                btn_notifikasi.setTextColor(Color.parseColor("#FFFFFF"));
                btn_tentang.setBackground(btn_left_active);
                btn_tentang.setTextColor(Color.parseColor("#4682B4"));
                loadFragment(new TentangFragment());
            }
        });

        btn_notifikasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_postingan.setBackgroundColor(Color.parseColor("#4682B4"));
                btn_postingan.setTextColor(Color.parseColor("#FFFFFF"));
                btn_notifikasi.setBackground(btn_right_active);
                btn_notifikasi.setTextColor(Color.parseColor("#4682B4"));
                btn_tentang.setBackground(btn_left);
                btn_tentang.setTextColor(Color.parseColor("#FFFFFF"));
                loadFragment(new NotifikasiFragment());
            }
        });

        btn_postingan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_postingan.setBackgroundColor(Color.parseColor("#F0E68C"));
                btn_postingan.setTextColor(Color.parseColor("#4682B4"));
                btn_notifikasi.setBackground(btn_right);
                btn_notifikasi.setTextColor(Color.parseColor("#FFFFFF"));
                btn_tentang.setBackground(btn_left);
                btn_tentang.setTextColor(Color.parseColor("#FFFFFF"));
                loadFragment(new PostinganFragment());
            }
        });

        btn_ganti_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rxPermissions
                        .request(Manifest.permission.CAMERA)
                        .subscribe(granted -> {
                            if (granted) { // Always true pre-M
                                openGallery();
                            } else {
                                Toast.makeText(getContext(), "hahahhaha", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

    private void openGallery() {
        easyImage.openCameraForImage(ProfileFragment.this);
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getChildFragmentManager();

        FragmentTransaction fragmentTransaction = fm.beginTransaction();

        fragmentTransaction.replace(R.id.frameprofile, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        easyImage.handleActivityResult(requestCode, resultCode, data, getActivity(), new DefaultCallback() {
            @Override
            public void onMediaFilesPicked(MediaFile[] imageFiles, MediaSource source) {
                onPhotosReturned(imageFiles);
            }

            @Override
            public void onImagePickerError(@NonNull Throwable error, @NonNull MediaSource source) {
                //Some error handling
                error.printStackTrace();
            }

            @Override
            public void onCanceled(@NonNull MediaSource source) {
                //Not necessary to remove any files manually anymore
            }
        });
    }

    private void onPhotosReturned(MediaFile[] imageFiles) {
        file = imageFiles[0].getFile();
        uploadFotoProfile();
        Glide.with(getContext()).load(imageFiles[0].getFile()).error(R.drawable.profile).into(foto_profile_iv);
    }

    private void uploadFotoProfile() {
        int id = preferences.getInt("id", 0);
        String id_string = Integer.toString(id);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("foto_profile", file.getName(), requestFile);
        RequestBody id_user = RequestBody.create(MediaType.parse("multipart/form-data"),id_string);
        progressDoalog.show();
        RetrofitHelper.server(getContext()).updateFotoProfile(
                id_user,
                body
        ).enqueue(new Callback<UpdateFotoProfile>() {
            @Override
            public void onResponse(Call<UpdateFotoProfile> call, Response<UpdateFotoProfile> response) {
                progressDoalog.dismiss();
                if (response.body().isSuccess()){
                    Toast.makeText(getContext(), "Sukses Mengganti Foto", Toast.LENGTH_SHORT).show();
                }else{
                    Glide.with(getContext()).load(R.drawable.profile).error(R.drawable.profile).into(foto_profile_iv);
                    Toast.makeText(getContext(), "Gagal Mengganti Foto", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UpdateFotoProfile> call, Throwable t) {

            }
        });
    }
}