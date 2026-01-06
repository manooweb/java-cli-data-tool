package fr.manooweb.cli;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.nio.file.Files;
import java.nio.file.Path;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.manooweb.core.JsonFlattener;
import fr.manooweb.io.JsonIO;

@Command(name = "transform", mixinStandardHelpOptions = true, description = "Transform a JSON input file and write the result to a file or stdout.")
public class TransformCommand implements Runnable {

    @Option(names = "--in", required = true, description = "Input file path.")
    private Path input;

    @Option(names = "--out", description = "Output file path. If omitted, output is written to stdout.")
    private Path output;

    @Option(names = "--op", required = true, converter = OperationNameConverter.class, description = "Operation to apply. Valid values: ${COMPLETION-CANDIDATES}.")
    private OperationName operation;

    @Override
    public void run() {
        if (!Files.exists(input)) {
            System.err.println("Input file not found: " + input);
            throw new IllegalArgumentException("Input file not found");
        }

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode inputJson = mapper.readTree(input.toFile());

            JsonNode outputJson;
            switch (operation) {
                case FLATTEN -> outputJson = JsonFlattener.flatten(inputJson);
                case PICK -> throw new UnsupportedOperationException("pick is not implemented yet");
                default -> throw new IllegalStateException("Unexpected operation: " + operation);
            }

            if (output == null) {
                JsonIO.writePretty(outputJson, System.out);
                System.out.println();
            } else {
                JsonIO.writePretty(outputJson, output);
            }
        } catch (JsonProcessingException e) {
            System.err.println("Invalid JSON: " + e.getOriginalMessage());
            throw new IllegalArgumentException("Invalid JSON", e);
        } catch (java.io.IOException e) {
            System.err.println("Failed to read input file: " + e.getMessage());
            throw new IllegalArgumentException("I/O error", e);
        }
    }
}
