package fr.manooweb.error;

public final class ExitCodeMapper {

    private ExitCodeMapper() {
        // utility
    }

    public static int from(Throwable t) {
        if (t instanceof InputException) {
            return ExitCodes.INPUT;
        }
        if (t instanceof ParseException) {
            return ExitCodes.PARSE;
        }
        if (t instanceof ProcessingException) {
            return ExitCodes.PROCESSING;
        }
        return ExitCodes.PROCESSING;
    }
}
