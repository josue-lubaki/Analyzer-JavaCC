package JUnit;

import org.junit.jupiter.api.Test;
import utility.JavaInterpreter;

import static org.junit.jupiter.api.Assertions.*;

class JavaInterpreterTest {

    @Test
    void capitalizeString() {
        String str = "programme";
        assertEquals("Programme", JavaInterpreter.capitalizeString(str));
    }
}