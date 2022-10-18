package com.sibusiso.short_url;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="short_url_mapping")
@Component
public class ShortUrlMapping implements Serializable {
    @Id
    @Column(name = "id")
    Integer id;
	
    @Column(name = "long_url")
    private String longUrl;

    public ShortUrlMapping() {
    }

    public ShortUrlMapping(Integer id, String longUrl) {
        this.id = id;
        this.longUrl = longUrl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    @Override
    public boolean equals(Object otherShortUrl) {
        if(this.getLongUrl().equals(((ShortUrlMapping) otherShortUrl).getLongUrl()))
            return true;
        return false;
    }
}
