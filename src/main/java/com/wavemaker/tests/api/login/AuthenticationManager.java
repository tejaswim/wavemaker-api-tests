package com.wavemaker.tests.api.login;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wavemaker.tests.api.client.LoginClient;
import com.wavemaker.tests.api.config.StudioTestConfig;
import com.wavemaker.tests.api.rest.models.studio.User;
import com.wavemaker.tests.api.rest.models.studio.UsersBuilder;

/**
 * @author <a href="mailto:sunil.pulugula@wavemaker.com">Sunil Kumar</a>
 * @since 3/11/16
 */
public abstract class AuthenticationManager {

    private final UsersBuilder usersBuilder;
    private static volatile AtomicInteger i = new AtomicInteger(0);
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationManager.class);
    private LoginClient loginClient = new LoginClient();

    /**
     * Creates a user builder object where all the users from the test users file are stored in cache
     */

    public AuthenticationManager(String filePath, int noOfUsers) {
        usersBuilder = new UsersBuilder(filePath, noOfUsers);
    }

    /**
     * Logs in to studio with users taken from user cache
     */
    public User login() {
        final User user = getUser();
        final String baseUrl = StudioTestConfig.getInstance().getUrl();
        final String url = baseUrl + "/login/authenticate";
        return loginClient.login(url, user);
    }

    private User getUser() {
        List<User> users = usersBuilder.getCachedUsers();
        int j = (i.incrementAndGet()) % users.size();
        final User user = users.get(j);
        LOGGER.info("User index is {} and user name is {}", j, user.getUserName());
        return user;
    }

    public abstract User login(User user);
}
