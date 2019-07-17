package com.example.touchpoint.api;

import com.example.touchpoint.models.ApiResponse;
import com.example.touchpoint.models.Contract;
import com.example.touchpoint.models.Identification;
import com.example.touchpoint.models.LoginResponse;
import com.example.touchpoint.models.User;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface TouchPointService {

    @POST("/api/auth/login")
    Call<LoginResponse> loginUser(@Body User user);

    @Multipart
    @POST("/api/upload/contract-image")
    Call<ApiResponse> uploadContractImage(
            @Header("Authorization") String authorization,
            @Part MultipartBody.Part image,
            @Part("id") RequestBody id);



    @POST("/api/store-contracts")
    Call<ApiResponse> uploadContract(
            @Header("Authorization") String authorization,
            @Body Contract contract);


    @Multipart
    @POST("/api/upload/identification-image")
    Call<ApiResponse> uploadIdentificationImage(
            @Header("Authorization") String authorization,
            @Part MultipartBody.Part image1,
            @Part MultipartBody.Part image2,
            @Part("id") RequestBody id);



    @POST("/api/store-identification")
    Call<ApiResponse> uploadIdentification(
            @Header("Authorization") String authorization,
            @Body Identification identification);

    @GET("/api/identification/{id}")
    Call<Identification> getIdentification( @Header("Authorization") String authorization,  @Path("id") int id);

    @GET("/api/contracts/{id}")
    Call<Contract> getContract(@Header("Authorization") String authorization, @Path("id") int id);

}
