package command.analyzer;

import constant.OrderAttributes;
import lombok.RequiredArgsConstructor;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.ParameterException;

import java.io.File;
import java.util.Arrays;

/**
 * The `CommandLineAnalyzer` is a utility class that provides methods to validate and process
 * the command-line arguments used in the JSON statistics parser application.
 */
@RequiredArgsConstructor
public class CommandLineAnalyzer {
    /**
     * Validates the given attribute against the valid `OrderAttributes` values.
     * If the attribute is not found in the `OrderAttributes` enum, a `ParameterException`
     * is thrown with an appropriate error message.
     *
     * @param attribute the attribute to validate
     * @param spec      the `CommandSpec` object for the command-line interface
     * @throws ParameterException if the attribute is invalid
     */
    public void setAttribute(String attribute, CommandSpec spec) {
        if (Arrays.stream(OrderAttributes.values())
                .noneMatch(attr -> attr.toString().equalsIgnoreCase(attribute))) {
            throw new ParameterException(spec.commandLine(), String.format("Error: invalid argument '%s' for " +
                    "'--attribute'. Valid arguments are %s", attribute, Arrays.toString(OrderAttributes.values())));
        }
    }

    /**
     * Validates the given directory path to ensure that it exists and is a valid directory.
     * If the directory path does not exist or is not a valid directory, a `ParameterException`
     * is thrown with an appropriate error message.
     *
     * @param directoryPath the directory path to validate
     * @param spec          the `CommandSpec` object for the command-line interface
     * @throws ParameterException if the directory path is invalid
     */
    public void setDirectoryPath(String directoryPath, CommandSpec spec) {
        File directory = new File(directoryPath);
        if (!directory.exists() || !directory.isDirectory()) {
            throw new ParameterException(spec.commandLine(),
                    String.format("Error: '%s' is not a valid directory path.", directoryPath));
        }
    }

    /**
     * Validates the given thread number to ensure that it is within the valid range of 1 to 32767.
     * If the thread number is not within the valid range, a `ParameterException` is thrown
     * with an appropriate error message.
     *
     * @param threadNumber the thread number to validate
     * @param spec         the `CommandSpec` object for the command-line interface
     * @throws ParameterException if the thread number is invalid
     */
    public void setThreadNumber(Integer threadNumber, CommandSpec spec) {
        if (!(threadNumber >= 1 && threadNumber <= 0x7fff)) {
            throw new ParameterException(spec.commandLine(),
                    String.format("Error: '%s' is not valid, the thread number must be 1 - 32767.", threadNumber));
        }
    }
}