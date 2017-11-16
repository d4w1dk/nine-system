package com.demo.hope.service.Weather;


import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;
import org.apache.commons.lang3.tuple.Pair;

public interface GeoWeather {

    @GET("/findNearByWeatherJSON")
    Single<SearchResult> search(
            @Query("lat") Double lat,
            @Query("lng") Double lng,
            @Query("username") String username


    );

    //TODO List type is not needed here query response is Single
    default Flowable<Pair<String, String>> weatherOf(Pair<Double,Double> coordinates) {
        return search(coordinates.getLeft(), coordinates.getRight(), "d4widk")
                .map(SearchResult::getWeatherObservation)
                .map(o -> Pair.of(o.getWindSpeed(), o.getStationName()))
                .toFlowable();
    }


}
