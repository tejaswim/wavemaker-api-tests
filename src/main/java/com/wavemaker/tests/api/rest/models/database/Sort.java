package com.wavemaker.tests.api.rest.models.database;

/*
 * Created by venkateswarluk on 26/5/17.
 */

public class Sort {

    private String direction;
    private String property;
    private boolean ignoreCase;
    private String nullHandling;
    private boolean ascending;

    @Override
    public String toString() {
        return "Sort{" +
                "direction='" + direction + '\'' +
                ", property='" + property + '\'' +
                ", ignoreCase=" + ignoreCase +
                ", nullHandling='" + nullHandling + '\'' +
                ", ascending=" + ascending +
                '}';
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public boolean isIgnoreCase() {
        return ignoreCase;
    }

    public void setIgnoreCase(boolean ignoreCase) {
        this.ignoreCase = ignoreCase;
    }

    public String getNullHandling() {
        return nullHandling;
    }

    public void setNullHandling(String nullHandling) {
        this.nullHandling = nullHandling;
    }

    public boolean isAscending() {
        return ascending;
    }

    public void setAscending(boolean ascending) {
        this.ascending = ascending;
    }

}
