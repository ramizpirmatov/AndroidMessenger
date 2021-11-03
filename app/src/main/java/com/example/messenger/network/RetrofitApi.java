package com.example.messenger.network;

import com.example.messenger.model.remote.Conversations;
import com.example.messenger.model.User;
import com.example.messenger.model.remote.Messages;
import com.example.messenger.model.remote.Users;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitApi
{
    @GET("users")
    Call<User> getMainUser(@Query(value = "phone") String phone);

    @GET("users")
    Call<Users> getUsers();

    @GET("conversations")
    Call<Conversations> getConversations();

    @GET("conversations")
    Call<Messages> getMessages(@Query(value = "id") long id);

    @POST("tokens")
    Call<String> postToken(@Body String token);
}
