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

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.demo.hope.service.CitiesCoordniates.GDYNIA;
import static com.demo.hope.service.CitiesCoordniates.WARSAW;
import static com.demo.hope.service.CitiesTestDataList.getRandomGeoHandleFromList;
import static com.demo.hope.service.CitiesTestDataMap.coordinatesForWords;
import static java.lang.System.out;
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

    private static List<String> walkAndFilterStackframe() {
        return StackWalker.getInstance().walk(s ->
                s.map(frame -> frame.getClassName() + "/" + frame.getMethodName())
                        .filter(name -> name.startsWith("com.demo.hope"))
                        .limit(10)
                        .collect(Collectors.toList())
        );
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

        coordinates.flatMap(RETROFIT_CLIENT_WEATHER::windOf, Pair::of)
                .doOnNext(r -> LOGGER.info("Wind (knots) and weather station name for coordinates: {}", r))
                .subscribe(testSubscriber);

        testSubscriber.awaitTerminalEvent();
        testSubscriber.assertValueCount(1);
    }

    @Test
    public void test_12() throws Exception {
        TestSubscriber<Map<Pair<Double, Double>, Pair<String, String>>> testSubscriber = new TestSubscriber<>();

        Flowable<Pair<Double, Double>> coordinates = Flowable.just(GDYNIA.getCoordinates());

        coordinates.flatMap(RETROFIT_CLIENT_WEATHER::windOf, Map::of)
                .doOnNext(r -> LOGGER.info("Wind (knots) and weather station name for coordinates: {}", r))
                .subscribe(testSubscriber);

        testSubscriber.awaitTerminalEvent();
        testSubscriber.assertValueCount(1);
    }

    @Test
    public void test_13() throws Exception {
        TestSubscriber<Map<Pair<Double, Double>, Pair<String, String>>> testSubscriber = new TestSubscriber<>();

        Flowable<String> cities = Flowable.just("Longyearbyen", "Sydney");

        Flowable<Pair<Double, Double>> coordinates = coordinatesForWords(cities);

        coordinates.flatMap(RETROFIT_CLIENT_WEATHER::windOf, Map::of)
                .doOnNext(r -> LOGGER.info("Wind (knots) and weather station name for coordinates: {}", r))
                .subscribe(testSubscriber);

        testSubscriber.awaitTerminalEvent();
        testSubscriber.assertValueCount(2);
    }

    @Test
    public void test_14() throws Exception {
        TestSubscriber<Map<Pair<Double, Double>, List<String>>> testSubscriber = new TestSubscriber<>();

        Flowable<String> cities = Flowable.just("Longyearbyen", "Sydney");

        Flowable<Pair<Double, Double>> coordinates = coordinatesForWords(cities);

        coordinates.flatMap(RETROFIT_CLIENT_WEATHER::basicWeatherOf, Map::of)
                .doOnNext(r -> LOGGER.info("Basic weather for coordinates: {}", r))
                .subscribe(testSubscriber);

        testSubscriber.awaitTerminalEvent();
        testSubscriber.assertValueCount(2);
    }

    @Test
    public void test_15() throws Exception {
        TestSubscriber<Map<Pair<Double, Double>, List<String>>> testSubscriber = new TestSubscriber<>();

        Stream<String> cities = Stream.of("London", "Madrid");

        /**(Resources c1 = cities) */

        try (cities) {
            Set<Pair<Double, Double>> cityCoord = cities.
                    map(s -> CitiesCoordniates.valueOf(s.toUpperCase()).getCoordinates())
                    .collect(Collectors.toSet());

            Flowable<Pair<Double, Double>> coordinates = Flowable.just(cityCoord.iterator().next());

            coordinates.flatMap(RETROFIT_CLIENT_WEATHER::basicWeatherOf, Map::of)
                    .doOnNext(r -> LOGGER.info("Basic weather for coordinates: {}", r))
                    .subscribe(testSubscriber);

            testSubscriber.awaitTerminalEvent();
            testSubscriber.assertValueCount(1);
        }
    }

    @Test
    public void test_16() throws Exception {
        TestSubscriber<Map<Pair<Double, Double>, List<String>>> testSubscriber = new TestSubscriber<>();

        Stream<String> cities = Stream.of("Madrid", "");

        Set<Pair<Double, Double>> cityCoord = cities
                .takeWhile(s -> !s.isEmpty())
                .map(s -> CitiesCoordniates.valueOf(s.toUpperCase()).getCoordinates())
                .collect(Collectors.toSet());

        Flowable<Pair<Double, Double>> coordinates = Flowable.just(cityCoord.iterator().next());

        coordinates.flatMap(RETROFIT_CLIENT_WEATHER::basicWeatherOf, Map::of)
                .doOnNext(r -> LOGGER.info("Basic weather for coordinates: {}", r))
                .subscribe(testSubscriber);

        testSubscriber.awaitTerminalEvent();
        testSubscriber.assertValueCount(1);
    }

    @Test
    public void test_17() throws Exception {
        TestSubscriber<Map<Pair<Double, Double>, List<String>>> testSubscriber = new TestSubscriber<>();

        Stream<String> cities = Stream.of("", "Oslo");

        Set<Pair<Double, Double>> cityCoord = cities
                .dropWhile(s -> {
                    LOGGER.info("Droped " + s);
                    return s.isEmpty();
                })
                .peek(s -> out.println("Take after " + s))
                .map(s -> CitiesCoordniates.valueOf(s.toUpperCase()).getCoordinates())
                .collect(Collectors.toSet());

        Flowable<Pair<Double, Double>> coordinates = Flowable.just(cityCoord.iterator().next());

        coordinates.flatMap(RETROFIT_CLIENT_WEATHER::basicWeatherOf, Map::of)
                .doOnNext(r -> LOGGER.info("Basic weather for coordinates: {}", r))
                .subscribe(testSubscriber);

        testSubscriber.awaitTerminalEvent();
        testSubscriber.assertValueCount(1);
    }

    @Test
    public void test_18() throws Exception {
        TestSubscriber<Map<Pair<Double, Double>, List<String>>> testSubscriber = new TestSubscriber<>();

        Stream<String> cities = Stream.of("Amsterdam", "Oslo");

        Set<Pair<Double, Double>> cityCoord = cities
                .flatMap(element -> Stream.ofNullable(CitiesCoordniates.valueOf(element.toUpperCase()).getCoordinates()))
                .collect(Collectors.toSet());

        Flowable<Pair<Double, Double>> coordinates = Flowable.just(cityCoord.iterator().next());

        coordinates.flatMap(RETROFIT_CLIENT_WEATHER::basicWeatherOf, Map::of)
                .doOnNext(r -> LOGGER.info("Basic weather for coordinates: {}", r))
                .subscribe(testSubscriber);

        testSubscriber.awaitTerminalEvent();
        testSubscriber.assertValueCount(1);
    }

    @Test
    public void test_19() throws Exception {
        walkAndFilterStackframe().forEach(out::println);
    }

    @Test
    public void test_20() throws Exception {
        ProcessHandle.allProcesses()
                .map(ProcessHandle::info)
                .filter(processInfo -> processInfo.user()
                        .filter(user -> user.equals("d.kubicki"))
                        .isPresent())
                .filter(processInfo -> processInfo.command()
                        .filter(command -> command.contains("java"))
                        .isPresent())

                .forEach(out::println);
    }

    @Test
    public void test_21() throws Exception {
        /** Depreciated method marked as error in IDE */
        Thread.currentThread().stop(new RuntimeException());
    }

    @Test
    public void test_22() throws Exception {

    }


}









