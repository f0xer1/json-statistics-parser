package command.analyzer;

import constant.OrderAttributes;
import lombok.RequiredArgsConstructor;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.ParameterException;

import java.io.File;
import java.util.Arrays;

@RequiredArgsConstructor
public class CommandLineAnalyzer {
    public void setAttribute(String attribute, CommandSpec spec) {
        if (Arrays.stream(OrderAttributes.values())
                .noneMatch(attr -> attr.toString().equalsIgnoreCase(attribute))) {
            throw new ParameterException(spec.commandLine(), String.format("Error: invalid argument '%s' for " +
                    "'--attribute'. Valid arguments are %s", attribute, Arrays.toString(OrderAttributes.values())));
        }
    }

    public void setDirectoryPath(String directoryPath, CommandSpec spec) {
        File directory = new File(directoryPath);
        if (!directory.exists() || !directory.isDirectory()) {
            throw new ParameterException(spec.commandLine(),
                    String.format("Error: '%s' is not a valid directory path.", directoryPath));
        }
    }

    public void setThreadNumber(Integer threadNumber, CommandSpec spec) {
        if (!(threadNumber >= 1 && threadNumber <= 0x7fff)) {
            throw new ParameterException(spec.commandLine(),
                    String.format("Error: '%s' is not valid, the thread number must be 1 - 32767.", threadNumber));
        }
    }
}