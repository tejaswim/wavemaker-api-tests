package com.wavemaker.api.login;

import com.wavemaker.api.client.LoginClient;
import com.wavemaker.api.config.StudioTestConfig;
import com.wavemaker.api.rest.models.studio.User;

/**
 * Created by Tejaswi Maryala on 11/21/2017.
 */
public class ApiAuthenticationManager extends AuthenticationManager {
    private LoginClient loginClient = new LoginClient();

    /**
     * Creates a user builder object where all the users from the test users file are stored in cache
     *
     * @param filePath
     * @param noOfUsers
     */
    public ApiAuthenticationManager(final String filePath, final int noOfUsers) {
        super(filePath, noOfUsers);
    }

    @Override
    public User login(final User user) {
        final String baseUrl = StudioTestConfig.getInstance().getUrl();
        final String url = baseUrl + "/login/authenticate";
        return loginClient.login(url, user);
    }
}
