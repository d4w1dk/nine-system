package com.demo.hope.service;

import javax.sql.rowset.BaseRowSet;
import java.util.HashMap;
import java.util.Map;

import static com.demo.hope.service.CitiesCoordniates.*;

class CityTestDataMap {
    private static final Map<String, CitiesCoordniates> CITY_FOR_WEATHER = new HashMap<>();

    static {
        CITY_FOR_WEATHER.put("Warszawa",  WARSAW);
        CITY_FOR_WEATHER.put("Madryt", MADRID);
        CITY_FOR_WEATHER.put("Gdynia", GDYNIA);
        CITY_FOR_WEATHER.put("Sopot", SOPOT);
        CITY_FOR_WEATHER.put("Paris", PARIS);
        CITY_FOR_WEATHER.put("Oslo", OSLO);
        CITY_FOR_WEATHER.put("Berlin", BERLIN);
        CITY_FOR_WEATHER.put("Rome", ROME);
        CITY_FOR_WEATHER.put("Prague", PRAGUE);
        CITY_FOR_WEATHER.put("London", LONDON);
        CITY_FOR_WEATHER.put("Dublin", DUBLIN);
        CITY_FOR_WEATHER.put("Hamburg", HAMBURG);
        CITY_FOR_WEATHER.put("Stavanger", STAVANGER);
        CITY_FOR_WEATHER.put("Trondheim", TRONDHEIM);
        CITY_FOR_WEATHER.put("Brno", BRNO);
        CITY_FOR_WEATHER.put("Longyearbyen",LONGYEARBYEN );
        CITY_FOR_WEATHER.put("Seoul",SEOUL);
        CITY_FOR_WEATHER.put("Sydney", SYDNEY);
    }

}
