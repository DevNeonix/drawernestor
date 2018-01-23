package com.example.root.navigation.services;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by marquez on 17/01/18.
 */

public class API {

    public static Retrofit myRetrofit = new Retrofit.Builder().baseUrl("http://190.239.17.114/marcaideas.php/").addConverterFactory(GsonConverterFactory.create()).build();
}
