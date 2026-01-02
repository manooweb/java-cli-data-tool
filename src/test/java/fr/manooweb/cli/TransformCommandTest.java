package fr.manooweb.cli;

import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransformCommandTest {

    @Test
    void transformHelpShouldReturnZero() {
        int exitCode = new CommandLine(new RootCommand()).execute("transform", "--help");
        assertEquals(0, exitCode);
    }

    @Test
    void transformMissingRequiredArgsShouldReturnUsageError() {
        int exitCode = new CommandLine(new RootCommand()).execute("transform");
        assertEquals(CommandLine.ExitCode.USAGE, exitCode);
    }
}
