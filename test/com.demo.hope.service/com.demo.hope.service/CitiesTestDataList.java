package com.demo.hope.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

class CitiesTestDataList {
    static final List<String> EXAMPLE_HANDLES_0 =
            Arrays.asList(
                    "Paris", "Madrid", "Warsaw", "Oslo", "Berlin", "Rome",
                    "Prague", "London", "Dublin", "Hamburg", "Trondheim",
                    "Stavanger", "Brno", "Longyearbyen", "Seoul", "Sydney"
            );


    static final List<String> EXAMPLE_HANDLES_1 =
            Collections.unmodifiableList(Arrays.asList(
                    "Paris", "Madrid", "Warsaw", "Oslo", "Berlin", "Rome",
                    "Prague", "London", "Dublin", "Hamburg", "Trondheim",
                    "Stavanger", "Brno", "Longyearbyen", "Seoul", "Sydney"
            ));

    static final List<String> EXAMPLE_HANDLES_2 =
            List.of("Paris", "Madrid", "Warsaw", "Oslo", "Berlin", "Rome", "Prague", "London", "Dublin", "Hamburg",
                    "Trondheim", "Stavanger", "Brno", "Longyearbyen", "Seoul", "Sydney");


    static final Random random = new Random();

    static String getRandomGeoHandleFromList() {
        return EXAMPLE_HANDLES_2.get(random.nextInt(16));

    }
}
