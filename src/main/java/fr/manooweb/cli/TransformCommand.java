package fr.manooweb.cli;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.Callable;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import fr.manooweb.core.JsonFlattener;
import fr.manooweb.error.ExitCodes;
import fr.manooweb.error.InputException;
import fr.manooweb.error.ParseException;
import fr.manooweb.error.ProcessingException;
import fr.manooweb.io.JsonIO;

@Command(name = "transform", mixinStandardHelpOptions = true, description = "Transform a JSON input file and write the result to a file or stdout.")
public class TransformCommand implements Callable<Integer> {

    @Option(names = "--in", required = true, description = "Input file path.")
    private Path input;

    @Option(names = "--out", description = "Output file path. If omitted, output is written to stdout.")
    private Path output;

    @Option(names = "--op", required = true, converter = OperationNameConverter.class, description = "Operation to apply. Valid values: ${COMPLETION-CANDIDATES}.")
    private OperationName operation;

    @Override
    public Integer call() {
        if (!Files.exists(input)) {
            throw new InputException("Input file not found: " + input);
        }

        JsonNode inputJson;
        try {
            inputJson = fr.manooweb.io.JsonIO.read(input);
        } catch (JsonProcessingException e) {
            throw new ParseException("Invalid JSON: " + e.getOriginalMessage(), e);
        } catch (IOException e) {
            throw new InputException("I/O error while reading input: " + e.getMessage(), e);
        }

        JsonNode outputJson;
        try {
            switch (operation) {
                case FLATTEN -> outputJson = JsonFlattener.flatten(inputJson);
                case PICK -> throw new ProcessingException("pick is not implemented yet");
                default -> throw new ProcessingException("Unexpected operation: " + operation);
            }
        } catch (RuntimeException e) {
            // If it's already a domain exception, just rethrow.
            if (e instanceof InputException
                    || e instanceof ParseException
                    || e instanceof ProcessingException) {
                throw e;
            }
            throw new ProcessingException("Processing error: " + e.getMessage(), e);
        }

        try {
            if (output == null) {
                JsonIO.writePretty(outputJson, System.out);
                System.out.println();
            } else {
                JsonIO.writePretty(outputJson, output);
            }
        } catch (IOException e) {
            throw new InputException("I/O error while writing output: " + e.getMessage(), e);
        }

        return ExitCodes.OK;
    }
}
