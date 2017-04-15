package com.example.sashok.clientapp;

import android.app.Application;

import java.util.Map;

import retrofit2.Call;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by sashok on 13.4.17.
 */

public class App extends Application {

    private static RetailcrmApi retailcrmApi;
    private Retrofit retrofit;

    public static RetailcrmApi getApi() {
        return retailcrmApi;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        retrofit = new Retrofit.Builder()
                .baseUrl("https://fsfss.retailcrm.ru/") //Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create()) //Конвертер, необходимый для преобразования JSON'а в объекты
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        retailcrmApi = retrofit.create(RetailcrmApi.class); //Создаем объект, при помощи которого будем выполнять запросы
    }

    public interface RetailcrmApi {
        @GET("api/v4/customers")
        Observable<Response> getCostumers(@Query("apiKey") String keyName);

        @POST("api/v4/customers/{id}/edit")
        Call<String> editCostumer(@Path("id") int id, @Query("apiKey") String keyName, @Query("by") String from, @Query("customer") String customer, @Query("site") String site);

        @FormUrlEncoded
        @POST("api/v4/customers/{id}/edit")
        Observable<Object>postCustomer(@Path("id") Integer id, @FieldMap Map<String, String> gson);
    }
}
