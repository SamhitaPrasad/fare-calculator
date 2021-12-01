package com.littlepay.farecalculator.controller;

import com.littlepay.farecalculator.FareCalculatorApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;
import org.springframework.util.LinkedMultiValueMap;

import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = FareCalculatorApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"spring.profiles.active = test"})
public class FareCalculatorControllerTest {

    private final TestRestTemplate template;

    @Autowired
    public FareCalculatorControllerTest(TestRestTemplate template) {
        this.template = template;
    }

    @Test
    public void uploadFileTest_success() throws URISyntaxException {
        //Given that we have a well-formed csv
        LinkedMultiValueMap<String, Object> multipart = new LinkedMultiValueMap<>();
        multipart.add("file", file());
        //When the system receives the files it processes it and returns a json payload
        final ResponseEntity<Object[]> responseEntity = template.postForEntity("/api/csv/upload", new HttpEntity<>(multipart, headers()), Object[].class);
        Object[] csvOutputList = responseEntity.getBody();
        Map<String, String> csvOutput = (Map<String, String>) csvOutputList[0];
        //Then the first data in the payload is of status complete
        assertEquals("COMPLETE", csvOutput.get("status"));
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    private HttpHeaders headers() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        return headers;
    }

    private FileSystemResource file() throws URISyntaxException {
        return new FileSystemResource(Paths.get(ClassLoader.getSystemResource("taps.csv").toURI()));
    }
}
