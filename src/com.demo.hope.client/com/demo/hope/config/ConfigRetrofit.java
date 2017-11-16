package com.demo.hope.config;


import com.demo.hope.service.Population.GeoNames;
import com.demo.hope.service.Weather.GeoWeather;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.slf4j.bridge.SLF4JBridgeHandler;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;

public class ConfigRetrofit {

    static {
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
    }

    private ObjectMapper objectMapper() {
        return new ObjectMapper()
                .configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    private OkHttpClient client() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        return new OkHttpClient.Builder().addInterceptor(interceptor).build();
    }

    public GeoNames createNamesClient() {
        return new Retrofit.Builder()
                .client(client())
                .baseUrl("http://api.geonames.org")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(JacksonConverterFactory.create(objectMapper()))
                .build()
                .create(GeoNames.class);
    }


    public GeoWeather createWeatherClient() {
        return new Retrofit.Builder()
                .client(client())
                .baseUrl("http://api.geonames.org")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(JacksonConverterFactory.create(objectMapper()))
                .build()
                .create(GeoWeather.class);
    }


}
