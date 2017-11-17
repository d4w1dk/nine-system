package com.demo.hope.service;

import org.apache.commons.lang3.tuple.Pair;

enum CitiesCoordniates {
    GDYNIA(Pair.of(54.51, 18.53)),
    SOPOT(Pair.of(54.44, 18.56)),
    PARIS(Pair.of(48.85, 2.34)),
    MADRID(Pair.of(40.41, -3.70)),
    WARSAW(Pair.of(52.22, 21.01)),
    OSLO(Pair.of(59.91, 10.74)),
    BERLIN(Pair.of(52.52, 13.41)),
    ROME(Pair.of(41.89, 12.51)),
    PRAGUE(Pair.of(50.08, 14.42)),
    LONDON(Pair.of(51.50, -0.12)),
    DUBLIN(Pair.of(53.33, -6.24)),
    HAMBURG(Pair.of(53.57, 10.01)),
    STAVANGER(Pair.of(58.97, 5.733)),
    TRONDHEIM(Pair.of(63.43, 10.39)),
    BRNO(Pair.of(49.19, 16.60)),
    LONGYEARBYEN(Pair.of(78.22, 15.64)),
    SEOUL(Pair.of(37.56, 126.97)),
    SYDNEY(Pair.of(-33.86, 151.20)),
    AMSTERDAM(null);


    private Pair<Double, Double> coordinates;

    CitiesCoordniates(Pair cords) {
        this.coordinates = cords;
    }

    public Pair<Double, Double> getCoordinates() {
        return coordinates;
    }
}
