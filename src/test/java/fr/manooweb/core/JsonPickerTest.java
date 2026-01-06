package fr.manooweb.core;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class JsonPickerTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void shouldPickOnlyRequestedTopLevelFields() throws Exception {
        String json = """
            {
              "id": 1,
              "name": "Manu",
              "email": "x@y",
              "meta": { "age": 38 }
            }
            """;

        JsonNode input = mapper.readTree(json);
        Set<String> fields = JsonPicker.parseFieldsCsv("id,name");

        ObjectNode output = JsonPicker.pickTopLevelFields(input, fields);

        assertEquals(1, output.get("id").asInt());
        assertEquals("Manu", output.get("name").asText());
        assertNull(output.get("email"));
        assertNull(output.get("meta"));
    }

    @Test
    void shouldIgnoreMissingFields() throws Exception {
        String json = """
            { "id": 1 }
            """;

        JsonNode input = mapper.readTree(json);
        Set<String> fields = JsonPicker.parseFieldsCsv("id,doesNotExist");

        ObjectNode output = JsonPicker.pickTopLevelFields(input, fields);

        assertEquals(1, output.get("id").asInt());
        assertNull(output.get("doesNotExist"));
    }
}
