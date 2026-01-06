package fr.manooweb.core;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Iterator;
import java.util.Map;

public final class JsonFlattener {

    private JsonFlattener() {
        // Utility class
    }

    /**
     * Flattens nested JSON objects using dot notation.
     * Arrays are left untouched as values.
     *
     * Example:
     * {"a":{"b":1}} => {"a.b":1}
     */
    public static ObjectNode flatten(ObjectMapper mapper, JsonNode input) {
        ObjectNode output = mapper.createObjectNode();
        flattenInto(output, "", input);
        return output;
    }

    private static void flattenInto(ObjectNode output, String prefix, JsonNode node) {
        if (node == null || node.isNull()) {
            // Keep null as a value at the current prefix if applicable
            if (!prefix.isEmpty()) {
                output.putNull(prefix);
            }
            return;
        }

        if (node.isObject()) {
            Iterator<Map.Entry<String, JsonNode>> fields = node.fields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> entry = fields.next();
                String key = prefix.isEmpty() ? entry.getKey() : prefix + "." + entry.getKey();
                JsonNode value = entry.getValue();

                if (value != null && value.isObject()) {
                    flattenInto(output, key, value);
                } else {
                    // Arrays and primitives are stored as-is
                    output.set(key, value);
                }
            }
            return;
        }

        // If the root is not an object, keep it under a fixed key
        // (rare, but makes the behavior explicit and testable)
        output.set(prefix.isEmpty() ? "value" : prefix, node);
    }
}
