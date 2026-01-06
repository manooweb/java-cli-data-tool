package fr.manooweb.error;

public final class ExitCodes {

    public static final int OK = 0;
    public static final int USAGE = 2;       // handled by Picocli
    public static final int INPUT = 3;
    public static final int PARSE = 4;
    public static final int PROCESSING = 5;

    private ExitCodes() {
        // constants only
    }
}
