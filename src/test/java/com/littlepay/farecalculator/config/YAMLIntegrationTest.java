package com.littlepay.farecalculator.config;

import com.littlepay.farecalculator.FareCalculatorApplication;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = FareCalculatorApplication.class)
@TestPropertySource(properties = {"spring.profiles.active = test"})
class YAMLIntegrationTest {

    @Autowired
    private YAMLConfig config;
    
    @Test
    void whenProfileTest_thenNameTesting() {
        assertTrue("testing".equalsIgnoreCase(config.getEnvironment()));
        assertTrue("test-YAML".equalsIgnoreCase(config.getName()));
        assertFalse(config.isEnabled());
    }

    @Test
    void givenUserDefinedPOJO_whenBindingPropertiesFile_thenAllFieldsAreSet() {
        String expectedValue = "{{0, 3.25, 7.3}, {3.25, 0, 5.5},{7.3, 5.5, 0}}";
        assertEquals(expectedValue, config.getMatrix());
    }
    @Test
    void givenUserDefinedPOJO_whenBindingPropertiesFile_thenAllFieldsAreSet1() {
        Map<String, String> expectedResourcesPath = new HashMap<>();
        expectedResourcesPath.put("Stop1", "0");
        expectedResourcesPath.put("Stop2", "1");
        expectedResourcesPath.put("Stop3", "2");
        assertEquals(expectedResourcesPath, config.getStops());

    }
}
