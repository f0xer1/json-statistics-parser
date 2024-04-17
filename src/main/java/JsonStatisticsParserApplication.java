import command.FileCommandHandler;
import picocli.CommandLine;

public class JsonStatisticsParserApplication {
    public static void main(String[] args) {
        int exitCode = new CommandLine(new FileCommandHandler()).execute(args);
        System.exit(exitCode);
    }
}
