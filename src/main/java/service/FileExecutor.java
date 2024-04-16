package service;

import com.fasterxml.jackson.core.JsonFactory;

import java.io.File;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FileExecutor {
    private final int threadNumber = Runtime.getRuntime().availableProcessors() - 1;
    private final ConcurrentHashMap<String, Integer> statisticMap = new ConcurrentHashMap<>();
    private final JsonFactory factory = new JsonFactory();

    public void execute(String attribute, String directoryPath) {
        List<File> files = getFiles(directoryPath);
        ExecutorService service = Executors.newFixedThreadPool(threadNumber);
    }

    private List<File> getFiles(String directoryPath) {
        return FileReader.getFiles(directoryPath);
    }
    private void processingAllFiles(ConcurrentHashMap<String, Integer> statisticMap,
                                    String attribute, List<File> files){

    }

}
