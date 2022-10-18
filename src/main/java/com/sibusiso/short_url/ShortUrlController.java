package com.sibusiso.short_url;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/short-url")
public class ShortUrlController {
    @Autowired
    ShortUrlService service;

    @RequestMapping(method = RequestMethod.GET, value = "/encode/{longUrl}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ShortUrl> encode(@PathVariable("longUrl") String longUrl)
            throws ResponseStatusException {
        return ResponseEntity.ok(service.encode(longUrl));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/decode/{shortUrl}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LongUrl>  decode(@PathVariable("shortUrl") String shortUrl)
            throws ResponseStatusException {
        return ResponseEntity.ok(service.decode(shortUrl));
    }
}
