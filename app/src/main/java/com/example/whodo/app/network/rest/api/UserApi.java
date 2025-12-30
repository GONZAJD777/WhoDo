package com.example.whodo.app.network.rest.api;

import com.example.whodo.app.domain.user.User;
import com.example.whodo.app.network.ApiResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

//******// INTERFACE NECESARIA PARA RETROFIT, INSTANCIA Y GENERA UNA CLASE EN TIEMPO DE EJECUCION

public interface UserApi {
    @POST("/api/users/createUser")
    Call<User> createUser(@Body User user);
    @PUT("/api/users/updateUser")
    Call<ApiResponse<User>> updateUser(@Body User user);
    @GET("api/users/getAllUsersInBound")
    Call<ApiResponse<List<User>>> findProviders(@Query("userType") int userType, @Query("lat1") Double lat1, @Query("lon1") Double lon1, @Query("lat2") Double lat2, @Query("lon2") Double lon2 );
    @GET("/api/users/userByAuth/{authId}")
    Call<ApiResponse<User>> findUser(@Path("authId") String id);
    @GET("/api/users/clientes")
    Call<User> findCustomer();

}