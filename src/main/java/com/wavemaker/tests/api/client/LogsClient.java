package com.wavemaker.tests.api.client;

import com.wavemaker.tests.api.wrapper.StringWrapper;

/**
 * Created by tejaswim on 6/24/2016.
 */
public class LogsClient extends BaseClient {

    private static final String SERVER_LOGS_URL = "/studio/services/studio/logs/server/1000";
    private static final String APPLICATION_LOGS_URL = "/studio/services/studio/logs/application/1000";

    public String getServerLogs() {
        String url = constructUrl(SERVER_LOGS_URL);
        StringWrapper stringWrapper = get(url, StringWrapper.class);
        return stringWrapper.getResult();
    }

    public String getApplicationLogs() {
        String url = constructUrl(APPLICATION_LOGS_URL);
        StringWrapper stringWrapper = get(url, StringWrapper.class);
        return stringWrapper.getResult();
    }
}
