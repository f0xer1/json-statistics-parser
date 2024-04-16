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
                .noneMatch(attr -> attr.name().equals(attribute.toUpperCase()))) {
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

}