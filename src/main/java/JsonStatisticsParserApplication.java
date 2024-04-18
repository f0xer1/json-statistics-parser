import command.FileCommandHandler;
import picocli.CommandLine;

/**
 * The `JsonStatisticsParserApplication` is the entry point of the application responsible for
 * parsing and analyzing JSON files.
 */
public class JsonStatisticsParserApplication {
    public static void main(String[] args) {
        int exitCode = new CommandLine(new FileCommandHandler()).execute(args);
        System.exit(exitCode);
    }
}
