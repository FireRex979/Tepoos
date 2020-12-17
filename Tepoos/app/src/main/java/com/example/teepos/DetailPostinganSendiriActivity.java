package com.example.teepos;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.teepos.api.RetrofitHelper;
import com.example.teepos.datasignup.updatePostingan.Data;
import com.example.teepos.datasignup.updatePostingan.Response;
import com.example.teepos.db.App;
import com.example.teepos.db.Postingan;
import com.tbruyelle.rxpermissions3.RxPermissions;

import java.io.File;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import pl.aprilapps.easyphotopicker.MediaFile;
import pl.aprilapps.easyphotopicker.MediaSource;
import retrofit2.Call;
import retrofit2.Callback;

public class DetailPostinganSendiriActivity extends AppCompatActivity {
    private Button btn_upload_image, btn_upload_postingan, btn_delete;
    RxPermissions rxPermissions;
    private ImageView img_preview;
    EasyImage easyImage;
    private File file;
    private EditText caption_et;
    ProgressDialog progressDoalog;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    CompositeDisposable mDisposable;
    String id_postingan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_postingan_sendiri);
        String caption_value = getIntent().getStringExtra("caption");
        String foto_value = getIntent().getStringExtra("foto");
        id_postingan = getIntent().getStringExtra("id_postingan");
        mDisposable = new CompositeDisposable();
        preferences = this.getSharedPreferences("profile", Context.MODE_PRIVATE);
        btn_upload_image = findViewById(R.id.btn_upload_image);
        btn_upload_postingan = findViewById(R.id.btn_upload_postingan);
        btn_delete = findViewById(R.id.btn_delete);
        img_preview = findViewById(R.id.image_postingan);
        caption_et = findViewById(R.id.caption_et);
        Glide.with(this).load(foto_value).error(R.drawable.upload_image).into(img_preview);
        caption_et.setText(caption_value);
        progressDoalog = new ProgressDialog(this);
        progressDoalog.setCancelable(false);
        progressDoalog.setMessage("Postingan Sedang Diupdate....");

        easyImage = new EasyImage.Builder(this)
                .setCopyImagesToPublicGalleryFolder(false)
                .setFolderName("EasyImage sample")
                .allowMultiple(true)
                .build();

        rxPermissions = new RxPermissions(DetailPostinganSendiriActivity.this);

        btn_upload_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rxPermissions
                        .request(Manifest.permission.CAMERA)
                        .subscribe(granted -> {
                            if (granted) { // Always true pre-M
                                openGallery();
                            } else {
                                Toast.makeText(DetailPostinganSendiriActivity.this, "Tidak ada ijin kamera", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        btn_upload_postingan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePostingan();
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DetailPostinganSendiriActivity.this, "dhuhsaidhiashdia", Toast.LENGTH_SHORT).show();
                deletePostingan();
            }
        });
    }

    private void updatePostingan() {
        String caption = caption_et.getText().toString().trim();
        MultipartBody.Part body = null;
        if (file != null) {
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            body = MultipartBody.Part.createFormData("foto", file.getName(), requestFile);
        }
        RequestBody id = RequestBody.create(MediaType.parse("multipart/form-data"), id_postingan);
        RequestBody captions = RequestBody.create(MediaType.parse("multipart/form-data"), caption);

        progressDoalog.show();

        RetrofitHelper.server(this).updatePostingan(
                id,
                captions,
                body
        ).enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if (response.body().isSuccess()) {
                    simpanSqlite(response);
                    progressDoalog.dismiss();
                    finish();
                } else {
                    Toast.makeText(DetailPostinganSendiriActivity.this, "Gagal Mengupdate", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Toast.makeText(DetailPostinganSendiriActivity.this, "Gagal Mengupdate", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deletePostingan(){
        progressDoalog.show();
        RetrofitHelper.server(this).deletePostingan(
                id_postingan
        ).enqueue(new Callback<com.example.teepos.datasignup.deletePostingan.Response>() {
            @Override
            public void onResponse(Call<com.example.teepos.datasignup.deletePostingan.Response> call, retrofit2.Response<com.example.teepos.datasignup.deletePostingan.Response> response) {
                if (response.body().isSuccess()) {
                    deleteSqlite(response);
                    progressDoalog.dismiss();
                    finish();
                } else {
                    progressDoalog.dismiss();
                    Toast.makeText(DetailPostinganSendiriActivity.this, "Gagal Mengupdate", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<com.example.teepos.datasignup.deletePostingan.Response> call, Throwable t) {

            }
        });
    }

    private void deleteSqlite(retrofit2.Response<com.example.teepos.datasignup.deletePostingan.Response> response) {
        Single.just(0)
                .subscribeOn(Schedulers.io())
                .map(new Function<Integer, Object>() {
                    @Override
                    public Object apply(Integer integer) throws Throwable {
                        App.db(DetailPostinganSendiriActivity.this).postinganDao().delete(id_postingan);
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
                        Toast.makeText(DetailPostinganSendiriActivity.this, "Sukses Menghapus Data di lokal", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Toast.makeText(DetailPostinganSendiriActivity.this, "Gagal Menghapus", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void simpanSqlite(retrofit2.Response<com.example.teepos.datasignup.updatePostingan.Response> response) {
        Data data = response.body().getData();

        Single.just(0)
                .subscribeOn(Schedulers.io())
                .map(new Function<Integer, Object>() {
                    @Override
                    public Object apply(Integer integer) throws Throwable {
                        App.db(DetailPostinganSendiriActivity.this).postinganDao().update(data.getCaption(), data.getFoto(), data.getIdPostingan());
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
                        Toast.makeText(DetailPostinganSendiriActivity.this, "Sukses Mengupdate Data di lokal", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Toast.makeText(DetailPostinganSendiriActivity.this, "Gagal Mengupdate", Toast.LENGTH_SHORT).show();
                    }
                });

    }



    public void openGallery() {
        easyImage.openCameraForImage(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        easyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
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
        Glide.with(this).load(imageFiles[0].getFile()).error(R.drawable.upload_image).into(img_preview);
        file = imageFiles[0].getFile();
    }
}