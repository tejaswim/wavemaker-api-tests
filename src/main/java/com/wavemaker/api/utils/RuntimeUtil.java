package com.wavemaker.api.utils;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:sunil.pulugula@wavemaker.com">Sunil Kumar</a>
 * @since 3/11/16
 */
public class RuntimeUtil {

    private static final Logger logger = LoggerFactory.getLogger(RuntimeUtil.class);

    public static File getRuntimeResource(String path) {
        final URL url = Thread.currentThread().getContextClassLoader().getResource(path);
        File file = null;
        try {
            file = new File(url.toURI());
        } catch (URISyntaxException e) {
            logger.error("Failed to locate {}", path, e);
            throw new RuntimeException("Failed to locate " + path, e);
        }
        return file;
    }
}
