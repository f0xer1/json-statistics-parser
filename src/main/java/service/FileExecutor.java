package service;


import java.io.File;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;

import static java.util.stream.Collectors.groupingByConcurrent;
import static java.util.stream.Collectors.summingLong;


public class FileExecutor {
    private final ConcurrentHashMap<String, Long> statisticMap = new ConcurrentHashMap<>();
    private final FileProcessor processor = new FileProcessor();


    public Set<Map.Entry<String, Long>> execute(String attribute, List<File> files, Integer treadNumber) {
        ForkJoinPool pool = Objects.isNull(treadNumber) ? ForkJoinPool.commonPool() : new ForkJoinPool(treadNumber);

        statisticMap.putAll(pool.submit(() -> files.parallelStream()
                .map(file -> processor.processingJsonArray(file, attribute))
                .flatMap(map -> map.entrySet().stream())
                .collect(groupingByConcurrent(Map.Entry::getKey, summingLong(Map.Entry::getValue)))
        ).join());
        return statisticMap.entrySet();
    }
}
