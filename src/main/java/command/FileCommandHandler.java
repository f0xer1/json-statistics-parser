package command;

import command.analyzer.CommandLineAnalyzer;
import lombok.RequiredArgsConstructor;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Spec;
import service.FileExecutor;

@Command(name = "fileCommandHandler", mixinStandardHelpOptions = true)
@RequiredArgsConstructor
public class FileCommandHandler implements Runnable {
    @Spec
    CommandSpec spec;
    private final CommandLineAnalyzer commandLineParser;
    private String attribute;
    private String directoryPath;

    public static void main(String[] args) {
        CommandLineAnalyzer commandLineAnalyzer = new CommandLineAnalyzer();
        int exitCode = new CommandLine(new FileCommandHandler(commandLineAnalyzer)).execute(args);
        System.exit(exitCode);
    }

    @Option(names = {"-a", "--attribute"}, required = true,
            description = "Specify the attribute to search for in the JSON files.")
    public void setAttribute(String attribute) {
        commandLineParser.setAttribute(attribute, spec);
        this.attribute = attribute.toUpperCase();
    }

    @Option(names = {"-d", "--directory"}, required = true,
            description = "Specify the directory path to search for JSON files.")
    public void setDirectoryPath(String directoryPath) {
        commandLineParser.setDirectoryPath(directoryPath, spec);
        this.directoryPath = directoryPath;
    }

    @Override
    public void run() {
        FileExecutor executor = new FileExecutor();
        executor.execute(attribute, directoryPath);
    }
}
