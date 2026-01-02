package fr.manooweb.cli;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class MainTest {

    @Test
    void mainShouldRunWithoutException() {
        assertDoesNotThrow(() -> Main.main(new String[0]));
    }
}
