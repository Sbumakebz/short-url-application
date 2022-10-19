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
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
class ShortUrlApplicationTests {
    @Autowired
    private WebClient webClient;
    private String longURL;
    private String shortURL;
    
    @BeforeAll
    public void setLongURL() {
        longURL  = "http:~~google.com";//assume url
        webClient = WebClient.create("http://localhost:8083/short-url");
    }

    @Test
    @Order(1)
    void testEncodeURL() {
        String message = webClient.get().uri("/encode/{longURL}", longURL)
                .retrieve().bodyToMono(ResponseEntity<ShortUrl>.class).block();
        ObjectMapper objectMapper = new ObjectMapper();
        // Deserialization into the 'ShortUrl' class
        ShortUrl shortURLObject = objectMapper.readValue(message, ShortUrl.class);
        shortURL = shortURLObject.getShortURL();
        Assert.isTrue(shortURL.startsWith("short_url.com/"), "Passed URL encoding for: " + longURL);

        //test url format
        String invalidLongURL = "httpsgithub.com-Sbumakebzshort-url-application";
        Exception exception = assertThrows(MalformedURLException.class, () -> {
            webClient.get().uri("/encode/{longURL}", invalidLongURL)
                    .retrieve().bodyToMono(ResponseEntity<ShortUrl>.class).block();
        });

        String expectedMessage = "Invalid Url: " + invalidLongURL;
        String actualMessage = exception.getMessage();

        Assert.isTrue(actualMessage.equals(expectedMessage), "Passed Invalid URL test.");
    }

    @Test
    @Order(2)
    void testDecodeURL() {
        String message = webClient.get().uri("/decode/{shortURL}", shortURL)
                .retrieve().bodyToMono(ResponseEntity<LongUrl>.class).block();
        ObjectMapper objectMapper = new ObjectMapper();
        // Deserialization into the 'LongURL' class
        LongURL longURLObject = objectMapper.readValue(message, LongURL.class);
        Assert.isTrue(longURLObject.getLongURL().equals(longURL), "Passed URL decoding for: " + shortURL);

        String invalidShortURL = "httpsgithub.com:Sbumakebzshort-url-application";//non existant url on DB
        message = webClient.get().uri("/decode/{shortURL}", invalidShortURL)
                .retrieve().bodyToMono(ResponseEntity<LongUrl>.class).block();
        longURLObject = objectMapper.readValue(message, LongURL.class);
     
        String expectedMessage = "Short Url could not be decoded.";

        Assert.isTrue(longURLObject.getLongURL().equals(expectedMessage), "Passed Invalid short URL test.");
    }
}
