package com.wavemaker.tests.api.client;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wavemaker.tests.api.manager.SecurityManager;
import com.wavemaker.tests.api.rest.IRestInput;
import com.wavemaker.tests.api.rest.RestInputImpl;
import com.wavemaker.tests.api.rest.models.project.ProjectDetails;
import com.wavemaker.tests.api.rest.models.project.ProjectModel;
import com.wavemaker.tests.api.utils.JSONUtils;
import com.wavemaker.tests.api.wrapper.BooleanWrapper;
import com.wavemaker.platform.util.LockUtils;
import com.wavemaker.studio.platform.project.model.BasicInfoWithID;

/**
 * Created by ArjunSahasranam on 5/2/16.
 */
public class ProjectControllerClient extends BaseClient {
    private static final Logger logger = LoggerFactory.getLogger(ProjectControllerClient.class);
    private final String allProjectsUrl = "/edn-services/rest/projects";
    private final String projectsListUrl = "/edn-services/rest/users/projects/list";
    private final String projectUrl = "/edn-services/rest/projects/:projectId";
    private final String studioProjectDetailsUrl = "/studio/services/projects/:studioProjectId";

    public ProjectDetails createProject(final ProjectModel projectModel) {
        return LockUtils.execute("createProject", SecurityManager.getAuthCookie(), "Create  Project", () -> {
            final String url = constructUrl(allProjectsUrl);
            IRestInput projectCreateInputs = new RestInputImpl() {
                @Override
                public Object getPayload() {
                    return JSONUtils.toJSON(projectModel);
                }
            };
            return post(url, projectCreateInputs, ProjectDetails.class);
        });
    }

    public List<BasicInfoWithID> getAllProjects() {
        final String url = constructUrl(projectsListUrl);
        final BasicInfoWithID[] basicInfoWithIDArray = get(url, BasicInfoWithID[].class);
        return Arrays.asList(basicInfoWithIDArray);
    }

    public boolean deleteProject(String projectId) {
        final String url = constructUrl(getBaseUrl() + projectUrl, new String[][]{{"projectId", projectId}});
        final BooleanWrapper deleteStatus = delete(url, BooleanWrapper.class);
        return deleteStatus.getResult();
    }

    public ProjectDetails getProject(String projectId) {
        final String url = constructUrl(projectUrl, new String[][]{{"projectId", projectId}});
        final ProjectDetails projectDetails = get(url, ProjectDetails.class);
        return projectDetails;
    }

    public void deleteProjects(List<BasicInfoWithID> allProjects) {
        if (allProjects != null && allProjects.size() > 0) {
            for (BasicInfoWithID basicInfoWithID : allProjects) {
                deleteProject(basicInfoWithID.getId());
            }
        }
    }

    public void deleteAllProjects() {
        final List<BasicInfoWithID> allProjects = getAllProjects();
        if (allProjects != null && allProjects.size() > 0) {
            logger.info("No of Projects {} exists for user cookie {}", allProjects.size(), SecurityManager.getAuthCookie());
            for (BasicInfoWithID basicInfoWithID : allProjects) {
                String prjName = basicInfoWithID.getName();
                try {
                    boolean deleteProject = deleteProject(basicInfoWithID.getId());
                    if (deleteProject) {
                        logger.info("Project {} deletion is successful", prjName);
                    }
                } catch (Exception e) {
                    logger.error("Unable to delete designtime with name {}", prjName, e.getMessage());
                }
            }
        }
    }

    public com.wavemaker.studio.platform.project.model.ProjectDetails getStudioProjectDetails(String studioProjectId) {
        final String url = constructUrl(studioProjectDetailsUrl, new String[][]{{"studioProjectId", studioProjectId}});
        return get(url, com.wavemaker.studio.platform.project.model.ProjectDetails.class);
    }
}