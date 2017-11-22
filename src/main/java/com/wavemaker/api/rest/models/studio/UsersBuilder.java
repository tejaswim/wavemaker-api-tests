package com.wavemaker.api.rest.models.studio;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wavemaker.api.config.StudioTestConfig;
import com.wavemaker.api.utils.RuntimeUtil;

/**
 * @author <a href="mailto:sunil.pulugula@wavemaker.com">Sunil Kumar</a>
 * @since 3/11/16
 */
public class UsersBuilder {
    private static final StudioTestConfig configProps = StudioTestConfig.getInstance();

    private static final Logger logger = LoggerFactory.getLogger(UsersBuilder.class);

    private final int noOfUsers;
    private final String userCSVFilePath;
    private List<User> activeUsers = new ArrayList<>();
    private List<User> cachedUsers = new ArrayList<>();

    public UsersBuilder(String userCSVFilePath, int noOfUsers) {
        this.userCSVFilePath = userCSVFilePath;
        this.noOfUsers = noOfUsers;
    }

    public List<User> getCachedUsers() {
        if (cachedUsers.isEmpty()) {
            final List<CSVRecord> userRecords = getUserRecords();
            for (final CSVRecord csvRecord : userRecords) {
                if (cachedUsers.size() < noOfUsers) {
                    cachedUsers.add(prepareUser(csvRecord));
                }
            }
        }
        return cachedUsers;
    }

    private User prepareUser(final CSVRecord csvRecord) {
        final String userName = csvRecord.get("USER_NAME");
        final String password = csvRecord.get("PASSWORD");
        return new User(userName, password);
    }

    private List<CSVRecord> getUserRecords() {
        final File runtimeResource = RuntimeUtil.getRuntimeResource(userCSVFilePath);
        return getUserRecords(runtimeResource);
    }

    private static List<CSVRecord> getUserRecords(final File sanityCSVFile) {
        try {
            CSVParser parser = CSVFormat.EXCEL.withHeader().parse(new FileReader(sanityCSVFile));
            return parser.getRecords();
        } catch (IOException e) {
            logger.error("Failed to read {}", sanityCSVFile, e);
            throw new RuntimeException("Failed to read " + sanityCSVFile, e);
        }
    }

    public List<User> getActiveUsers() {
        return activeUsers;
    }

    public void setActiveUsers(final List<User> activeUsers) {
        this.activeUsers = activeUsers;
    }
}
