package service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

/**
 * The `JsonFileReader` class provides a utility method to retrieve a list of JSON files
 * from a specified directory.
 */
public class JsonFileReader {
    /**
     * Retrieves a list of all JSON files (files with the ".json" extension) from the
     * specified directory.
     *
     * @param directoryPath the path of the directory to search for JSON files
     * @return a list of File objects representing the JSON files found in the directory
     */
    public static List<File> getFiles(String directoryPath) {
        List<File> files = List.of();
        try (Stream<Path> pathStream = Files.walk(Paths.get(directoryPath))) {
            files = pathStream
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().toLowerCase().endsWith(".json"))
                    .map(Path::toFile).toList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return files;
    }
}
