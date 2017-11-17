package com.demo.hope.service.Weather;


import io.reactivex.Flowable;
import io.reactivex.Single;
import org.apache.commons.lang3.tuple.Pair;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.List;

public interface GeoWeather {

    static void doSomething(Pair<Double, Double> exampleParam) {
    }

    static void doSomethingElse() {
    }

    @GET("/findNearByWeatherJSON")
    Single<SearchResult> search(
            @Query("lat") Double lat,
            @Query("lng") Double lng,
            @Query("username") String username


    );

    default Flowable<Pair<String, String>> windOf(Pair<Double, Double> coordinates) {
        return search(coordinates.getLeft(), coordinates.getRight(), "d4widk")
                .map(SearchResult::getWeatherObservation)
                .map(o -> Pair.of(o.getWindSpeed(), o.getStationName()))
                .toFlowable();
    }

    default Flowable<List<String>> basicWeatherOf(Pair<Double, Double> coordinates) {
        return search(coordinates.getLeft(), coordinates.getRight(), "d4widk")
                .map(SearchResult::getWeatherObservation)
                .map(o -> List.of(o.getWindSpeed(), o.getStationName(), o.getClouds(), o.getTemperature()))
                .toFlowable();
    }

    private void extractedPrivateMethod() {

    }
}
