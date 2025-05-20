package com.example.whodo.app.network.rest.api;

import com.example.whodo.app.domain.user.UserApiRestRequestDTO;
import com.example.whodo.app.domain.user.UserDTO;

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
    Call<UserDTO> createUser(@Body UserApiRestRequestDTO UserApiRestRequestDTO);

    @PUT("/api/users/updateUser")
    Call<UserDTO> updateUser(@Body UserApiRestRequestDTO UserApiRestRequestDTO);

    @GET("/api/users/usuarios/{id}")
    Call<UserDTO> findUser(@Path("id") String id);

    @GET("/api/users/clientes")
    Call<UserDTO> findCustomer();

    @GET("/api/users/proveedores")
    Call<List<UserDTO>> findProviders();

    @GET("/api/users/idiomas")
    Call<List<String>> findLanguages();

    @GET("/api/users/servicios")
    Call<List<String>> findServices();
}