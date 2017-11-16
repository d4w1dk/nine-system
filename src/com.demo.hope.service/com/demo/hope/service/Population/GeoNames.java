package com.demo.hope.service.Population;

import io.reactivex.Flowable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface GeoNames {

    @GET("/searchJSON")
    Single<SearchResult> search(
            @Query("q") String query,
            @Query("maxRows") int maxRows,
            @Query("style") String style,
            @Query("username") String username


    );

    default Flowable<Long> populationOf(String city) {
        return search(city, 1, "LONG", "d4widk")
                .map(SearchResult::getGeonames)
                .map(g -> g.get(0))
                .map(GeoNamesImpl::getPopulation)
                .toFlowable();
    }


}