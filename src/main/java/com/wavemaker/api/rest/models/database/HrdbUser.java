package com.wavemaker.api.rest.models.database;

/**
 * Created by tejaswim on 9/27/2016.
 */
public class HrdbUser {

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
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof HrdbUser)) return false;

        final HrdbUser hrdbUser = (HrdbUser) o;

        if (!getUserId().equals(hrdbUser.getUserId())) return false;
        if (getUsername() != null ? !getUsername().equals(hrdbUser.getUsername()) : hrdbUser.getUsername() != null) return false;
        if (getPassword() != null ? !getPassword().equals(hrdbUser.getPassword()) : hrdbUser.getPassword() != null) return false;
        if (getRole() != null ? !getRole().equals(hrdbUser.getRole()) : hrdbUser.getRole() != null) return false;
        return getTenantId() != null ? getTenantId().equals(hrdbUser.getTenantId()) : hrdbUser.getTenantId() == null;

    }

    @Override
    public int hashCode() {
        return getUserId().hashCode();
    }
}
