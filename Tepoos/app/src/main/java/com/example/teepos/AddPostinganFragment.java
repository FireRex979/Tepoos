package com.example.teepos;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.bumptech.glide.Glide;
import com.example.teepos.api.RetrofitHelper;
import com.example.teepos.datasignup.StorePostingan;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddPostinganFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddPostinganFragment extends Fragment {
    private Button btn_upload_image, btn_upload_postingan;
    RxPermissions rxPermissions;
    private ImageView img_preview;
    EasyImage easyImage;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private File file;
    private EditText caption_et;
    public ImageView btn_home_fragment, btn_add_fragment;
    ProgressDialog progressDoalog;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    public AddPostinganFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddPostinganFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddPostinganFragment newInstance(String param1, String param2) {
        AddPostinganFragment fragment = new AddPostinganFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_postingan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        preferences = getContext().getSharedPreferences("profile", Context.MODE_PRIVATE);
        btn_upload_image = view.findViewById(R.id.btn_upload_image);
        btn_upload_postingan = view.findViewById(R.id.btn_upload_postingan);
        img_preview = view.findViewById(R.id.image_postingan);
        caption_et = view.findViewById(R.id.caption_et);
        btn_home_fragment = getActivity().findViewById(R.id.home);
        btn_add_fragment = getActivity().findViewById(R.id.add_postingan);

        progressDoalog = new ProgressDialog(getContext());
        progressDoalog.setCancelable(false);
        progressDoalog.setMessage("Postingan Sedang Dibuat....");

        easyImage = new EasyImage.Builder(getContext())
                .setCopyImagesToPublicGalleryFolder(false)
                .setFolderName("EasyImage sample")
                .allowMultiple(true)
                .build();

        rxPermissions = new RxPermissions(AddPostinganFragment.this);

        btn_upload_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rxPermissions
                        .request(Manifest.permission.CAMERA)
                        .subscribe(granted -> {
                            if (granted) { // Always true pre-M
                                openGallery();
                            } else {
                                Toast.makeText(getContext(), "Tidak ada ijin kamera", Toast.LENGTH_SHORT).show();
                            }
                });
            }
        });

        btn_upload_postingan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadPostingan();
            }
        });
    }

    private void uploadPostingan() {
        String caption = caption_et.getText().toString().trim();
        int id = preferences.getInt("id", 0);
        String id_string = Integer.toString(id);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("foto", file.getName(), requestFile);
        RequestBody id_user = RequestBody.create(MediaType.parse("multipart/form-data"),id_string);
        RequestBody captions = RequestBody.create(MediaType.parse("multipart/form-data"), caption);

        progressDoalog.show();

        RetrofitHelper.server(getContext()).storePostingan(
                id_user,
                captions,
                body
        ).enqueue(new Callback<StorePostingan>() {
            @Override
            public void onResponse(Call<StorePostingan> call, Response<StorePostingan> response) {
                progressDoalog.dismiss();
                if (response.body().isSuccess()){
                    Glide.with(getContext()).load(R.drawable.upload_image).error(R.drawable.upload_image).into(img_preview);

                    HomeActivity home = new HomeActivity();

                    home.toHomeFragment();

                    caption_et.setText("");
                    Toast.makeText(getContext(), "Sukses Menambahkan", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(), "gagal Menambahkan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<StorePostingan> call, Throwable t) {
                progressDoalog.dismiss();
            }
        });
    }

    public void openGallery(){
        easyImage.openCameraForImage(AddPostinganFragment.this);
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
        Glide.with(getContext()).load(imageFiles[0].getFile()).error(R.drawable.upload_image).into(img_preview);
        file = imageFiles[0].getFile();
    }
}