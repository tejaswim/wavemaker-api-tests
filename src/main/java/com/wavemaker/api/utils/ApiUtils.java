package com.wavemaker.api.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

/**
 * Created by naveen.
 * On  18-02-2016
 * At 17:39
 * For com.wavemaker.test.studio.framework.api.utils for Studio UI Automation
 */
public class ApiUtils {
    // For getting a file in test data as String
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiUtils.class);

    public static String getFileasString(String relativePath) {
        final URL resourceAsStream = Thread.currentThread().getContextClassLoader().getResource(relativePath);
        File file = null;
        StringBuilder out = null;
        InputStream in = null;
        try {
            in = new FileInputStream(new File(resourceAsStream.toURI()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            out = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                out.append(line);
            }
        } catch (URISyntaxException e) {
            LOGGER.info("Failed to locate " + relativePath, e);
        } catch (IOException e) {
            LOGGER.info(e.getMessage());
        }finally {
            IOUtils.closeQuietly(in);
        }
        return out.toString();
    }


    public static String generateRandomString(int length) {
        return RandomStringUtils.randomAlphabetic(length);
    }

    public static int getRandomNumber(int limit) {
        Random randomGenerator = new Random();
        return randomGenerator.nextInt(limit);
    }

    public static String getFileAsStringWithLines(String relativePath) {
        final URL resourceAsStream = Thread.currentThread().getContextClassLoader().getResource(relativePath);
        File file = null;
        StringBuilder out = null;
        InputStream in = null;
        try {
            in = new FileInputStream(new File(resourceAsStream.toURI()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder contents = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                contents.append(line);
                contents.append("\n");
            }
            out = contents;
        } catch (URISyntaxException e) {
            LOGGER.info("Failed to locate " + relativePath, e);
        } catch (IOException e) {
            LOGGER.info(e.getMessage());
        }finally {
            IOUtils.closeQuietly(in);
        }
        return out.toString();
    }

    public static String getFileAsString(String relativePath) {
        File file = null;
        final URL resourceAsStream = Thread.currentThread().getContextClassLoader().getResource(relativePath);
        try {

            file = new File(resourceAsStream.toURI());


        } catch (URISyntaxException e) {
            LOGGER.info("Failed to locate " + relativePath, e);
        }
        return file.toString();
    }

    public static File getFile(String relativePath) {
        final URL resourceAsStream = Thread.currentThread().getContextClassLoader().getResource(relativePath);
        File file = null;
        try {
            file = new File(resourceAsStream.toURI());
        } catch (URISyntaxException e) {

            throw new RuntimeException("Failed to locate " + relativePath, e);
        }
        return file;
    }

    public static String getZipFilePath(String appName) {
        try {
            ClassPathResource file = new ClassPathResource("applications/" + appName);
            File tempDirectory = FileUtils.getTempDirectory();
            File tempJarDirectory = new File(tempDirectory, UUID.randomUUID().toString());
            tempJarDirectory.mkdirs();
            File tempFile = new File(tempJarDirectory, appName);
            FileUtils.copyInputStreamToFile(file.getInputStream(), tempFile);
            return tempFile.getAbsolutePath();
        } catch (IOException e) {
            throw new RuntimeException("Failed to get file path for AppName = " + appName, e);
        }
    }

    public static String getSqlFilePath(String sqlFileName) {
        try {
            ClassPathResource file = new ClassPathResource("testdata/sqlfiles/" + sqlFileName);
            File tempDirectory = FileUtils.getTempDirectory();
            File tempJarDirectory = new File(tempDirectory, UUID.randomUUID().toString());
            tempJarDirectory.mkdirs();
            File tempFile = new File(tempJarDirectory, sqlFileName);
            FileUtils.copyInputStreamToFile(file.getInputStream(), tempFile);
            return tempFile.getAbsolutePath();
        } catch (IOException e) {
            throw new RuntimeException("Failed to get file path for AppName = " + sqlFileName, e);
        }
    }

}
