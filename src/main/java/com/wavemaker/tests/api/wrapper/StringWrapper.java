package com.wavemaker.tests.api.wrapper;

public class StringWrapper {

    private String result;

    public StringWrapper() {
    }

    public StringWrapper(String result) {
        this.result = result;
    }


    public String getResult() {
        return result;
    }

    public void setResult(final String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "StringWrapper{" +
                "result='" + result + '\'' +
                '}';
    }
}
