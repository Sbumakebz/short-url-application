package com.sibusiso.short_url;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/short-url")
public class ShortUrlController {
    @Autowired
    ShortUrlService service;

    @GetMapping(value = "/encode/{longUrl}")
    public String create(@PathVariable("longUrl") String longUrl)
            throws ResponseStatusException {
        return service.encode(longUrl);
    }

    @GetMapping(value = "/decode/{shortUrl}")
    public String deposit(@PathVariable("shortUrl") String shortUrl)
            throws ResponseStatusException {
        return service.decode(shortUrl);
    }
}
