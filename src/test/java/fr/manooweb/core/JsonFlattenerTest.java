package fr.manooweb.core;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import fr.manooweb.cli.RootCommand;
import picocli.CommandLine;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;

class JsonFlattenerTest {

  private final ObjectMapper mapper = new ObjectMapper();

  @Test
  void shouldFlattenNestedObjects() throws Exception {
    String json = """
        {
          "user": {
            "name": "Manu",
            "address": {
              "city": "Nantes"
            }
          },
          "age": 38
        }
        """;

    JsonNode input = mapper.readTree(json);
    ObjectNode output = JsonFlattener.flatten(mapper, input);

    assertEquals("Manu", output.get("user.name").asText());
    assertEquals("Nantes", output.get("user.address.city").asText());
    assertEquals(38, output.get("age").asInt());
  }

  @Test
  void shouldKeepArraysUntouched() throws Exception {
    String json = """
        {
          "tags": ["a", "b"],
          "obj": {
            "x": 1
          }
        }
        """;

    JsonNode input = mapper.readTree(json);
    ObjectNode output = JsonFlattener.flatten(mapper, input);

    assertEquals(2, output.get("tags").size());
    assertEquals("a", output.get("tags").get(0).asText());
    assertEquals(1, output.get("obj.x").asInt());
  }

  @Test
  void shouldWrapNonObjectRootUnderValueKey() throws Exception {
    String json = """
        [1, 2, 3]
        """;

    JsonNode input = mapper.readTree(json);
    ObjectNode output = JsonFlattener.flatten(mapper, input);

    assertEquals(3, output.get("value").size());
    assertEquals(2, output.get("value").get(1).asInt());
  }

  @Test
  void shouldWriteFlattenedJsonToOutputFile() throws Exception {
    Path inputFile = Files.createTempFile("input-", ".json");
    Files.writeString(
        inputFile,
        """
            {
              "user": {
                "name": "Manu",
                "address": {
                  "city": "Nantes"
                }
              }
            }
            """);

    Path outputFile = Files.createTempFile("output-", ".json");

    int exitCode = new CommandLine(new RootCommand()).execute(
        "transform",
        "--in", inputFile.toString(),
        "--op", "flatten",
        "--out", outputFile.toString());

    assertEquals(0, exitCode);

    String outputJson = Files.readString(outputFile);

    assertTrue(outputJson.contains("\"user.name\""));
    assertTrue(outputJson.contains("\"Nantes\""));
  }
}
