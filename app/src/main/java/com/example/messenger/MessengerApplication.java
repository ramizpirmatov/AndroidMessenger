package com.example.messenger;

import android.app.Application;

import com.example.messenger.db.DataBaseController;
import com.example.messenger.model.User;
import com.example.messenger.network.RetrofitApi;
import com.google.gson.Gson;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MessengerApplication extends Application
{

    private static Socket mSocket;
    private static final Gson gson = new Gson();
    private static User user;
    private static RetrofitApi retrofitApi;
    public static String CONVERSATION_ID_NAME = "newConversationId";
    public static String SOCKET_EVENT_NAME = "message";

    {
        try
        {
            if (mSocket == null || !mSocket.connected())
            {
                mSocket = IO.socket("https://tayqatech-messenger-server.herokuapp.com/");
            }
        }
        catch (URISyntaxException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static Socket getSocket()
    {
        return mSocket;
    }

    public static Gson getGson()
    {
        return gson;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        DataBaseController.init(this);
        initRetrofit();
    }

    private void initRetrofit()
    {
        String BASE_URL = "https://tayqatech-messenger-server.herokuapp.com/";
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit
                .Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .baseUrl(BASE_URL)
                .build();

        retrofitApi = retrofit.create(RetrofitApi.class);
    }

    public static RetrofitApi getRetrofitApi()
    {
        return retrofitApi;
    }

    public static void setRetrofitApi(RetrofitApi retrofitApi)
    {
        MessengerApplication.retrofitApi = retrofitApi;
    }

    public static User getUser()
    {
        return user;
    }

    public static void setUser(User user)
    {
        MessengerApplication.user = user;
    }
}
