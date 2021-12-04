package utility;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class JavaFileExplorer {
    public static List<File> getAllJavaFiles(File directory, int depth) {
        try {
            List<File> javaFiles = new ArrayList<>();
            // verifier si c'est un dossier
            if (directory.isDirectory()) {
                // Recuperer les contenus du dossier
                File[] files = directory.listFiles();
                for (File file : files) {
                    if (file.isDirectory()) {
                        if (depth > 0) {
                            javaFiles.addAll(getAllJavaFiles(file, depth - 1));
                        }
                    } else {
                        // Verifier si l'extension est .java
                        if (file.getName().endsWith(".java")) {
                            javaFiles.add(file);
                        }
                    }
                }
            }
            return javaFiles;
        } catch (Exception e) {
            return null;
        }
    }
}
