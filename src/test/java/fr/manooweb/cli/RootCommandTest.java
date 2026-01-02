package fr.manooweb.cli;

import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RootCommandTest {

    @Test
    void shouldReturnZeroWhenShowingUsage() {
        int exitCode = new CommandLine(new RootCommand()).execute();
        assertEquals(0, exitCode);
    }
}
