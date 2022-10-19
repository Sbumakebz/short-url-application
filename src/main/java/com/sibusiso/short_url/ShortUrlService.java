package com.sibusiso.short_url;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.server.ResponseStatusException;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Optional;

@Service
@EnableAutoConfiguration
@ComponentScan(basePackages={"com.sibusiso.short_url"})
@EnableTransactionManagement
@EntityScan(basePackages="com.sibusiso.short_url")
//@Transactional
public class ShortUrlService {
    Logger logger = LoggerFactory.getLogger(ShortUrlService.class);

    private final String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    @Autowired
    ShortUrlRepository repository;

    //@Transactional
    public synchronized ShortUrl encode(String longURL) throws ResponseStatusException {
        logger.debug("To encode: " + longURL);

        try {
            longURL = longURL.replaceAll("~~", "//");
			new URL(longURL);
        } catch (MalformedURLException malformedURLException) {
            logger.error("Invalid Url: " + longURL);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Url: " + longURL);
        }

        StringBuilder builder = new StringBuilder();
        ShortUrlMapping url = null;
        List<ShortUrlMapping> shortUrlMappings = repository.getShortUrlMappingByLongUrl(longURL);
        if(shortUrlMappings == null || shortUrlMappings.isEmpty()) {
            //decode using base 62
            int base = characters.length();

            int baseId = 0;
            baseId = repository.getLatestId();
            baseId++;
            url = new ShortUrlMapping(baseId, longURL);
            repository.save(url);
            while(baseId > 0) {
                builder.append(characters.charAt((baseId % base)));
                baseId /= base;
            }
        } else {
            int base = characters.length();

            int baseId = 0;
            url = shortUrlMappings.get(0);
            baseId = url.getId();

            while(baseId > 0) {
                builder.append(characters.charAt((baseId % base)));
                baseId /= base;
            }
        }
        return new ShortUrl(url.getId(), "short_url.com/" + builder.reverse());
    }

    //@Transactional
    public synchronized LongUrl decode(String shortURL) throws ResponseStatusException {
        logger.debug("To decode: " + shortURL);

        shortURL = shortURL.substring(shortURL.lastIndexOf("~") + 1);//recall '/' is replaced by '~' on request
        int base = characters.length();
        int unknownBaseId = 0;
        for (int index = 0; index < shortURL.length(); index++)
        {
            if ('a' <= shortURL.charAt(index) && shortURL.charAt(index) <= 'z')
                unknownBaseId = unknownBaseId * base + shortURL.charAt(index) - 'a';
            if ('A' <= shortURL.charAt(index) && shortURL.charAt(index) <= 'Z')
                unknownBaseId = unknownBaseId * base + shortURL.charAt(index) - 'A' + 26;
            if ('0' <= shortURL.charAt(index) && shortURL.charAt(index) <= '9')
                unknownBaseId = unknownBaseId * base + shortURL.charAt(index) - '0' + 52;
        }

        logger.debug("unknownBaseId: " + unknownBaseId);
        Optional<ShortUrlMapping> url = repository.findById(unknownBaseId);
        return url.isPresent() ? new LongUrl(url.get().getId(), url.get().getLongUrl()) :
                new LongUrl(-1, "Short Url could not be decoded.");
    }
}

