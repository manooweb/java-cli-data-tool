package fr.manooweb.cli;

import picocli.CommandLine;

public final class Main {
    public static void main(String[] args) {
        CommandLine cmd = new CommandLine(new RootCommand());
        cmd.setExecutionExceptionHandler(new ExecutionExceptionHandler());
        int exitCode = cmd.execute(args);
        System.exit(exitCode);
    }
}
