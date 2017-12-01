package com.wavemaker.tests.api.rest.models.database.sampledb;

import java.util.Objects;

/**
 * Created by Tejaswi Maryala on 11/30/2017.
 */
public class User {

    private Integer userId;
    private String username;
    private String password;
    private String role;
    private Integer tenantId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(final Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(final String role) {
        this.role = role;
    }

    public Integer getTenantId() {
        return tenantId;
    }

    public void setTenantId(final Integer tenantId) {
        this.tenantId = tenantId;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", tenantId=" + tenantId +
                '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        final User user = (User) o;
        return Objects.equals(getUserId(), user.getUserId()) &&
                Objects.equals(getUsername(), user.getUsername()) &&
                Objects.equals(getPassword(), user.getPassword()) &&
                Objects.equals(getRole(), user.getRole()) &&
                Objects.equals(getTenantId(), user.getTenantId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getUsername(), getPassword(), getRole(), getTenantId());
    }
}
