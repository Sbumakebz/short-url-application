package com.sibusiso.short_url;

public class LongUrl {
    private Integer id;
    private String longURL;

    public LongUrl() {
    }

    public LongUrl(Integer id, String longURL) {
        this.id = id;
        this.longURL = longURL;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLongUrl() {
        return longURL;
    }

    public void setLongUrl(String longURL) {
        this.longURL = longURL;
    }
}
