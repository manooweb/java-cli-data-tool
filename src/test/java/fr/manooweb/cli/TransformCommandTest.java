package fr.manooweb.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import picocli.CommandLine;

class TransformCommandTest {

    @Test
    void transformHelpShouldReturnZero() {
        int exitCode = execute("transform", "--help");
        assertEquals(0, exitCode);
    }

    @Test
    void transformMissingRequiredArgsShouldReturnUsageError() {
        int exitCode = execute("transform");
        assertEquals(CommandLine.ExitCode.USAGE, exitCode);
    }

    @Test
    void missingInputFileShouldReturn3() {
        int exitCode = execute(
                "transform",
                "--in", "this-file-should-not-exist-123456.json",
                "--op", "flatten");

        assertEquals(3, exitCode);
    }

    @Test
    void invalidJsonShouldReturn4() throws Exception {
        Path inputFile = Files.createTempFile("invalid-", ".json");
        Files.writeString(inputFile, """
                { "bad": }
                """);

        int exitCode = execute(
                "transform",
                "--in", inputFile.toString(),
                "--op", "flatten");

        assertEquals(4, exitCode);
    }

    @Test
    void pickNotImplementedShouldReturn5() throws Exception {
        Path inputFile = Files.createTempFile("valid-", ".json");
        Files.writeString(inputFile, """
                { "a": 1 }
                """);

        int exitCode = execute(
                "transform",
                "--in", inputFile.toString(),
                "--op", "pick");

        assertEquals(5, exitCode);
    }

    private int execute(String... args) {
        CommandLine cmd = new CommandLine(new RootCommand());
        cmd.setExecutionExceptionHandler(new ExecutionExceptionHandler());
        return cmd.execute(args);
    }
}
