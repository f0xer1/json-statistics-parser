package service;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;


/**
 * The `FileProcessor` class is responsible for processing JSON files and extracting
 * statistics based on a specified attribute.
 */
@RequiredArgsConstructor
public class FileProcessor {
    private final JsonFactory factory = new JsonFactory();

    /**
     * Processes a JSON file and extracts statistics based on the specified attribute.
     *
     * @param file      the JSON file to process
     * @param attribute the attribute to extract statistics for
     * @return a `HashMap<String, Integer>` representing the statistics of the specified
     * attribute in the JSON file
     */
    public HashMap<String, Integer> processingJsonArray(File file, String attribute) {
        HashMap<String, Integer> map = new HashMap<>();
        try (JsonParser parser = factory.createParser(file)) {
            JsonToken rootToken = parser.nextToken();
            if (rootToken == JsonToken.START_OBJECT) {
                processingJsonObject(parser, attribute, map);
            } else if (rootToken == JsonToken.START_ARRAY) {
                while (parser.nextToken() != JsonToken.END_ARRAY) {
                    processingJsonObject(parser, attribute, map);
                }
            } else {
                throw new IOException("Expected an array or object as the root");
            }

        } catch (IOException e) {
            System.err.println("Error processing JSON array: " + e.getMessage());
            map.clear();
        }
        return map;
    }

    /**
     * Processes a JSON object and extracts the specified attribute.
     *
     * @param parser    the JSON parser
     * @param attribute the attribute to extract
     * @param map       the statistics map to update
     * @throws IOException if an error occurs during JSON parsing
     */
    private void processingJsonObject(JsonParser parser, String attribute, HashMap<String, Integer> map) throws IOException {
        if (parser.currentToken() == JsonToken.START_OBJECT) {
            while (parser.nextToken() != JsonToken.END_OBJECT) {
                String fieldName = parser.getValueAsString();
                parser.nextToken();
                if (attribute.equalsIgnoreCase(fieldName)) {
                    handleAttribute(parser.getText(), map);
                }
            }
        }
    }
    /**
     * Handles the specified attribute by splitting it on the ", " delimiter and updating
     * the statistics map accordingly.
     *
     * @param attribute the attribute to handle
     * @param map the statistics map to update
     */
    private void handleAttribute(String attribute, HashMap<String, Integer> map) {
        for (String part : attribute.split(", ")) {

            if (!part.equals("null" )){
                map.put(part, map.getOrDefault(part, 0) + 1);
            }
        }
    }

}

