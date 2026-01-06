package fr.manooweb.cli;

import fr.manooweb.error.ExitCodeMapper;
import picocli.CommandLine;

public class ExecutionExceptionHandler implements CommandLine.IExecutionExceptionHandler {

    @Override
    public int handleExecutionException(
            Exception ex,
            CommandLine commandLine,
            CommandLine.ParseResult parseResult) {

        Throwable cause = findDomainCause(ex);

        commandLine.getErr().println(cause.getMessage());
        return ExitCodeMapper.from(cause);
    }

    private static Throwable findDomainCause(Throwable ex) {
        Throwable current = ex;
        while (current != null) {
            if (current instanceof fr.manooweb.error.InputException
                    || current instanceof fr.manooweb.error.ParseException
                    || current instanceof fr.manooweb.error.ProcessingException) {
                return current;
            }
            current = current.getCause();
        }
        return ex;
    }
}
