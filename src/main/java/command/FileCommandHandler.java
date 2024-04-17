package command;

import command.analyzer.CommandLineAnalyzer;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Spec;
import service.FileExecutor;
import service.JsonFileReader;
import service.XmlFileWriter;

@Command(name = "fileCommandHandler", mixinStandardHelpOptions = true)
public class FileCommandHandler implements Runnable {
    @Spec
    CommandSpec spec;
    private final CommandLineAnalyzer commandLineParser = new CommandLineAnalyzer();
    private final FileExecutor executor = new FileExecutor();
    private final XmlFileWriter writer = new XmlFileWriter();
    private String attribute;
    private String directoryPath;
    private Integer threadNumber;

    @Option(names = {"-a", "--attribute"}, required = true,
            description = "Specify the attribute to search for in the JSON files.")
    public void setAttribute(String attribute) {
        commandLineParser.setAttribute(attribute, spec);
        this.attribute = attribute;
    }

    @Option(names = {"-d", "--directory"}, required = true,
            description = "Specify the directory path to search for JSON files.")
    public void setDirectoryPath(String directoryPath) {
        commandLineParser.setDirectoryPath(directoryPath, spec);
        this.directoryPath = directoryPath;
    }

    @Option(names = {"-t", "--threads"},
            description = "Specify the number of threads.")
    public void setThreadNumber(Integer threadNumber) {
        commandLineParser.setThreadNumber(threadNumber, spec);
        this.threadNumber = threadNumber;
    }

    @Override
    public void run() {
        writer.write(executor.execute(attribute, JsonFileReader.getFiles(directoryPath), threadNumber), attribute);
    }
}
