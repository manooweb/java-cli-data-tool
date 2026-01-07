package fr.manooweb.cli.testutil;

import java.io.PrintWriter;
import java.io.StringWriter;

import fr.manooweb.cli.ExecutionExceptionHandler;
import fr.manooweb.cli.RootCommand;
import picocli.CommandLine;

public class Helper {
    public static CommandResult execute(String... args) {
        CommandLine cmd = new CommandLine(new RootCommand());
        cmd.setExecutionExceptionHandler(new ExecutionExceptionHandler());

        StringWriter out = new StringWriter();
        StringWriter err = new StringWriter();
        PrintWriter printout = new PrintWriter(out);
        PrintWriter printErr = new PrintWriter(err);
        cmd.setOut(printout);
        cmd.setErr(printErr);
        int exitCode = cmd.execute(args);
        printout.flush();
        printErr.flush();
        return new CommandResult(exitCode, out, err);
    }
}
