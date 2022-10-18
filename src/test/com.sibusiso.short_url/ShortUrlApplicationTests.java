package com.sibusiso.short_url;

import com.netflix.discovery.EurekaClient;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.Assert;
import org.springframework.web.reactive.function.client.WebClient;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = ShortUrlController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ShortUrlApplicationTests {
    private WebClient webClient;

    @Test
    @Order(1)
    void testEncodeURL() {
        String longURL = "https://github.com/Sbumakebz/short-url-application";
        String message = webClient.get().uri("/encode/{longURL}", longURL)
                .retrieve().bodyToMono(ResponseEntity<ShortUrl>.class).block();
        Assert.isTrue(message.startsWith("short_url.com/"), "Passed URL encoding for: " + longURL);

        //test url format
        String longURL = "httpsgithub.com:Sbumakebzshort-url-application";
        Exception exception = assertThrows(MalformedURLException.class, () -> {
            webClient.get().uri("/encode/{longURL}", longURL)
                    .retrieve().bodyToMono(ResponseEntity<ShortUrl>.class).block();
        });

        String expectedMessage = "Invalid Url: " + longURL;
        String actualMessage = exception.getMessage();

        Assert.isTrue(actualMessage.equals(expectedMessage), "Passed Invalid URL test.");
    }

    @Test
    @Order(2)
    void testDecodeURL() {
        String shortURL = "short_url.com/";//TODO get a short url example
        String message = webClient.get().uri("/decode/{shortURL}", shortURL)
                .retrieve().bodyToMono(ResponseEntity<LongUrl>.class).block();
        Assert.isTrue(message.startsWith("http://"), "Passed URL encoding for: " + shortURL);

        String shortURL = "httpsgithub.com:Sbumakebzshort-url-application";
        String message = webClient.get().uri("/decode/{shortURL}", shortURL)
                .retrieve().bodyToMono(ResponseEntity<LongUrl>.class).block();

        String expectedMessage = "Short Url could not be decoded.";

        Assert.isTrue(message.equals(expectedMessage), "Passed Invalid short URL test.");
    }
}
