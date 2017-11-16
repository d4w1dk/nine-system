module com.demo.hope.client {
    requires reactive.streams;
    requires jackson.databind;
    requires jul.to.slf4j;
    requires logging.interceptor;
    requires converter.jackson;
    requires com.demo.hope;
    requires okhttp;
    requires retrofit;
    requires java.base;
    requires java.logging;
    requires com.demo.hope.service;

    uses com.demo.hope.service.Population.GeoNames;
    uses com.demo.hope.service.Weather.GeoWeather;


}