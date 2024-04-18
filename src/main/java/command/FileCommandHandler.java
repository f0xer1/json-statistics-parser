package command;

import command.analyzer.CommandLineAnalyzer;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Spec;
import service.FileExecutor;
import service.JsonFileReader;
import service.XmlFileWriter;

/**
 * The `FileCommandHandler` is responsible for handling the command-line options and
 * executing the main logic of the JSON statistics parser application.
 * <p>
 * This class uses the `picocli` library to define the command-line options and
 * their corresponding methods. The available options are:
 * <p>
 * - `-a, --attribute`: Specifies the attribute to search for in the JSON files.
 * - `-d, --directory`: Specifies the directory path to search for JSON files.
 * - `-t, --threads`: Specifies the number of threads to use for the processing.
 * <p>
 * When the `run()` method is called, the `FileCommandHandler` retrieves the list of
 * JSON files from the specified directory using the `JsonFileReader` class, and then
 * executes the main logic of the application using the `FileExecutor` and `XmlFileWriter`
 * classes. The result of the processing is then printed to the console.
 */
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

    /**
     * Sets the attribute to search for in the JSON files.
     *
     * @param attribute the attribute to search for
     */
    @Option(names = {"-a", "--attribute"}, required = true,
            description = "Specify the attribute to search for in the JSON files.")
    public void setAttribute(String attribute) {
        commandLineParser.setAttribute(attribute, spec);
        this.attribute = attribute;
    }

    /**
     * Sets the directory path to search for JSON files.
     *
     * @param directoryPath the directory path to search for JSON files
     */
    @Option(names = {"-d", "--directory"}, required = true,
            description = "Specify the directory path to search for JSON files.")
    public void setDirectoryPath(String directoryPath) {
        commandLineParser.setDirectoryPath(directoryPath, spec);
        this.directoryPath = directoryPath;
    }

    /**
     * Sets the number of threads to use for the processing.
     * It is not mandatory.
     *
     * @param threadNumber the number of threads to use
     */
    @Option(names = {"-t", "--threads"},
            description = "Specify the number of threads.")
    public void setThreadNumber(Integer threadNumber) {
        commandLineParser.setThreadNumber(threadNumber, spec);
        this.threadNumber = threadNumber;
    }

    @Override
    public void run() {
        System.out.println(writer.write(executor.execute(attribute, JsonFileReader.getFiles(directoryPath), threadNumber),
                attribute, directoryPath));
    }
}
