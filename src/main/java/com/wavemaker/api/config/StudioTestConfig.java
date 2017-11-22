package com.wavemaker.api.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wavemaker.api.utils.RuntimeUtil;

public class StudioTestConfig {

    private static final Logger logger = LoggerFactory.getLogger(StudioTestConfig.class);
    private static final String CONFIG_PROPERTIES = "config.properties";
    private static final String CONFIG_PROPERTIES_LOCAL = "config.properties.local";
    private Properties props;

    private StudioTestConfig() {
        init();
    }

    private static class Holder {
        private static final StudioTestConfig CONFIGURATOR_SUPPORT = new StudioTestConfig();
    }

    public static StudioTestConfig getInstance() {
        return Holder.CONFIGURATOR_SUPPORT;
    }


    public String getProperty(String strKey) {
        return props.getProperty(strKey);
    }

    private void init() {
        final Optional<Properties> globalProperties = readProperties(CONFIG_PROPERTIES);
        final Optional<Properties> localProperties = readProperties(CONFIG_PROPERTIES_LOCAL);
        props = globalProperties.orElse(new Properties());
        localProperties.ifPresent(props::putAll);
    }

    private Optional<Properties> readProperties(final String filePath) {
        FileInputStream in = null;
        final URL resource = this.getClass().getClassLoader().getResource(filePath);
        try {
            if (resource != null) {
                File f = new File(resource.toURI());
                if (f.exists()) {
                    in = new FileInputStream(f);
                    Properties properties = new Properties();
                    properties.load(in);
                    return Optional.of(properties);
                } else {
                    logger.error("File {} not found!", filePath);
                }
            }
        } catch (URISyntaxException | IOException e) {
            logger.error("Failed to read {}", filePath, e);
            throw new RuntimeException(e);
        } finally {
            IOUtils.closeQuietly(in);
        }
        return Optional.empty();
    }

    public String getUrl() {
        return getProperty("baseurl");
    }

    public String getInvocationCount() {
        return getProperty("invocationcount");
    }

    private List<CSVRecord> getUserRecords() {
        final File runtimeResource = RuntimeUtil.getRuntimeResource(getUsersCSV());
        return getUserRecords(runtimeResource);
    }

    private static List<CSVRecord> getUserRecords(final File sanityCSVFile) {
        try {
            CSVParser parser = CSVFormat.EXCEL.withHeader().parse(new FileReader(sanityCSVFile));
            return parser.getRecords();
        } catch (IOException e) {
            throw new RuntimeException("Failed to read " + sanityCSVFile, e);
        }
    }

    public String getUsersCSV() {
        return getProperty("testUsers");
    }

    public Integer getNoOfUsers() {
        List<CSVRecord> usersCSV = getUserRecords();
        return usersCSV.size();
    }
}