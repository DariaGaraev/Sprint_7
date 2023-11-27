package com.example.data;

import org.apache.commons.lang3.RandomStringUtils;

public class CourierGenerator {

    public static CourierData getRandomCourier() {
        String login = RandomStringUtils.randomAlphabetic(10);
        String password = RandomStringUtils.randomAlphabetic(10);
        String firstName = RandomStringUtils.randomAlphabetic(10);

        return new CourierData(login, password, firstName);
    }
}


