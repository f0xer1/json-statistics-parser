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
        HashMap<String, Integer> map =  new HashMap<>();
        try (JsonParser parser = factory.createParser(file)) {
            if (parser.nextToken() != JsonToken.START_ARRAY) {
                throw new IOException("Expected an array as the root");
            }
            while (parser.nextToken() != JsonToken.END_ARRAY) {
                processingJsonObject(parser, attribute, map);
            }
        } catch (IOException e) {
            System.err.println("Error processing JSON array: " + e.getMessage());
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

