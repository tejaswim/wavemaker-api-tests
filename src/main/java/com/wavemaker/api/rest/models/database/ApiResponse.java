package com.wavemaker.api.rest.models.database;


import java.util.List;

/**
 * Created by tejaswim on 8/12/2016.
 */
public class ApiResponse<T> {

    private int totalPages;
    private int totalElements;
    private boolean first;
    private int numberOfElements;
    private boolean last;
    private int size;
    private int number;
    private List<T> content;
    private List<Sort> sort;

    public List<Sort> getSort() {
        return sort;
    }

    public void setSort(List<Sort> sort) {
        this.sort = sort;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(final int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(final int totalElements) {
        this.totalElements = totalElements;
    }

    public boolean isFirst() {
        return first;
    }

    public void setFirst(final boolean first) {
        this.first = first;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(final int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(final boolean last) {
        this.last = last;
    }

    public int getSize() {
        return size;
    }

    public void setSize(final int size) {
        this.size = size;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(final int number) {
        this.number = number;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(final List<T> content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "totalPages=" + totalPages +
                ", totalElements=" + totalElements +
                ", first=" + first +
                ", numberOfElements=" + numberOfElements +
                ", last=" + last +
                ", size=" + size +
                ", number=" + number +
                ", content=" + content +
                '}';
    }
}
