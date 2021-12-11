package utility;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class JavaFileExplorer {
    /**
     * methode qui permet de collecter tous les fichers se trouvant dans des dossiers,
     * allant jusqu'à une profondeur de @code{depth} dans les dossiers
     * @param directory le dossier ou fichier servant de point d'entrée pour la collection des fichiers
     * @param depth la profondeur jusqu'au auquel fouiller les fichiers
     * @param ext l'extension de fichiers à rechercher
     * @return List<File> sinon null
     * */
    public static List<File> getAllJavaFiles(File directory, int depth, String ext) {
        try {
            List<File> javaFiles = new ArrayList<>();
            // verifier si c'est un dossier
            if (directory.isDirectory()) {
                // Recuperer les contenus du dossier
                File[] files = directory.listFiles();
                assert files != null;
                for (File file : files) {
                    if (file.isDirectory()) {
                        if (depth > 0) {
                            javaFiles.addAll(Objects.requireNonNull(getAllJavaFiles(file, depth - 1, ext)));
                        }
                    } else {
                        // Verifier si l'extension est .java
                        if (file.getName().endsWith(ext)) {
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
