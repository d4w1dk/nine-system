package com.demo.hope.service;

import com.demo.hope.config.ConfigRetrofit;
import com.demo.hope.service.Population.GeoNames;
import com.demo.hope.service.Weather.GeoWeather;
import io.reactivex.Flowable;
import io.reactivex.subscribers.TestSubscriber;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.Callable;

import static com.demo.hope.service.CitiesCoordniates.*;
import static com.demo.hope.service.CityTestDataList.getRandomGeoHandleFromList;
import static java.util.concurrent.TimeUnit.SECONDS;

public class ReactiveTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReactiveTest.class);
    private static final ConfigRetrofit CONFIG_RETROFIT = new ConfigRetrofit();

    private static final GeoNames RETROFIT_CLIENT_NAMES = CONFIG_RETROFIT.createNamesClient();
    private static final GeoWeather RETROFIT_CLIENT_WEATHER = CONFIG_RETROFIT.createWeatherClient();


    static String slow() throws InterruptedException {
        LOGGER.info("Running");
        SECONDS.sleep(1);
        return "abc";
    }

    Flowable<Pair<String, Long>> populationOfCity(String city) {
        Flowable<Long> population = RETROFIT_CLIENT_NAMES.populationOf(city);
        return population.map(p -> Pair.of(city, p));
    }

    @Test
    public void test_0() throws Exception {

        final Flowable<String> cached =
                Flowable.fromCallable(ReactiveTest::slow)
                        .cache();
        cached.subscribe(
                x -> {/*Ignore*/ },
                e -> LOGGER.error("Prepopulation error", e)
        );
    }

    @Test
    public void test_1() throws Exception {


        TestSubscriber<Long> testSubscriber = new TestSubscriber<>();

        Flowable<String> cities = Flowable.just("Warsaw", "Paris");

        GeoNames geoNames = CONFIG_RETROFIT.createNamesClient();

        cities.concatMap(geoNames::populationOf)
                .doOnNext(r -> LOGGER.info("Population: {}", r))
                .subscribe(testSubscriber);

        testSubscriber.awaitTerminalEvent();
        testSubscriber.assertValues(1702139L, 2138551L);

    }

    @Test
    public void test_2() throws Exception {
        TestSubscriber<Long> testSubscriber = new TestSubscriber<>();

        Flowable<String> cities = Flowable.just("Warsaw", "Paris", "London", "Madrid");

        GeoNames geoNames = CONFIG_RETROFIT.createNamesClient();

        cities.flatMap(geoNames::populationOf)
                .doOnNext(r -> LOGGER.info("Population: {}", r))
                .subscribe(testSubscriber);

        testSubscriber.awaitTerminalEvent();

    }

    @Test
    public void test_3() throws Exception {
        TestSubscriber<Long> testSubscriber = new TestSubscriber<>();

        Flowable<String> cities = Flowable.just("Warsaw", "Paris", "Madrid");

        cities.concatMapEager(RETROFIT_CLIENT_NAMES::populationOf)
                .doOnNext(r -> LOGGER.info("Population: {}", r))
                .subscribe(testSubscriber);

        testSubscriber.awaitTerminalEvent();
    }

    @Test
    public void test_4() throws Exception {
        TestSubscriber<Pair<String, Long>> testSubscriber = new TestSubscriber<>();

        Flowable<String> cities = Flowable.just("Warsaw", "Paris", "Madrid");

        cities.flatMap(this::populationOfCity)
                .doOnNext(r -> LOGGER.info("Population: {}", r))
                .subscribe(testSubscriber);

        testSubscriber.awaitTerminalEvent();
    }

    @Test
    public void test_5() throws Exception {
        TestSubscriber<Pair<String, Long>> testSubscriber = new TestSubscriber<>();

        Flowable<String> cities = Flowable.just("Warsaw", "Paris", "Madrid");

        cities.flatMap(city -> RETROFIT_CLIENT_NAMES.populationOf(city), (city, pop) -> Pair.of(city, pop))
                .doOnNext(r -> LOGGER.info("Population: {}", r))
                .subscribe(testSubscriber);

        testSubscriber.awaitTerminalEvent();
    }


    @Test
    public void test_6() throws Exception {
        TestSubscriber<Pair<String, Long>> testSubscriber = new TestSubscriber<>();

        Flowable<String> cities = Flowable.just("Warsaw", "Paris", "Madrid");

        cities.flatMap(RETROFIT_CLIENT_NAMES::populationOf, Pair::of)
                .doOnNext(r -> LOGGER.info("Population: {}", r))
                .subscribe(testSubscriber);

        testSubscriber.awaitTerminalEvent();
    }

    @Test
    public void test_7() throws Exception {
        TestSubscriber<UUID> testSubscriber = new TestSubscriber<>();
        Flowable<UUID> ids = Flowable
                .fromCallable(UUID::randomUUID)
                .repeat()
                .take(10);

        ids
                .doOnNext(r -> LOGGER.info("UUID: {}", r.toString()))
                .subscribe(testSubscriber);

        testSubscriber.awaitTerminalEvent();

    }

    @Test
    public void test_8() throws Exception {

        Flowable<Long> naturalNumbers =
                Flowable.generate(() -> 0L, (state, emitter) -> {
                    emitter.onNext(state);
                    return state + 1;

                });

        naturalNumbers.subscribe();

    }

    @Test
    public void test_9() throws Exception {

        Flowable.generate(Random::new, (random, emitter) -> {
            emitter.onNext(random.nextBoolean());
        });
    }

    @Test
    public void test_10() throws Exception {
        TestSubscriber<Pair<String, Long>> testSubscriber = new TestSubscriber<>();

        Flowable<String> cities = Flowable.just(getRandomGeoHandleFromList());

        cities.flatMap(RETROFIT_CLIENT_NAMES::populationOf, Pair::of)
                .doOnNext(r -> LOGGER.info("Population: {}", r))
                .subscribe(testSubscriber);

        testSubscriber.awaitTerminalEvent();
        testSubscriber.assertValueCount(1);
    }

    @Test
    public void test_11() throws Exception {
        TestSubscriber<Pair<Pair<Double, Double>, Pair<String, String>>> testSubscriber = new TestSubscriber<>();

        Flowable<Pair<Double, Double>> coordinates = Flowable.just(WARSAW.getCoordinates());

        coordinates.flatMap(RETROFIT_CLIENT_WEATHER::weatherOf, Pair::of)
                .doOnNext(r -> LOGGER.info("Wind (knots) and weather station name for coordinates: {}", r))
                .subscribe(testSubscriber);

        testSubscriber.awaitTerminalEvent();
        testSubscriber.assertValueCount(1);
    }

    @Test
    public void test_12() throws Exception {
        TestSubscriber<Map<Pair<Double, Double>, Pair<String, String>>> testSubscriber = new TestSubscriber<>();

        Flowable <Pair<Double, Double>> coordinates = Flowable.just(GDYNIA.getCoordinates());

        coordinates.flatMap(RETROFIT_CLIENT_WEATHER::weatherOf, Map::of)
                .doOnNext(r -> LOGGER.info("Wind (knots) and weather station name for coordinates: {}", r))
                .subscribe(testSubscriber);

        testSubscriber.awaitTerminalEvent();
        testSubscriber.assertValueCount(1);
    }


    }




