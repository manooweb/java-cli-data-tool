# java-cli-data-tool

A small Java 21 CLI to transform JSON files.

Built as a compact “vitrine” project: executable fat jar (`java -jar`), real file I/O, clean structure, unit tests, and explicit exit codes.

## Requirements

- Java 21
- Maven 3.8+

## Build

```bash
mvn clean test
mvn clean package
```

The executable jar is created in the `target/` directory.

## Usage

Show help:

```bash
java -jar target/java-cli-data-tool-*.jar --help
java -jar target/java-cli-data-tool-*.jar transform --help
```

### Operation: flatten

Flatten nested objects using dot notation. Arrays are kept as-is.

```bash
java -jar target/java-cli-data-tool-*.jar transform \
  --in samples/input-nested.json \
  --op flatten
```

Write to a file:

```bash
java -jar target/java-cli-data-tool-*.jar transform \
  --in samples/input-nested.json \
  --op flatten \
  --out build/output.json
```

### Operation: pick

Keep only selected top-level fields (comma-separated list). Missing fields are ignored.

```bash
java -jar target/java-cli-data-tool-*.jar transform \
  --in samples/input-nested.json \
  --op pick \
  --fields user,age
```

## Exit codes

- 0 : OK
- 2 : Usage error (invalid or missing CLI arguments — handled by Picocli)
- 3 : Input error (missing/unreadable input file or output I/O error)
- 4 : Parse error (invalid JSON)
- 5 : Processing error (unsupported operation, missing --fields for pick, etc.)

## Project structure

- fr.manooweb.cli
  Entry point and Picocli commands

- fr.manooweb.core
  Transformation logic (flatten, pick)

- fr.manooweb.io
  JSON read/write helpers (Jackson)

- fr.manooweb.error
  Domain exceptions and exit code mapping

## Tech stack

- Java 21
- Maven
- Picocli
- Jackson (tree model)
- JUnit 5
