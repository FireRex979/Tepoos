package com.example.teepos.api;

import com.example.teepos.datasignup.GetDataUser;
import com.example.teepos.datasignup.ResponseLogin;
import com.example.teepos.datasignup.ResponseSignUp;
import com.example.teepos.datasignup.StorePostingan;
import com.example.teepos.datasignup.UpdateFotoProfile;
import com.example.teepos.datasignup.UpdateProfile;
import com.example.teepos.datasignup.postingan.Response;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiService {

  @FormUrlEncoded
  @POST("signup")
  Call<ResponseSignUp> signUp(
          @Field("email") String email,
          @Field("name") String name,
          @Field("tgl_lahir") String tgl_lahir,
          @Field("kelamin") String kelamin,
          @Field("password") String password
  );

  @FormUrlEncoded
  @POST("login")
  Call<ResponseLogin> login(
          @Field("email") String email,
          @Field("password") String password
  );

  @FormUrlEncoded
  @POST("update/profile/{id}")
  Call<UpdateProfile> updateProfile(
          @Path("id") int id,
          @Field("name") String name,
          @Field("tgl_lahir") String tgl_lahir,
          @Field("kelamin") String kelamin
  );

  @Multipart
  @POST("postingan/store")
  Call<StorePostingan> storePostingan(
          @Part("id_user") RequestBody id_user,
          @Part("caption") RequestBody caption,
          @Part MultipartBody.Part image
  );

  @GET("postingan/{id}")
  Call<com.example.teepos.datasignup.showPostingan.Response> showPostingan(
          @Path("id") int id
  );

  @Multipart
  @POST("user/update/foto-profile")
  Call<UpdateFotoProfile> updateFotoProfile(
          @Part("id_user") RequestBody id_user,
          @Part MultipartBody.Part image
  );

  @GET("user")
  Call<GetDataUser> getDataUser();

  @GET("postingan")
  Call<Response> showPostingan();

  @POST("komentar/store")
  Call<com.example.teepos.datasignup.storeKomentar.Response> storeKomentar(
          @Field("id_user") int id_user,
          @Field("komentar") String komentar,
          @Field("id_postingan") int id_postingan
  );

}
