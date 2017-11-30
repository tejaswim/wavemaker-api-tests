package com.wavemaker.api.tests.builder;


import org.apache.commons.lang3.RandomStringUtils;

import com.wavemaker.api.rest.models.database.sampledb.User;

/**
 * Created by Tejaswi Maryala on 11/30/2017.
 */
public class SampleDBObjectsBuilder {

    public static User buildUser() {
        User user = new User();
        user.setUserId(Integer.valueOf(RandomStringUtils.randomNumeric(3)));
        user.setPassword(RandomStringUtils.randomAlphabetic(5));
        user.setRole(RandomStringUtils.randomAlphabetic(5));
        user.setUsername(RandomStringUtils.randomAlphabetic(5));
        user.setTenantId(Integer.valueOf(RandomStringUtils.randomNumeric(1)));
        return user;
    }

    public static User buildUser(Integer pkColumn) {
        User user = new User();
        user.setUserId(pkColumn);
        user.setPassword(RandomStringUtils.randomAlphabetic(5));
        user.setRole(RandomStringUtils.randomAlphabetic(5));
        user.setUsername(RandomStringUtils.randomAlphabetic(5));
        user.setTenantId(Integer.valueOf(RandomStringUtils.randomNumeric(1)));
        return user;
    }
}
