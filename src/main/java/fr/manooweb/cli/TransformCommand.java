package fr.manooweb.cli;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.nio.file.Files;
import java.nio.file.Path;

@Command(
        name = "transform",
        mixinStandardHelpOptions = true,
        description = "Transform a JSON input file and write the result to a file or stdout."
)
public class TransformCommand implements Runnable {

    @Option(names = "--in", required = true, description = "Input file path.")
    private Path input;

    @Option(names = "--out", description = "Output file path. If omitted, output is written to stdout.")
    private Path output;

    @Option(
            names = "--op",
            required = true,
            converter = OperationNameConverter.class,
            description = "Operation to apply. Valid values: ${COMPLETION-CANDIDATES}."
    )
    private OperationName operation;

    @Override
    public void run() {
        if (!Files.exists(input)) {
            System.err.println("Input file not found: " + input);
            throw new IllegalArgumentException("Input file not found");
        }

        System.out.println("Not implemented yet: --op=" + operation + ", --in=" + input + ", --out=" + output);
    }
}
