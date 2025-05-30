package com.example.whodo.app.network.rest.api;

import com.example.whodo.app.domain.user.User;
import com.example.whodo.app.domain.user.UserApiRestRequestDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

//******// INTERFACE NECESARIA PARA RETROFIT, INSTANCIA Y GENERA UNA CLASE EN TIEMPO DE EJECUCION

public interface UserApi {
    @POST("/api/users/createUser")
    Call<User> createUser(@Body UserApiRestRequestDTO UserApiRestRequestDTO);

    @PUT("/api/users/updateUser")
    Call<User> updateUser(@Body UserApiRestRequestDTO UserApiRestRequestDTO);

    @GET("/api/users/usuarios/{id}")
    Call<User> findUser(@Path("id") String id);

    @GET("/api/users/clientes")
    Call<User> findCustomer();

    @GET("/api/users/proveedores")
    Call<List<User>> findProviders();

}