package com.wavemaker.tests.api.manager;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wavemaker.tests.api.client.LogsClient;

/**
 * Created by Tejaswi Maryala on 11/16/2016.
 */
public class LoggerManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggerManager.class);
    private static final LogsClient logs = new LogsClient();

    public void saveLogFileInLogsFolder(String fileName) {
        try {
            String appsPathName = "logs" + File.separator + "apps" + File.separator + fileName + ".log";
            FileUtils.writeStringToFile(new File(appsPathName), getAppLogs(), true);
            String serverPathName = "logs" + File.separator + "server" + File.separator + fileName + ".log";
            FileUtils.writeStringToFile(new File(serverPathName), getServerLogs(), true);
            LOGGER.info("Server,Application Logs saved at {},{}",appsPathName,serverPathName);
        } catch (Exception e) {
            LOGGER.error("Failed to fetch screenshot" + e.getMessage());
        }
    }

    public String getAppLogs() {
        try {
            return logs.getApplicationLogs();
        } catch (Exception e) {
            LOGGER.error("App Logs :Failed to fetch app logs" + e.getMessage());
            return "Error occurred while fetching app logs";
        }
    }

    public String getServerLogs() {
        try {
            return logs.getServerLogs();
        } catch (Exception e) {
            LOGGER.error("Server Logs :Failed to fetch server logs" + e.getMessage());
            return "Error occurred while fetching server logs";
        }
    }
}
