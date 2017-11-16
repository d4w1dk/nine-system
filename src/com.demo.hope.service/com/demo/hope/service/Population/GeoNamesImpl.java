package com.demo.hope.service.Population;

import java.util.ArrayList;
import java.util.List;


class GeoNamesImpl {
    public Long getPopulation() {
        return population;
    }

    private double lat;
    private double lng;
    private Integer geonameId;
    private Long population;
    private String countryCode;
    private String name;


}


class SearchResult{
    private List<GeoNamesImpl> geonames = new ArrayList<>();

    public List<GeoNamesImpl> getGeonames() {
        return geonames;
    }
}
