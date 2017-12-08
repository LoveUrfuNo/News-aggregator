package com.tochka.newsParser.dto;

public class SearchParameters {

    private String searchLine;

    public String getSearchLine() {
        return searchLine;
    }

    public void setSearchLine(String searchLine) {
        this.searchLine = searchLine;
    }

    @Override
    public String toString() {
        return "SearchParameters{" +
                "searchLine='" + searchLine + '\'' +
                '}';
    }
}
