package fr.manooweb.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import fr.manooweb.cli.testutil.CommandResult;
import fr.manooweb.cli.testutil.Helper;
import picocli.CommandLine;

class TransformCommandTest {

    @Test
    void transformHelpShouldReturnZero() {
        CommandResult cmdResult = Helper.execute("transform", "--help");
        assertEquals(0, cmdResult.exitCode());
        String out = cmdResult.out().toString();
        assertTrue(out.contains("Usage: java-cli-data-tool transform"), () -> "stdout was:\n" + out);
    }

    @Test
    void transformMissingRequiredArgsShouldReturnUsageError() {
        CommandResult cmdResult = Helper.execute("transform");
        assertEquals(CommandLine.ExitCode.USAGE, cmdResult.exitCode());
        String err = cmdResult.err().toString();
        assertTrue(err.contains("Missing required options:"), () -> "stderr was:\n" + err);
    }

    @Test
    void missingInputFileShouldReturn3() {
        CommandResult cmdResult = Helper.execute(
                "transform",
                "--in", "this-file-should-not-exist-123456.json",
                "--op", "flatten");

        assertEquals(3, cmdResult.exitCode());
        String err = cmdResult.err().toString();
        assertTrue(err.contains("Input file not found:"), () -> "stderr was:\n" + err);
    }

    @Test
    void invalidJsonShouldReturn4() throws Exception {
        Path inputFile = Files.createTempFile("invalid-", ".json");
        Files.writeString(inputFile, """
                { "bad": }
                """);

        CommandResult cmdResult = Helper.execute(
                "transform",
                "--in", inputFile.toString(),
                "--op", "flatten");

        assertEquals(4, cmdResult.exitCode());
        String err = cmdResult.err().toString();
        assertTrue(err.contains("Invalid JSON:"), () -> "stderr was:\n" + err);
    }

    @Test
    void pickWithoutFieldsShouldReturn5() throws Exception {
        Path inputFile = Files.createTempFile("valid-", ".json");
        Files.writeString(inputFile, """
                { "a": 1 }
                """);

        CommandResult cmdResult = Helper.execute(
                "transform",
                "--in", inputFile.toString(),
                "--op", "pick");

        assertEquals(5, cmdResult.exitCode());
        String err = cmdResult.err().toString();
        assertTrue(err.contains("--fields is required when --op=pick"), () -> "stderr was:\n" + err);
    }
}
