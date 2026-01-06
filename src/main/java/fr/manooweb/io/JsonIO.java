package fr.manooweb.io;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public final class JsonIO {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private JsonIO() {
        // Utility class
    }

    public static ObjectMapper mapper() {
        return MAPPER;
    }

    public static JsonNode read(Path input) throws IOException, JsonProcessingException {
        return MAPPER.readTree(input.toFile());
    }

    public static void writePretty(JsonNode json, OutputStream out) throws IOException {
        MAPPER.writerWithDefaultPrettyPrinter().writeValue(out, json);
    }

    public static void writePretty(JsonNode json, Path output) throws IOException {
        Path parent = output.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
        MAPPER.writerWithDefaultPrettyPrinter().writeValue(output.toFile(), json);
    }
}
