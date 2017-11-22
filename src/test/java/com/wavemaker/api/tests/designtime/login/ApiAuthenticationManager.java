package com.wavemaker.api.tests.designtime.login;

/**
 * Created by Tejaswi Maryala on 11/21/2017.
 */
public class ApiAuthenticationManager extends AuthenticationManager {
    /**
     * Creates a user builder object where all the users from the test users file are stored in cache
     *
     * @param filePath
     * @param noOfUsers
     */
    public ApiAuthenticationManager(final String filePath, final int noOfUsers) {
        super(filePath, noOfUsers);
    }
}
