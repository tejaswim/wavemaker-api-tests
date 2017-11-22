/**
 * Copyright (c) 2013 - 2014 WaveMaker Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker Inc.
 */
package com.wavemaker.api.rest.models.project;

/**
 * @author Dilip Kumar.
 */
public enum Role {
    OWNER {
        @Override
        public boolean canInvite() {
            return true;
        }

        @Override
        public boolean canTakeActionOnInvite() {
            return false;
        }

        @Override
        public boolean canRequest() {
            return false;
        }

        @Override
        public boolean canTakeActionOnRequest() {
            return true;
        }

        @Override
        public boolean canManageDomains() {
            return true;
        }
    },
    CONTRIBUTOR {
        @Override
        public boolean canInvite() {
            return false;
        }

        @Override
        public boolean canTakeActionOnInvite() {
            return false;
        }

        @Override
        public boolean canRequest() {
            return false;
        }

        @Override
        public boolean canTakeActionOnRequest() {
            return false;
        }

        @Override
        public boolean canManageDomains() {
            return false;
        }
    },
    GUEST {
        @Override
        public boolean canInvite() {
            return false;
        }

        @Override
        public boolean canTakeActionOnInvite() {
            return true;
        }

        @Override
        public boolean canRequest() {
            return false;
        }

        @Override
        public boolean canTakeActionOnRequest() {
            return false;
        }

        @Override
        public boolean canManageDomains() {
            return false;
        }
    },
    DEVELOPER {
        @Override
        public boolean canInvite() {
            return false;
        }

        @Override
        public boolean canTakeActionOnInvite() {
            return true;
        }

        @Override
        public boolean canRequest() {
            return true;
        }

        @Override
        public boolean canTakeActionOnRequest() {
            return false;
        }

        @Override
        public boolean canManageDomains() {
            return false;
        }
    };

    public abstract boolean canInvite();

    public abstract boolean canTakeActionOnInvite();

    public abstract boolean canRequest();

    public abstract boolean canTakeActionOnRequest();

    public abstract boolean canManageDomains();

}
