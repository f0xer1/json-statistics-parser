package service;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class FileProcessingTests {
    private final FileExecutor fileExecutor = new FileExecutor();
    private static final String ATTRIBUTE = "items";


    @ParameterizedTest
    @DisplayName("Test execution time with different number of threads")
    @ValueSource(ints = {1, 2, 4, 8})
    void testExecutionTimeCheckForDifferentThreads(int numberOfThreads) {
        List<File> files = JsonFileReader.getFiles("src/test/resources/data");
        Instant start = Instant.now();
        fileExecutor.execute(ATTRIBUTE, files, numberOfThreads);
        Instant end = Instant.now();
        System.out.println(Duration.between(start, end) + " by " + numberOfThreads + " threads");
    }

    @Test
    @DisplayName("Test processing of valid JSON files")
    void testProcessingIsValid() {
        List<File> files = JsonFileReader.getFiles("src/test/resources/data");
        Set<Map.Entry<String, Long>> result = fileExecutor.execute(ATTRIBUTE, files, null);
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    @DisplayName("Test processing of empty JSON files")
    void testProcessingByEmptyJson() {
        List<File> files = JsonFileReader.getFiles("src/test/resources/wrong/empty");
        Set<Map.Entry<String, Long>> result = fileExecutor.execute(ATTRIBUTE, files, null);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Test processing of invalid JSON files")
    void testProcessingIsInvalidJson() {
        List<File> files = JsonFileReader.getFiles("src/test/resources/wrong/invalid");
        Set<Map.Entry<String, Long>> result = fileExecutor.execute(ATTRIBUTE, files, null);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Test processing of invalid JSON files")
    void testProcessingIsInvalidAttribute() {
        List<File> files = JsonFileReader.getFiles("src/test/resources/data");
        Set<Map.Entry<String, Long>> result = fileExecutor.execute("invalid", files, null);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
