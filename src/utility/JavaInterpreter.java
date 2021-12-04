package utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import pkgAttribute.JavaAttribute;
import pkgClass.JavaClass;

/**
 * C'est la classe qui permet d'interpreter le resultat obtenue par l'analyse
 * 
 */
public class JavaInterpreter {
    private JavaObjetManager objectManager;

    // constructor
    public JavaInterpreter(JavaObjetManager objectManager) {
        this.objectManager = objectManager;
    }

    public void showStatisticToConsole() {
        StringBuilder sbModifier = new StringBuilder();
        StringBuilder sbVisibility = new StringBuilder();
        StringBuilder sbCoupling = new StringBuilder();
        StringBuilder sbCall = new StringBuilder();
        StringBuilder sbInheritance = new StringBuilder();
        JavaModifierSet modifiersHelpers = new JavaModifierSet();

        int fileCount = objectManager.size();

        objectManager.stream().forEach(obj -> {
            JavaFile file = (JavaFile) obj;

            file.getListClasses().stream().forEach(currentClass -> {
                // Pour chaque classe, faire statistique
                int nbrePublicAttribute = 0, nbrePrivateAttribute = 0, nbreProtectedAttribute = 0,
                        nbrePrimitiveAttribute = 0, nbreReferenceAttribute = 0;

                // Affichage des Titres
                Map<String, Integer> coupling = new HashMap<>();
                sbModifier.append("Q1/ Voici les statistiques de l'Analyse de la portee des attributs pour la classe "
                        + currentClass.getcName() + "\n");
                sbVisibility.append("Q2/ Voici les statistiques de l'Analyse de la visibilite de la classe "
                        + currentClass.getcName() + "\n");
                sbCoupling.append("Q4/ Voici les statistiques de l'aanlyse sur le couplage pour la classe "
                        + currentClass.getcName() + "\n");
                sbCall.append(
                        "Q5/ Voici les statistiques de l'analyse de graphe d'appel direct entre methodes pour la classe "
                                + currentClass.getcName());
                sbInheritance.append("Q3/ Voici les statistiques de l'analyse portant sur l'heritage:\n");
                sbInheritance.append(String.format("La classe %s est herite de %s", currentClass.getcName(),
                        currentClass.hasSuperClass() ? currentClass.getSuperClass() : "None") + "\n");

                List<String> listAssociations = new ArrayList<>();
                List<String> listVisibility = new ArrayList<>();

                for (JavaAttribute attr : currentClass.getListAttributes()) {
                    if (modifiersHelpers.isPublic(attr.getaScope()))
                        nbrePublicAttribute++;
                    else if (modifiersHelpers.isPrivate(attr.getaScope()))
                        nbrePrivateAttribute++;
                    else if (modifiersHelpers.isProtected(attr.getaScope()))
                        nbreProtectedAttribute++;

                    if (attr.isPrimitive())
                        nbrePrimitiveAttribute++;
                    else {
                        nbreReferenceAttribute++;

                        // Nous ne voulons pas d'auto-references ni dans les schemas ni dans la liste de
                        // visibilite
                        if (!listVisibility.contains(attr.getaType())
                                && !currentClass.getcName().equalsIgnoreCase(attr.getaType())) {
                            listVisibility.add(attr.getaType());
                            sbVisibility.append(String.format("\t%s\n", attr.getaType()));
                        }

                        if (!listAssociations.contains(attr.getaType())
                                && !currentClass.getcName().equalsIgnoreCase(attr.getaType())) {
                            listAssociations.add(attr.getaType());
                            sbInheritance.append(String.format("\t\t%s\n", attr.getaType()));
                        }
                    }
                } // end for

                double total = nbrePublicAttribute + nbrePrivateAttribute + nbreProtectedAttribute;
                if (total > 0) {
                    sbModifier.append(
                            String.format("\tPublic: %.2f%s\n", ((double) nbrePublicAttribute / total * 100), "%"));
                    sbModifier.append(String.format("\tProtected: %.2f%s\n",
                            ((double) nbreProtectedAttribute / total * 100), "%"));
                    sbModifier.append(
                            String.format("\tPrivate: %.2f%s\n", ((double) nbrePrivateAttribute / total * 100), "%"));

                    total = nbrePrimitiveAttribute + nbreReferenceAttribute;

                    sbModifier.append(String.format("\tSimple type: %.2f%s\n",
                            ((double) nbrePrimitiveAttribute / total * 100), "%"));
                    sbModifier.append(String.format("\tReference type: %.2f%s\n",
                            ((double) nbreReferenceAttribute / total * 100), "%"));
                } else
                    sbModifier.append("\tPas de Variables\n");

                // TODO Method Analysis

                // Coupling
                if (!coupling.entrySet().isEmpty()) {
                    for (Entry<String, Integer> entry : coupling.entrySet()) {
                        sbCoupling.append(String.format("\t%s: %d\n", entry.getKey(), entry.getValue()));
                    }
                } else {
                    sbCoupling.append("\tPas de reference direct.\n");
                }
            });
        });
        
    	System.out.println("==========================================");
    	System.out.println("=============== Modifiers ================");
    	System.out.println("==========================================");
    	System.out.println(sbModifier.toString());
    	System.out.println("==========================================");
    	System.out.println("=============== Visibility ===============");
    	System.out.println("==========================================");
    	System.out.println(sbVisibility.toString());
    	System.out.println("==========================================");
    	System.out.println("=============== Class Schemes ============");
    	System.out.println("==========================================");
    	System.out.println(sbInheritance);
    	System.out.println("==========================================");
    	System.out.println("=============== Coupling =================");
    	System.out.println("==========================================");
    	System.out.println(sbCoupling.toString());
    	System.out.println("==========================================");
    	System.out.println("=============== Call Hierarchy ===========");
    	System.out.println("==========================================");
    	System.out.println(sbCall.toString());
    }

}
