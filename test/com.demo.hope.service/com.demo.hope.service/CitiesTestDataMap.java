package com.demo.hope.service;

import com.demo.hope.service.Weather.GeoWeather;
import io.reactivex.Flowable;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static com.demo.hope.service.CitiesCoordniates.*;
import static java.util.Map.*;

class CitiesTestDataMap {
    private static final Map<String, CitiesCoordniates> CITY_FOR_WEATHER = new HashMap<>();

    private static final Map<String, CitiesCoordniates> CITY_FOR_WEATHER_2 = Map.of(
            "warsaw", WARSAW,
            "seoul", SEOUL,
            "sydney", SYDNEY,
            "madryt", MADRID,
            "gdynia", GDYNIA,
            "sopot", SOPOT,
            "paris", PARIS,
            "oslo", OSLO,
            "berlin", BERLIN,
            "longyearbyen", LONGYEARBYEN);


    private static final Map<String, CitiesCoordniates> CITY_FOR_WEATHER_1 =
  ofEntries(
            entry("warsaw", WARSAW),
            entry("madryt", MADRID),
            entry("gdynia", GDYNIA),
            entry("sopot", SOPOT),
            entry("paris", PARIS),
            entry("oslo", OSLO),
            entry("berlin", BERLIN),
            entry("rome", ROME),
            entry("prague", PRAGUE),
            entry("london", LONDON),
            entry("dublin", DUBLIN),
            entry("hamburg", HAMBURG),
            entry("stavanger", STAVANGER),
            entry("trondheim", TRONDHEIM),
            entry("brno", BRNO),
            entry("longyearbyen", LONGYEARBYEN),
            entry("seoul", SEOUL),
            entry("sydney", SYDNEY));

    static {
        CITY_FOR_WEATHER.put("warsaw", WARSAW);
        CITY_FOR_WEATHER.put("madryt", MADRID);
        CITY_FOR_WEATHER.put("gdynia", GDYNIA);
        CITY_FOR_WEATHER.put("sopot", SOPOT);
        CITY_FOR_WEATHER.put("paris", PARIS);
        CITY_FOR_WEATHER.put("oslo", OSLO);
        CITY_FOR_WEATHER.put("berlin", BERLIN);
        CITY_FOR_WEATHER.put("rome", ROME);
        CITY_FOR_WEATHER.put("prague", PRAGUE);
        CITY_FOR_WEATHER.put("london", LONDON);
        CITY_FOR_WEATHER.put("dublin", DUBLIN);
        CITY_FOR_WEATHER.put("hamburg", HAMBURG);
        CITY_FOR_WEATHER.put("stavanger", STAVANGER);
        CITY_FOR_WEATHER.put("trondheim", TRONDHEIM);
        CITY_FOR_WEATHER.put("brno", BRNO);
        CITY_FOR_WEATHER.put("longyearbyen", LONGYEARBYEN);
        CITY_FOR_WEATHER.put("seoul", SEOUL);
        CITY_FOR_WEATHER.put("sydney", SYDNEY);
    }

    static Flowable<Pair<Double, Double>> coordinatesForWords(Flowable<String> words) {
        return words.map(String::toLowerCase)
                .filter(CITY_FOR_WEATHER::containsKey)
                .map(CITY_FOR_WEATHER::get)
                .map(CitiesCoordniates::getCoordinates);
    }

    static Optional<Pair<Double,Double>> getCoordinates(String key){
        final Optional<Pair<Double,Double>> optional =
                Optional.ofNullable(CITY_FOR_WEATHER.get(key).getCoordinates());

        optional.stream();

        optional.or(() -> getAnotherOptional(key));

        optional.ifPresentOrElse(GeoWeather::doSomething, GeoWeather::doSomethingElse) ;

        return optional;
    }

    private static Optional<Pair<Double,Double>> getAnotherOptional(String key) {
        return Optional.ofNullable(CITY_FOR_WEATHER_1.get(key).getCoordinates());

    }


}
