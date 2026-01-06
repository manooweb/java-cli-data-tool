package fr.manooweb.core;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.LinkedHashSet;
import java.util.Set;

public final class JsonPicker {

    private JsonPicker() {
        // Utility class
    }

    /**
     * Picks a subset of top-level fields from an input JSON object.
     * Missing fields are ignored.
     */
    public static ObjectNode pickTopLevelFields(JsonNode input, Set<String> fields) {
        ObjectNode output = JsonNodeFactory.instance.objectNode();

        if (input == null || input.isNull()) {
            return output;
        }

        if (!input.isObject()) {
            // Keep behavior explicit; pick is defined only for objects.
            // Returning empty output is acceptable for this MVP.
            return output;
        }

        for (String field : fields) {
            if (field == null || field.isBlank()) {
                continue;
            }
            JsonNode value = input.get(field);
            if (value != null) {
                output.set(field, value);
            }
        }

        return output;
    }

    public static Set<String> parseFieldsCsv(String csv) {
        Set<String> fields = new LinkedHashSet<>();
        if (csv == null || csv.isBlank()) {
            return fields;
        }

        for (String raw : csv.split(",")) {
            String trimmed = raw.trim();
            if (!trimmed.isEmpty()) {
                fields.add(trimmed);
            }
        }
        return fields;
    }
}
