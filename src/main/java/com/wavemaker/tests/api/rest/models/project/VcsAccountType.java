/**
 * Copyright (c) 2013 - 2014 WaveMaker Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker Inc.
 */
package com.wavemaker.tests.api.rest.models.project;

/**
 * @author Dilip Kumar.
 */
public enum VcsAccountType {
    STASH("STASH", 1, true, true),
    SVN("SVN", 2, false, false),
    BIT_BUCKET("BIT_BUCKET", 3 , true, true),
    GIT_LAB("GIT_LAB", 4, true, true),
    SVN_HL("SVN_HL", 5, false, false);

    private String name;
    private int id;
    private boolean globalSupported;
    private boolean groupingSupported;

    VcsAccountType(String name, int id, boolean globalSupported, boolean groupingSupported) {
        this.id = id;
        this.name = name;
        this.globalSupported = globalSupported;
        this.groupingSupported = groupingSupported;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }


    public static VcsAccountType forName(String name) {
        for (VcsAccountType e : VcsAccountType.values()) {
            if (e.getName().equals(name)) {
                return e;
            }
        }
        return null;
    }

    public boolean isGlobalSupported() {
        return globalSupported;
    }

    public boolean isGroupingSupported() {
        return groupingSupported;
    }
}
