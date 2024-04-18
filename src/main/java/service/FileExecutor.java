package service;


import java.io.File;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;

import static java.util.stream.Collectors.groupingByConcurrent;
import static java.util.stream.Collectors.summingLong;

/**
 * The `FileExecutor` class is responsible for executing the processing of JSON files and
 * computing the statistics based on a specified attribute.
 * <p>
 * The `execute()` method takes the following parameters:
 * - `attribute`: the attribute to extract statistics for
 * - `files`: a list of JSON files to process
 * - `threadNumber`: the number of threads to use for parallel processing (or `null` to use the common ForkJoinPool)
 * <p>
 * The method uses a `ForkJoinPool` to process the JSON files in parallel, and updates a
 * `ConcurrentHashMap` with the statistics for each attribute found in the files. The
 * `processingJsonArray()` method from the `FileProcessor` class is used to process each
 * individual file.
 * <p>
 * Finally, the method returns a `Set<Map.Entry<String, Long>>` containing the statistics
 * for each attribute.
 */
public class FileExecutor {
    private final ConcurrentHashMap<String, Long> statisticMap = new ConcurrentHashMap<>();
    private final FileProcessor processor = new FileProcessor();

    /**
     * Executes the processing of the specified JSON files and computes the statistics
     * based on the given attribute.
     *
     * @param attribute    the attribute to extract statistics for
     * @param files        the list of JSON files to process
     * @param threadNumber the number of threads to use for parallel processing
     *                     (or `null` to use the common ForkJoinPool)
     * @return a set of key-value pairs representing the statistics for each attribute
     */
    public Set<Map.Entry<String, Long>> execute(String attribute, List<File> files, Integer threadNumber) {
        ForkJoinPool pool = Objects.isNull(threadNumber) ? ForkJoinPool.commonPool() : new ForkJoinPool(threadNumber);

        statisticMap.putAll(pool.submit(() -> files.parallelStream()
                .map(file -> processor.processingJsonArray(file, attribute))
                .flatMap(map -> map.entrySet().stream())
                .collect(groupingByConcurrent(Map.Entry::getKey, summingLong(Map.Entry::getValue)))
        ).join());
        return statisticMap.entrySet();
    }
}
