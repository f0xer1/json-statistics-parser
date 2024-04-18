package service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class JsonFileReader {
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
