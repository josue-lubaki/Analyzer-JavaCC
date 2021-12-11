package JUnit;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import utility.JavaFileExplorer;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JavaFileExplorerTest {

    @ParameterizedTest
    @ValueSource(strings = {"src/pkgClass", "src/pkgAttribute", "src/pkgInterface", "src/pkgMethod", "src/pkgMethodCall"})
    void getAllJavaFiles(String path) {
        List<File> list =  JavaFileExplorer.getAllJavaFiles(new File(path), 0, ".java");
        assert list != null;
        assertEquals(2, list.size());
    }
}