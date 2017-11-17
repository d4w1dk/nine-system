package com.demo.hope.service.Weather;

class GeoWeatherImpl {
    private String windSpeed;
    private String stationName;
    private Long elevation;
    private String temperature;
    private String clouds;

    public String getWindSpeed() {
        return windSpeed;
    }

    public String getStationName() {
        return stationName;
    }

    public String getClouds() {
        return clouds;
    }

    public String getTemperature() {
        return temperature;
    }
}

class SearchResult {
    private GeoWeatherImpl weatherObservation = new GeoWeatherImpl();

    public GeoWeatherImpl getWeatherObservation() {
        return weatherObservation;
    }
}

