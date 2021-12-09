package utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import pkgAttribute.JavaAttribute;
import pkgClass.JavaClass;
import pkgMethod.JavaMethod;
import pkgMethodCall.JavaMethodCall;

/**
 * C'est la classe qui permet d'interpreter le resultat obtenue par l'analyse
 * 
 */
public class JavaInterpreter {
	public static final String RESET = "\033[0m";  // Text Reset
	public static final String GREEN_BOLD = "\033[1;32m";  // GREEN
	public static final String YELLOW_BOLD = "\033[1;33m"; // YELLOW
	public static final String YELLOW_UNDERLINED = "\033[4;33m"; // YELLOW
	public static final String YELLOW_BOLD_BRIGHT = "\033[1;93m";// YELLOW
	public static final String RED_BOLD = "\033[1;31m";    // RED
	public static final String RED = "\033[0;31m";     // RED
	public static final String GREEN_BOLD_BRIGHT = "\033[1;92m"; // GREEN
	public static final String WHITE_BOLD = "\033[1;37m";  // WHITE
	public static final String WHITE_BOLD_BRIGHT = "\033[1;97m"; // WHITE
	private final JavaObjetManager objectManager;

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

		// int fileCount = objectManager.size();

		objectManager.forEach(obj -> {
			JavaFile file = (JavaFile) obj;

			file.getListClasses().forEach(currentClass -> {
				// Pour chaque classe, faire statistique
				int nbrePublicAttribute = 0, nbrePrivateAttribute = 0, nbreProtectedAttribute = 0,
						nbrePrimitiveAttribute = 0, nbreReferenceAttribute = 0;

				// Affichage des Titres
				Map<String, Integer> coupling = new HashMap<>();
				sbModifier.append(YELLOW_BOLD + "Q1 |> Voici les statistiques de l'Analyse de la portee des attributs pour la classe " + YELLOW_BOLD_BRIGHT).append(currentClass.getcName()).append(RESET).append("\n");
				sbVisibility.append(YELLOW_BOLD + "Q2 |> Voici les statistiques de l'Analyse de la visibilite de la classe " + YELLOW_BOLD_BRIGHT).append(currentClass.getcName()).append(RESET).append("\n");
				sbCoupling.append(YELLOW_BOLD + "Q4 |> Voici les statistiques de l'Analyse sur le couplage pour la classe " + YELLOW_BOLD_BRIGHT).append(currentClass.getcName()).append(RESET).append("\n");
				sbCall.append(YELLOW_BOLD + "Q5/ Voici les statistiques de l'analyse de graphe d'appel direct entre methodes pour la classe " + YELLOW_BOLD_BRIGHT).append(currentClass.getcName()).append(RESET);
				sbInheritance.append(YELLOW_BOLD + "Q3 |> Voici les statistiques de l'analyse portant sur l'heritage de la classe " + YELLOW_BOLD_BRIGHT).append(currentClass.getcName()).append(RESET).append("\n");

				if (!currentClass.hasSuperClass())
					sbInheritance
							.append(String.format("La classe \"%s\" n'hérite d'aucune autre classe\n\tLes Associations :\n",
									currentClass.getcName()));
				else
					sbInheritance.append(String.format("La classe %s est herite de %s\n\tLes Associations : \n",
							currentClass.getcName(), currentClass.getSuperClass()));

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
						if (!listAssociations.contains(attr.getaType())
								&& !currentClass.getcName().equalsIgnoreCase(attr.getaType())) {
							listAssociations.add(attr.getaType());
							sbInheritance.append(String.format("\t\t- %s\n", attr.getaType()));
						}
					}
				} // end for

				double total = nbrePublicAttribute + nbrePrivateAttribute + nbreProtectedAttribute;
				if (total > 0) {
					double pPublic = (nbrePublicAttribute / total) * 100;
					double pProtected = (nbreProtectedAttribute / total) * 100;
					double pPrivate = (nbrePrivateAttribute / total) * 100;

					sbModifier.append("\tPourcentage des attributs Private: " + GREEN_BOLD).append(pPrivate).append("%").append(RESET).append("\n");
					sbModifier.append("\tPourcentage des attributs publics: " + GREEN_BOLD).append(pPublic).append("%").append(RESET).append("\n");
					sbModifier.append("\tPourcentage des attributs Protected: " + GREEN_BOLD).append(pProtected).append("%").append(RESET).append("\n");

					total = nbrePrimitiveAttribute + nbreReferenceAttribute;
					double pSimple = (nbrePrimitiveAttribute / total ) * 100;
					double pReference = (nbreReferenceAttribute / total) * 100;

					sbVisibility.append("\t% des attributs de type simple et % des attributs de référence (objet) :\n");
					sbVisibility.append("\t\tSimple Type: " + GREEN_BOLD).append(pSimple).append("%").append(RESET).append("\n");
					sbVisibility.append("\t\tRéférence Type: " + GREEN_BOLD).append(pReference).append("%").append(RESET).append("\n");

					sbVisibility.append("\tclasse à visibilité permanente : \n");
					for (JavaAttribute attr : currentClass.getListAttributes()) {
						if (!listVisibility.contains(attr.getaType())
								&& !currentClass.getcName().equalsIgnoreCase(attr.getaType())) {
							listVisibility.add(attr.getaType());
							sbVisibility.append(String.format("\t\t- %s\n", attr.getaType()));
						}
					}

				} else {
					sbModifier.append("\tPas d'Attribut\n");
					sbVisibility.append("\tPas de visibilité pour des Attributs\n");
				}

				for (JavaMethod method : currentClass.getListMethods()) {
					// recuperer le nom de la methode
					sbCall.append(String.format("\n\t%s: ", method.getmName()));
					boolean found = false;

					for (JavaMethodCall methodCall : method.getListMethodCalls()) {
						// Verifier si la method est appele par une variable d'instance ou sinon c'est
						// une method statique de la classe
						JavaAttribute attr = method.getListLocalVariables()
								.stream()
								.filter(m -> m.getaName().equals(methodCall.getObj()))
								.findAny()
								.orElseGet(() -> currentClass.getListAttributes().stream()
										.filter(a -> a.getaName().equals(methodCall.getObj())
										)
										.findAny()
										.orElse(null));

						// verification de l'heritage / recuperation de tous les parents
						JavaClass parent = currentClass.getcParent();
						while (attr == null && parent != null) {
							attr = parent.getListAttributes()
									.stream()
									.filter(a -> a.getaName().equalsIgnoreCase(methodCall.getObj()))
									.findAny()
									.orElse(null);
							parent = parent.getcParent();
						}

						JavaClass otherClass = getClassByName(attr != null ? attr.getaType() : methodCall.getObj());

						for (int pos = 1; pos < methodCall.getLength(); pos++) {
							if (otherClass == null)
								break;

							String call = methodCall.getCall(pos);
							if (call.contains("()")) {
								// si method static
								if(attr == null) {
									sbCall.append("\n\t\tappel " + GREEN_BOLD)
											.append(otherClass.getcName()).append(".")
											.append(WHITE_BOLD_BRIGHT).append(call)
											.append(RESET);
									sbCall.append(" | classe appartenance : " + GREEN_BOLD).append(otherClass.getcName()).append(RESET);
									sbCall.append(" | accès : STATIC Method");
								}
								else {
									sbCall.append("\n\t\tappel " + GREEN_BOLD)
											.append(attr.getaName())
											.append(".")
											.append(WHITE_BOLD_BRIGHT)
											.append(call)
											.append(RESET);
									sbCall.append(" | classe appartenance : " + GREEN_BOLD).append(otherClass.getcName()).append(RESET);
									sbCall.append(" | accès : Variable Instance");
								}

								found = true;

								int couplingSize = coupling.getOrDefault(otherClass.getcName(), 0);
								coupling.put(otherClass.getcName(), couplingSize + 1);

								List<JavaMethod> methodList = otherClass.getListMethods();
								otherClass = null;
								for (JavaMethod nextMethod : methodList) {
									if (nextMethod.getmName().equalsIgnoreCase(call.replace("()", ""))) {
										otherClass = getClassByName(nextMethod.getmTypeReturn());
										break;
									}
								}
							} else {
								List<JavaAttribute> attributeList = otherClass.getListAttributes();
								otherClass = null;
								for (JavaAttribute nextAttribute : attributeList) {
									if (nextAttribute.getaName().equalsIgnoreCase(call)) {
										otherClass = this.getClassByName(nextAttribute.getaType());
									}
								}
							}
						}


					}

					if(!found){
						sbCall.append("-");
					}
				}

				// Coupling
				if (!coupling.entrySet().isEmpty()) {
					for (Entry<String, Integer> entry : coupling.entrySet()) {
						sbCoupling.append("\t+ La classe ")
								.append(YELLOW_BOLD_BRIGHT).append(currentClass.getcName()).append(RESET)
								.append(" appelle ")
								.append(GREEN_BOLD).append(entry.getValue()).append(" méthodes").append(RESET)
								.append(" de la classe ")
								.append(GREEN_BOLD).append(entry.getKey()).append(RESET).append("\n");
					}
				} else {
					sbCoupling.append(RED + "\tPas de couplage entre classe\n").append(RESET);
					sbCall.append(RED + "\n\tPas de reference direct.\n").append(RESET);
				}
			});
		});

		System.out.println(WHITE_BOLD + "==========================================");
		System.out.println("=============== Modifiers ================");
		System.out.println("==========================================" + RESET);
		System.out.println(sbModifier);
		System.out.println(WHITE_BOLD + "==========================================");
		System.out.println("=============== Visibility ===============");
		System.out.println("==========================================" + RESET);
		System.out.println(sbVisibility);
		System.out.println(WHITE_BOLD + "==========================================");
		System.out.println("=============== Class Schemes ============");
		System.out.println("=========================================="+ RESET);
		System.out.println(sbInheritance);
		System.out.println(WHITE_BOLD + "==========================================");
		System.out.println("=============== Coupling =================");
		System.out.println("==========================================" + RESET);
		System.out.println(sbCoupling);
		System.out.println(WHITE_BOLD + "==========================================");
		System.out.println("=============== Call Hierarchy ===========");
		System.out.println("==========================================" + RESET);
		System.out.println(sbCall);
	}

	private JavaClass getClassByName(String name) {
		for (Object f : objectManager) {
			JavaFile file = (JavaFile) f;
			Optional<JavaClass> classes = file.getListClasses()
					.stream()
					.filter(c -> c.getcName().equals(name))
					.findAny();

			if (classes.isPresent()) {
				return classes.get();
			}
		}
		return null;
	}
}