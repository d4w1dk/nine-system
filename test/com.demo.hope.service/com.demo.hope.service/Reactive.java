//import io.reactivex.Flowable;
//import io.reactivex.subscribers.TestSubscriber;
//import org.apache.commons.lang3.tuple.Pair;
//import org.junit.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.Random;
//import java.util.UUID;
//
//import static java.util.concurrent.TimeUnit.SECONDS;
//
//public class Reactive {
//
//    private static final Logger log = LoggerFactory.getLogger(Reactive.class);
//    private static final ConfigRetrofit configRetrofit = new ConfigRetrofit();
//
//    private static final GeoNames geoNames = configRetrofit.createClient();
//
//    static String slow() throws InterruptedException {
//        log.info("Running");
//        SECONDS.sleep(1);
//        return "abc";
//    }
//
//    Flowable<Pair<String, Long>> populationOfCity(String city) {
//        Flowable<Long> population = geoNames.populationOf(city);
//        return population.map(p -> Pair.of(city, p));
//    }
//
//    @Test
//    public void test_0() throws Exception {
//
//        final Flowable<String> cached =
//                Flowable.fromCallable(Reactive::slow)
//                        .cache();
//        cached.subscribe(
//                x -> {/*Ignore*/ },
//                e -> log.error("Prepopulation error", e)
//        );
//    }
//
//    @Test
//    public void test_1() throws Exception {
//        TestSubscriber<Long> testSubscriber = new TestSubscriber<>();
//
//        Flowable<String> cities = Flowable.just("Warsaw", "Paris");
//
//        GeoNames geoNames = configRetrofit.createClient();
//
//        cities.concatMap(geoNames::populationOf)
//                .doOnNext(r -> log.info("Population: {}", r))
//                .subscribe(testSubscriber);
//
//        testSubscriber.awaitTerminalEvent();
//        testSubscriber.assertValues(1702139L, 2138551L);
//
//    }
//
//    @Test
//    public void test_2() throws Exception {
//        TestSubscriber<Long> testSubscriber = new TestSubscriber<>();
//
//        Flowable<String> cities = Flowable.just("Warsaw", "Paris", "London", "Madrid");
//
//        GeoNames geoNames = configRetrofit.createClient();
//
//        cities.flatMap(geoNames::populationOf)
//                .doOnNext(r -> log.info("Population: {}", r))
//                .subscribe(testSubscriber);
//
//        testSubscriber.awaitTerminalEvent();
//
//    }
//
//    @Test
//    public void test_3() throws Exception {
//        TestSubscriber<Long> testSubscriber = new TestSubscriber<>();
//
//        Flowable<String> cities = Flowable.just("Warsaw", "Paris", "Madrid");
//
//        cities.concatMapEager(geoNames::populationOf)
//                .doOnNext(r -> log.info("Population: {}", r))
//                .subscribe(testSubscriber);
//
//        testSubscriber.awaitTerminalEvent();
//    }
//
//    @Test
//    public void test_4() throws Exception {
//        TestSubscriber<Long> testSubscriber = new TestSubscriber<>();
//
//        Flowable<String> cities = Flowable.just("Warsaw", "Paris", "Madrid");
//
//        cities.flatMap(this::populationOfCity)
//                .doOnNext(r -> log.info("Population: {}", r))
//                .subscribe();
//
//        testSubscriber.awaitTerminalEvent();
//    }
//
//    @Test
//    public void test_5() throws Exception {
//        TestSubscriber<Long> testSubscriber = new TestSubscriber<>();
//
//        Flowable<String> cities = Flowable.just("Warsaw", "Paris", "Madrid");
//
//        cities.flatMap(city -> geoNames.populationOf(city), (city, pop) -> Pair.of(city, pop))
//                .doOnNext(r -> log.info("Population: {}", r))
//                .subscribe();
//
//        testSubscriber.awaitTerminalEvent();
//    }
//
//
//    @Test
//    public void test_6() throws Exception {
//        TestSubscriber<Long> testSubscriber = new TestSubscriber<>();
//
//        Flowable<String> cities = Flowable.just("Warsaw", "Paris", "Madrid");
//
//        cities.flatMap(geoNames::populationOf, Pair::of)
//                .doOnNext(r -> log.info("Population: {}", r))
//                .subscribe();
//
//        testSubscriber.awaitTerminalEvent();
//    }
//
//    @Test
//    public void test_7() throws Exception {
//        TestSubscriber<UUID> testSubscriber = new TestSubscriber<>();
//        Flowable<UUID> ids = Flowable
//                .fromCallable(UUID::randomUUID)
//                .repeat()
//                .take(10);
//
//        ids
//                .doOnNext(r -> log.info("UUID: {}", r.toString()))
//                .subscribe(testSubscriber);
//
//        testSubscriber.awaitTerminalEvent();
//
//    }
//
//    @Test
//    public void test_8() throws Exception {
//
//        Flowable<Long> naturalNumbers =
//                Flowable.generate(() -> 0L, (state, emitter) -> {
//                    emitter.onNext(state);
//                    return state + 1;
//
//                });
//
//        naturalNumbers.subscribe();
//
//    }
//
//    @Test
//    public void test_9() throws Exception {
//
//        Flowable.generate(Random::new, (random, emitter) -> {
//             emitter.onNext(random.nextBoolean());
//        });
//
//
//
//    }
//}
//
