/**
 * Copyright (c) 2013 - 2014 WaveMaker Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker Inc.
 */
package com.wavemaker.tests.api.rest.models.project;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Dilip Kumar.
 */
public enum ProjectMembershipStatus {
    INVITED(true),
    REQUESTED(true),
    REQUEST_REJECTED(false),
    INVITE_REJECTED(false),
    ACTIVE(true),
    LEFT(false),
    NOT_REGISTERED(false),
    REMOVED(false),
    TO_BE_REMOVED(false),
    APPROVED_BY_OWNER(true);

    private static List<ProjectMembershipStatus> activeStatuses;
    private static String activeSatusesString;

    private final boolean isActiveStatus;

    ProjectMembershipStatus(final boolean isActiveStatus) {
        this.isActiveStatus = isActiveStatus;
    }


    /**
     * Active status means a pending status or having successful membership status.
     *
     * @return true if it is active status.
     */
    public boolean isActiveStatus() {
        return this.isActiveStatus;
    }

    /**
     * Active status means a pending status or having successful membership status.
     *
     * @return List of active {@link ProjectMembershipStatus}es.
     */
    public static List<ProjectMembershipStatus> activeStatuses() {
        if (activeStatuses == null) {
            activeStatuses = new ArrayList<ProjectMembershipStatus>();
            for (ProjectMembershipStatus status : values()) {
                if (status.isActiveStatus) {
                    activeStatuses.add(status);
                }
            }
        }
        return activeStatuses;
    }

    /**
     * @return comma separated {@link #activeStatuses} String.
     */
    public static String activeStatusesSqlString() {
        if (StringUtils.isBlank(activeSatusesString)) {
            List<String> statuses = new ArrayList<String>(activeStatuses().size());
            for (ProjectMembershipStatus status : activeStatuses()) {
                statuses.add("'" + status.name() + "'");
            }
            activeSatusesString = StringUtils.join(statuses, ',');
        }
        return activeSatusesString;
    }


}
