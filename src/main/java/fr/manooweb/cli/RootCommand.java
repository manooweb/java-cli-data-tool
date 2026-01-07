package fr.manooweb.cli;

import picocli.CommandLine.Command;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Spec;

@Command(name = "java-cli-data-tool", mixinStandardHelpOptions = true, // --help, --version
        version = "0.6.2", description = "A small CLI tool to transform data files.", subcommands = {
                TransformCommand.class })
public class RootCommand implements Runnable {
    @Spec CommandSpec commandSpec;

    @Override
    public void run() {
        // If no subcommand is provided, show usage help.
        commandSpec.commandLine().usage(commandSpec.commandLine().getOut());
    }
}
