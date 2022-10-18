package com.sibusiso.short_url;

import javax.persistence.Column;

public class ShortUrl {
    private Integer id;
    private String shortUrl;

    public ShortUrl() {
    }

    public ShortUrl(Integer id, String shortUrl) {
        this.id = id;
        this.shortUrl = shortUrl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }
}
