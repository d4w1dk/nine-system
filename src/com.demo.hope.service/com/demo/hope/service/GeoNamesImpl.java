package com.demo.hope.service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

class GeoNamesImpl {

    private static final Logger LOGGER = Logger.getLogger(GeoNamesImpl.class.getName());

    private double lat;
    private double lng;
    private Integer geonameId;
    private Long population;
    private String countryCode;
    //name field between before after underscore is ok
    private String _;

    public Long getPopulation() {
        return population;
    }


}


class SearchResult {
    private List<GeoNamesImpl> geonames = new ArrayList<>();

    public List<GeoNamesImpl> getGeonames() {
        return geonames;
    }
}
