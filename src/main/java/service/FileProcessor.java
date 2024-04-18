package service;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

@RequiredArgsConstructor
public class FileProcessor {
    private final JsonFactory factory = new JsonFactory();

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

    private void handleAttribute(String attribute, HashMap<String, Integer> map) {
        for (String part : attribute.split(", ")) {
            map.put(part, map.getOrDefault(part, 0) + 1);
        }
    }

}

