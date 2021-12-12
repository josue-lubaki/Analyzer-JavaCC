package JUnit;

import org.junit.jupiter.api.Test;
import parser.JavaParser;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JavaParserTest {

    @Test
    void readFile() {
        // Vérifier si le fichier de la grammaire contient au moins une ligne
        List<String> lines = JavaParser.readFile("src/parser", "java.jj");
        assertTrue(lines.size() > 0);
    }
}