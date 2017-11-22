package com.wavemaker.api.client;

import java.util.List;

import org.apache.http.impl.cookie.BasicClientCookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import com.wavemaker.api.manager.SecurityManager;
import com.wavemaker.api.rest.IRestInput;
import com.wavemaker.api.rest.RestConnectorTemplate;
import com.wavemaker.api.rest.RestInputImpl;
import com.wavemaker.api.rest.models.RestResponse;
import com.wavemaker.api.rest.models.studio.User;


/**
 * Created by harishv on 3/6/2017.
 */
public class LoginClient {
    private static final Logger logger = LoggerFactory.getLogger(LoginClient.class);

    public User login(String url, final User user) {
        logger.info(Thread.currentThread().getName() + " " + Thread.currentThread().getId());
        if (SecurityManager.getAuthCookie() != null) {
            return null;
        }
        IRestInput restInput = new RestInputImpl() {
            @Override
            public Object getPayload() {
                HttpHeaders formData = new HttpHeaders();
                formData.add("j_username", user.getUserName());
                formData.add("j_password", user.getPassword());
                return formData;
            }

            @Override
            public String getContentType() {
                return MediaType.APPLICATION_FORM_URLENCODED_VALUE;
            }
        };

        RestConnectorTemplate restConnectorTemplate = new RestConnectorTemplate();
        final RestResponse response = restConnectorTemplate.execute(HttpMethod.POST, null, url, restInput);
        int statusCode = response.getStatusCode();
        if (statusCode == 302 || statusCode == 200) {
            List<BasicClientCookie> cookies = response.getCookies();
            SecurityManager.setAuthCookie(buildCookie(cookies));
            return user;
        } else if (statusCode == 401) {
            throw new RuntimeException("Unable to login with user " + user.getUserName());
        } else {
            throw new RuntimeException("Unable to login with user " + user.getUserName() + " : status code " +
                    "received is : " + statusCode);
        }

    }

    private static String buildCookie(final List<BasicClientCookie> cookies) {
        StringBuilder stringBuilder = new StringBuilder();
        boolean first = true;
        for (BasicClientCookie cookie : cookies) {
            if (!first) {
                stringBuilder.append("; ");
            }
            stringBuilder.append(cookie.getName()).append("=").append(cookie.getValue());
            first = false;
        }
        return stringBuilder.toString();
    }
}
