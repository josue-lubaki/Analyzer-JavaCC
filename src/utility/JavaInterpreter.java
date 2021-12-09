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
				sbModifier.append(String.format("Q1 |> Voici les statistiques de l'Analyse de la portee des attributs pour la classe %s\n", currentClass.getcName()));
				sbVisibility.append(String.format("Q2 |> Voici les statistiques de l'Analyse de la visibilite de la classe %s\n",currentClass.getcName()));
				sbCoupling.append(String.format("Q4 |> Voici les statistiques de l'Analyse sur le couplage pour la classe %s\n",currentClass.getcName()));
				sbCall.append(String.format(
						"Q5/ Voici les statistiques de l'analyse de graphe d'appel direct entre methodes pour la classe %s\n",currentClass.getcName()));
				sbInheritance.append("Q3 |> Voici les statistiques de l'analyse portant sur l'heritage:\n");

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
							sbInheritance.append(String.format("\t\t%s\n", attr.getaType()));
						}
					}
				} // end for

				double total = nbrePublicAttribute + nbrePrivateAttribute + nbreProtectedAttribute;
				if (total > 0) {
					sbModifier.append(
							String.format("\tPourcentage des attributs publics: %.2f%s\n", ((double) nbrePublicAttribute / total * 100), "%"));
					sbModifier.append(String.format("\tPourcentage des attributs Protected: %.2f%s\n",
							((double) nbreProtectedAttribute / total * 100), "%"));
					sbModifier.append(
							String.format("\tPourcentage des attributs Private: %.2f%s\n", ((double) nbrePrivateAttribute / total * 100), "%"));

					total = nbrePrimitiveAttribute + nbreReferenceAttribute;

					sbVisibility.append("\t% des attributs de type simple et % des attributs de référence (objet) :\n");
					sbVisibility.append(String.format("\t\tSimple Type: %.2f%s\n",
							((double) nbrePrimitiveAttribute / total * 100), "%"));
					sbVisibility.append(String.format("\t\tRéférence Type: %.2f%s\n",
							((double) nbreReferenceAttribute / total * 100), "%"));

					sbVisibility.append("\tclasse à visibilité permanente : \n");
					for (JavaAttribute attr : currentClass.getListAttributes()) {
						if (!listVisibility.contains(attr.getaType())
								&& !currentClass.getcName().equalsIgnoreCase(attr.getaType())) {
							listVisibility.add(attr.getaType());
							sbVisibility.append(String.format("\t\t%s\n", attr.getaType()));
						}
					}

				} else {
					sbModifier.append("\tPas d'Attribut\n");
					sbVisibility.append("\tPas de visibilité pour des Attributs\n");
				}

				for (JavaMethod method : currentClass.getListMethods()) {
					// recuperer le nom de la methode
					//sbCall.append(String.format("\t%s: \n", method.getmName()));

					for (JavaMethodCall methodCall : method.getListMethodCalls()) {
						// Verifier si la method est appele par une variable d'instance ou sinon c'est
						// une method statique de la classe
						JavaAttribute attr = method.getListLocalVariables()
								.stream()
								.filter(m -> m.getaName().equals(methodCall.getObj()))
								.findAny()
								.orElseGet(() -> currentClass.getListAttributes().stream()
										.filter(a -> {
											System.out.printf("a.getaType() = %s \net methodCall.getOjet == %s%n", a.getaName(), methodCall.getObj());
											return a.getaName().equals(methodCall.getObj());
										})
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
								sbCall.append(String.format("\t%s: \n", method.getmName()));
								sbCall.append(String.format("\t\t%s.%s\n", otherClass.getcName(), call));

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
				}

				// Coupling
				if (!coupling.entrySet().isEmpty()) {
					for (Entry<String, Integer> entry : coupling.entrySet()) {
						sbCoupling.append(String.format("\t%s: %d\n", entry.getKey(), entry.getValue()));
					}
				} else {
					sbCall.append("\tPas de couplage entre classe\n");
					sbCoupling.append("\tPas de reference direct.\n");
				}
			});
		});

		System.out.println("==========================================");
		System.out.println("=============== Modifiers ================");
		System.out.println("==========================================");
		System.out.println(sbModifier);
		System.out.println("==========================================");
		System.out.println("=============== Visibility ===============");
		System.out.println("==========================================");
		System.out.println(sbVisibility);
		System.out.println("==========================================");
		System.out.println("=============== Class Schemes ============");
		System.out.println("==========================================");
		System.out.println(sbInheritance);
		System.out.println("==========================================");
		System.out.println("=============== Coupling =================");
		System.out.println("==========================================");
		System.out.println(sbCoupling);
		System.out.println("==========================================");
		System.out.println("=============== Call Hierarchy ===========");
		System.out.println("==========================================");
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

	public void debugTree() {
		System.out.println("==========================================");
		System.out.println("============== DEBUG =====================");
		System.out.println("==========================================");
		for (Object o : objectManager) {
			JavaFile f = (JavaFile) o;
			for (JavaClass c : f.getListClasses()) {
				System.out.printf("CLASS: %s\n", c.getcName());
				for (JavaAttribute a : c.getListAttributes()) {
					System.out.printf("\tATTR: %s : %s\n", a.getaName(), a.getaType());
				}

				for (JavaMethod m : c.getListMethods()) {
					System.out.printf("\tMETHOD: %s -> %s\n", m.getmName(), m.getmTypeReturn());
					for (JavaMethodCall mc : m.getListMethodCalls()) {
						System.out.printf("\t\tCALL: %s\n", mc.getRawCall());
					}
				}
			}
		}
	}

}
