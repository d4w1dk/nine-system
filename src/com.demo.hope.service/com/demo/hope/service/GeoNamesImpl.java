package com.demo.hope.service;

import java.util.ArrayList;
import java.util.List;


class GeoNamesImpl {
    private double lat;

    public Long getPopulation() {
        return population;
    }

    private double lng;
    private Integer geonameId;
    private Long population;
    private String countryCode;
    private String name;


}


class SearchResult{
    public List<GeoNamesImpl> getGeonames() {
        return geonames;
    }

    private List<GeoNamesImpl> geonames = new ArrayList<>();
}
