package fr.manooweb.cli.testutil;

import java.io.StringWriter;

public record CommandResult( int exitCode, StringWriter out, StringWriter err) {


}
