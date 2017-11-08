package com.demo.hope.service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static java.util.Arrays.asList;

class CityTestDataSet {
    static final Set<String> EXAMPLE_HANDLES_0
            = Collections.unmodifiableSet(new HashSet<>(asList(
            "Paris", "Madrid", "Warsaw", "Oslo", "Berlin", "Rome",
            "Prague", "London", "Dublin", "Hamburg", "Trondheim",
            "Stavanger", "Brno", "Longyearbyen", "Seoul", "Sydney"
    )));

    static final Set<String> EXAMPLE_HANDLES_1
            = Set.of("Paris", "Madrid", "Warsaw", "Oslo", "Berlin", "Rome", "Prague", "London", "Dublin", "Hamburg",
            "Trondheim", "Stavanger", "Brno", "Longyearbyen", "Seoul", "Sydney");

}
