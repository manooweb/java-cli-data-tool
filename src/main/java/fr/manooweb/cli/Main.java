package fr.manooweb.cli;

import picocli.CommandLine;

public final class Main {
    public static void main(String[] args) {
        int exitCode = new CommandLine(new RootCommand()).execute(args);
        System.exit(exitCode);
    }
}
