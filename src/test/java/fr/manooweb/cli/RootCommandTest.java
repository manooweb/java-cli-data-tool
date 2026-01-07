package fr.manooweb.cli;

import org.junit.jupiter.api.Test;

import fr.manooweb.cli.testutil.CommandResult;
import fr.manooweb.cli.testutil.Helper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RootCommandTest {

    @Test
    void shouldReturnZeroWhenShowingUsage() {
        CommandResult cmdResult = Helper.execute();
        assertEquals(0, cmdResult.exitCode());
        String out = cmdResult.out().toString();
        assertTrue(out.contains("Usage: java-cli-data-tool"), () -> "stdout was:\n" + out);
        assertTrue(out.contains("--help"), () -> "stdout was:\n" + out);
        assertTrue(out.contains("--version"), () -> "stdout was:\n" + out);
        assertTrue(out.contains("Commands:"), () -> "stdout was:\n" + out);
        assertTrue(out.contains("transform"), () -> "stdout was:\n" + out);
    }
}
