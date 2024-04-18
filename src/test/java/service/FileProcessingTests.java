package service;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.io.IOException;
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
    @ValueSource(ints = {1, 2, 4, 8})
    void testExecutionTimeCheckForDifferentThreads(int numberOfThreads) {
        List<File> files = JsonFileReader.getFiles("src/test/resources/data");
        Instant start = Instant.now();
        fileExecutor.execute(ATTRIBUTE, files, numberOfThreads);
        Instant end = Instant.now();
        System.out.println(Duration.between(start, end) + " by " + numberOfThreads + " threads");
    }

    @Test
    void testProcessingIsValid() {
        List<File> files = JsonFileReader.getFiles("src/test/resources/data");
        Set<Map.Entry<String, Long>> result = fileExecutor.execute(ATTRIBUTE, files, null);
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void testProcessingByEmptyJson() {
        List<File> files = JsonFileReader.getFiles("src/test/resources/wrong/empty");
        Set<Map.Entry<String, Long>> result = fileExecutor.execute(ATTRIBUTE, files, null);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testProcessingIsInvalidJson() {
        List<File> files = JsonFileReader.getFiles("src/test/resources/wrong/invalid");
        Set<Map.Entry<String, Long>> result = fileExecutor.execute(ATTRIBUTE, files, null);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    @Test
    void testProcessingIsInvalidAttribute() {
        List<File> files = JsonFileReader.getFiles("src/test/resources/data");
        Set<Map.Entry<String, Long>> result = fileExecutor.execute("invalid", files, null);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
