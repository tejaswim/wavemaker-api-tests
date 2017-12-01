package com.wavemaker.tests.api.listeners;

import java.util.List;

import org.apache.commons.csv.CSVRecord;
import org.testng.IAlterSuiteListener;
import org.testng.xml.XmlSuite;

import com.wavemaker.tests.api.client.ProjectControllerClient;
import com.wavemaker.tests.api.config.StudioTestConfig;
import com.wavemaker.tests.api.factory.ProjectControllerClientFactory;
import com.wavemaker.tests.api.login.ApiAuthenticationManager;
import com.wavemaker.tests.api.manager.SecurityManager;
import com.wavemaker.tests.api.rest.models.studio.User;

/**
 * Created by Tejaswi Maryala on 11/29/2017.
 */
public class WMIAlterListener implements IAlterSuiteListener {
    private static final StudioTestConfig INSTANCE = StudioTestConfig.getInstance();
    private ApiAuthenticationManager authenticationManager = new ApiAuthenticationManager(
            INSTANCE.getUsersCSV(), INSTANCE.getNoOfUsers());
    private ProjectControllerClient projectControllerClient = ProjectControllerClientFactory.getInstance();

    @Override
    public void alter(final List<XmlSuite> suites) {
        if (INSTANCE.getCleanUpStatus()) {
            final List<CSVRecord> users = INSTANCE.getUserRecords();
            for (CSVRecord csvRecord : users) {
                User user = new User(csvRecord.get("USER_NAME"), csvRecord.get("PASSWORD"));
                authenticationManager.login(user);
                projectControllerClient.deleteAllProjects();
                SecurityManager.clear();
            }
        }
    }
}
