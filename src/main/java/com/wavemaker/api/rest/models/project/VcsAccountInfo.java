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
public class VcsAccountInfo extends BaseEntity<String> {
    private String vcsAccountId;
    private String host;
    private int port;
    private VcsAccountType vcsAccountType;
    private String baseUrl;
    private boolean global;
    private String userName;
    private String password; //TODO should we hide the password?
    private String protocol;
    private boolean primary;

    public VcsAccountInfo() {
    }

    public boolean isPrimary() {
        return primary;
    }

    public void setPrimary(boolean primary) {
        this.primary = primary;
    }

    public String getVcsAccountId() {
        return vcsAccountId;
    }

    public void setVcsAccountId(String vcsAccountId) {
        this.vcsAccountId = vcsAccountId;
    }

    public VcsAccountType getVcsAccountType() {
        return vcsAccountType;
    }

    public void setVcsAccountType(VcsAccountType vcsAccountType) {
        this.vcsAccountType = vcsAccountType;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isGlobal() {
        return global;
    }

    public void setGlobal(boolean isGlobal) {
        this.global = isGlobal;
    }

    @Override
    public String getId() {
        return getVcsAccountId();
    }

    @Override
    public void setId(String id) {
        setVcsAccountId(id);
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    @Override
    public String toString() {
        return "VcsAccountInfo{" +
                "vcsAccountId='" + vcsAccountId + '\'' +
                ", host='" + host + '\'' +
                ", port=" + port +
                ", vcsAccountType=" + vcsAccountType +
                ", baseUrl='" + baseUrl + '\'' +
                ", global=" + global +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", protocol='" + protocol + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((baseUrl == null) ? 0 : baseUrl.hashCode());
        result = prime * result + ((userName == null) ? 0 : userName.hashCode());
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(obj == null)
            return false;
        if(getClass() != obj.getClass())
            return false;
        VcsAccountInfo other = (VcsAccountInfo) obj;
        if(baseUrl == null) {
            if(other.baseUrl != null)
                return false;
        } else if (!baseUrl.equals(other.baseUrl))
            return false;

        if(userName == null) {
            if(other.userName != null)
                return false;
        } else if (!userName.equals(other.userName))
            return false;

        if(password == null) {
            if(other.password != null)
                return false;
        } else if (!password.equals(other.password))
            return false;

        return true;
    }
}
