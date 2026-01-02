package fr.manooweb.cli;

import picocli.CommandLine.Command;

@Command(
        name = "java-cli-data-tool",
        mixinStandardHelpOptions = true, // --help, --version
        version = "0.2.0",
        description = "A small CLI tool to transform data files.",
        subcommands = { TransformCommand.class })
public class RootCommand implements Runnable {

    @Override
    public void run() {
        // If no subcommand is provided, show usage help.
        picocli.CommandLine.usage(this, System.out);
    }
}
