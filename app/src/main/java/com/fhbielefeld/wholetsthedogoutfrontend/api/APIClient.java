package com.fhbielefeld.wholetsthedogoutfrontend.api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A RestAdapter which creates the connection between RestAdapter and API Interface.
 */
public class APIClient {

    private static Retrofit retrofit = null;

    /**
     * A method which uses Retrofit and OkHttp to establish a connection
     * @return API Interface class object
     */
    public static Retrofit getClient() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();


        retrofit = new Retrofit.Builder()
                .baseUrl("http://37.120.191.169:5000")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        return retrofit;
    }

}
