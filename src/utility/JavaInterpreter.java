package utility;

import pkgAttribute.JavaAttribute;
import pkgClass.JavaClass;
import pkgInterface.JavaInterface;
import pkgMethod.JavaMethod;
import pkgMethodCall.JavaMethodCall;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicReference;

/**
 * C'est la classe qui permet d'interpreter le resultat obtenue par l'analyse
 * 
 */
public class JavaInterpreter {
	public static final String RESET = "\033[0m"; // Text Reset
	public static final String GREEN_BOLD = "\033[1;32m"; // GREEN
	public static final String YELLOW_BOLD = "\033[1;33m"; // YELLOW
	public static final String YELLOW_BOLD_BRIGHT = "\033[1;93m";// YELLOW
	public static final String RED = "\033[0;31m"; // RED
	public static final String WHITE_BOLD = "\033[1;37m"; // WHITE
	public static final String WHITE_BOLD_BRIGHT = "\033[1;97m"; // WHITE
	public static final String BLACK_BACKGROUND = "\033[40m";  // BLACK
	private final JavaObjetManager objectManager;
	private StringBuilder outResultat = new StringBuilder();
	public static final String directoryResultat = "Output";

	// constructor
	public JavaInterpreter(JavaObjetManager objectManager) {
		this.objectManager = objectManager;
	}

	/**
	 * Methode qui permet d'afficher les resultats de l'analyse
	 */
	public void showStatisticToConsole(boolean allowShowConsole) {
		AtomicReference<StringBuilder> sbModifier = new AtomicReference<>(new StringBuilder());
		AtomicReference<StringBuilder> sbVisibility = new AtomicReference<>(new StringBuilder());
		AtomicReference<StringBuilder> sbCoupling = new AtomicReference<>(new StringBuilder());
		AtomicReference<StringBuilder> sbCall = new AtomicReference<>(new StringBuilder());
		AtomicReference<StringBuilder> sbInheritance = new AtomicReference<>(new StringBuilder());
		JavaModifierSet modifiersHelpers = new JavaModifierSet();
		List<String> listAssociations = new ArrayList<>();
		List<String> listVisibility = new ArrayList<>();

		// Parcourir tous les fichiers
		objectManager.forEach(obj -> {
			if(obj instanceof JavaClass || obj instanceof JavaMethod || obj instanceof JavaInterface){
				return;
			}

			JavaFile file = (JavaFile) obj;

			// r??cup??rer toutes les classes pour l'analyse
			file.getListClasses().forEach(currentClass -> {
				int nbrePublicAttribute = 0, nbrePrivateAttribute = 0, nbreProtectedAttribute = 0,
						nbrePrimitiveAttribute = 0, nbreReferenceAttribute = 0;

				// Affichage des Titres
				Map<String, Integer> coupling = new HashMap<>();
				sbModifier.get()
						.append(YELLOW_BOLD
								+ "Q1 |> Voici les statistiques de l'Analyse de la portee des attributs pour la classe "
								+ YELLOW_BOLD_BRIGHT)
						.append(currentClass.getcName())
						.append(RESET).append("\n");

				sbVisibility.get()
						.append(YELLOW_BOLD + "Q2 |> Voici les statistiques de l'Analyse de la visibilite de la classe "
								+ YELLOW_BOLD_BRIGHT)
						.append(currentClass.getcName())
						.append(RESET).append("\n");

				sbInheritance.get()
						.append(YELLOW_BOLD
								+ "Q3 |> Voici les statistiques de l'analyse portant sur l'heritage de la classe "
								+ YELLOW_BOLD_BRIGHT)
						.append(currentClass.getcName())
						.append(RESET).append("\n");

				sbCoupling.get()
						.append(YELLOW_BOLD
								+ "Q4 |> Voici les statistiques de l'Analyse sur le couplage pour la classe "
								+ YELLOW_BOLD_BRIGHT)
						.append(currentClass.getcName())
						.append(RESET).append("\n");

				sbCall.get().append(YELLOW_BOLD
						+ "Q5/ Voici les statistiques de l'analyse de graphe d'appel direct entre methodes pour la classe "
						+ YELLOW_BOLD_BRIGHT)
						.append(currentClass.getcName())
						.append(RESET);

				// V??rifier si la classe courant a un super Parent
				if (!currentClass.hasSuperClass()) {
					sbInheritance.get()
							.append(String.format(
									"La classe \"%s\" n'h??rite d'aucune autre classe\n\tLes Associations :\n",
									currentClass.getcName()));
				} else {
					sbInheritance.get().append(String.format("La classe %s est herite de %s\n\tLes Associations : \n",
							currentClass.getcName(), currentClass.getSuperClass()));
				}

				// Analyser la port?? des attributs
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

						// recuperation des associations, et eviter les doublons
						if (!listAssociations.contains(attr.getaType())
								&& !currentClass.getcName().equalsIgnoreCase(attr.getaType())) {
							listAssociations.add(attr.getaType());
							sbInheritance.get().append(String.format("\t\t- %s\n", attr.getaType()));
						}
					}
				} // end for

				double total = nbrePublicAttribute + nbrePrivateAttribute + nbreProtectedAttribute;
				DecimalFormat f = new DecimalFormat();
				f.setMaximumFractionDigits(2);
				if (total > 0) {
					double pPublic = (nbrePublicAttribute / total) * 100;
					double pProtected = (nbreProtectedAttribute / total) * 100;
					double pPrivate = (nbrePrivateAttribute / total) * 100;

					sbModifier.get().append("\tPourcentage des attributs Private: " + GREEN_BOLD).append(f.format(pPrivate))
							.append("%")
							.append(RESET).append("\n");
					sbModifier.get().append("\tPourcentage des attributs publics: " + GREEN_BOLD).append(f.format(pPublic))
							.append("%")
							.append(RESET).append("\n");
					sbModifier.get().append("\tPourcentage des attributs Protected: " + GREEN_BOLD).append(f.format(pProtected))
							.append("%").append(RESET).append("\n");

					total = nbrePrimitiveAttribute + nbreReferenceAttribute;
					double pSimple = (nbrePrimitiveAttribute / total) * 100;
					double pReference = (nbreReferenceAttribute / total) * 100;

					sbVisibility.get()
							.append("\t% des attributs de type simple et % des attributs de r??f??rence (objet) :\n");
					sbVisibility.get().append("\t\tSimple Type: " + GREEN_BOLD).append(f.format(pSimple)).append("%")
							.append(RESET)
							.append("\n");
					sbVisibility.get().append("\t\tR??f??rence Type: " + GREEN_BOLD).append(f.format(pReference)).append("%")
							.append(RESET).append("\n");

					sbVisibility.get().append("\tclasse ?? visibilit?? permanente : \n");

					for (JavaAttribute attr : currentClass.getListAttributes()) {
						if (!listVisibility.contains(attr.getaType())
								&& !currentClass.getcName().equalsIgnoreCase(attr.getaType())) {
							listVisibility.add(attr.getaType());
							sbVisibility.get().append(String.format("\t\t- %s\n", attr.getaType()));
						}
					}

				} else {
					sbModifier.get().append("\tPas d'Attribut\n");
					sbVisibility.get().append("\tPas de visibilit?? pour des Attributs\n");
				}

				// Analyse des methodes
				for (JavaMethod method : currentClass.getListMethods()) {
					sbCall.get().append(String.format("\n\t%s: ", method.getmName()));
					boolean foundReference = false;

					for (JavaMethodCall methodCall : method.getListMethodCalls()) {
						// Verifier si la method est appel?? par une variable d'instance ou sinon c'est
						// une method static de la classe
						JavaAttribute attr = method.getListLocalVariables()
								.stream()
								.filter(m -> m.getaName().equals(methodCall.getObj()))
								.findAny()
								.orElseGet(() -> currentClass.getListAttributes().stream()
										.filter(a -> a.getaName().equals(methodCall.getObj()))
										.findAny()
										.orElse(null));

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

						for (int index = 1; index < methodCall.getLength(); index++) {
							if (otherClass == null)
								break;

							String call = methodCall.getCall(index);
							if (call.contains("()")) {
								// si method static
								if (attr == null) {
									sbCall.get().append("\n\t\tappel " + GREEN_BOLD)
											.append(otherClass.getcName()).append(".")
											.append(WHITE_BOLD_BRIGHT).append(call)
											.append(RESET);
									sbCall.get().append(" | classe appartenance : " + GREEN_BOLD)
											.append(otherClass.getcName()).append(RESET);
									sbCall.get().append(" | acc??s : STATIC Method").append("\n");

								} else { // via variable instance
									sbCall.get().append("\n\t\tappel " + GREEN_BOLD)
											.append(attr.getaName())
											.append(".")
											.append(WHITE_BOLD_BRIGHT)
											.append(call)
											.append(RESET);
									sbCall.get().append(" | classe d'appartenance : " + GREEN_BOLD)
											.append(otherClass.getcName()).append(RESET);
									sbCall.get().append(" | acc??s : Variable Instance").append("\n");
								}

								foundReference = true;

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

					if (!foundReference) {
						sbCall.get().append("-");
					}
				}

				// Coupling
				if (!coupling.entrySet().isEmpty()) {
					for (Entry<String, Integer> entry : coupling.entrySet()) {
						sbCoupling.get().append("\t+ La classe ")
								.append(YELLOW_BOLD_BRIGHT).append(currentClass.getcName()).append(RESET)
								.append(" appelle ")
								.append(GREEN_BOLD).append(entry.getValue()).append(" m??thodes").append(RESET)
								.append(" de la classe ")
								.append(GREEN_BOLD).append(entry.getKey()).append(RESET).append("\n");
					}
				} else {
					sbCoupling.get().append(RED + "\tPas de couplage entre classe\n").append(RESET);
					sbCall.get().append(RED + "\n\tPas de reference direct.\n").append(RESET);
				}

				// Si l'affiche ?? la console est demand??e
				if(allowShowConsole){
					System.out.println(GREEN_BOLD + "===========================================================");
					System.out.println(WHITE_BOLD_BRIGHT + "===============" + YELLOW_BOLD_BRIGHT + " La Classe "
							+ currentClass.getcName()
							+ WHITE_BOLD_BRIGHT + " ================");
					System.out.println(GREEN_BOLD + "===========================================================" + RESET);
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
					System.out.println("==========================================" + RESET);
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
				else {
					// Cr??er un fichier et ??crire les resultats
					try {// cr??er un nouveau fichier qui stockera les informations de l'analyse ayant le
						// nom de la classe trait??e
						// date de la cr??ation du fichier
						long now = System.currentTimeMillis();
						// convertir le timestamp en date
						Date date = new Date(now);
						// cr??er un objet SimpleDateFormat qui convertira la date en format texte
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
						String path = directoryResultat + File.separator + currentClass.getcName() + "-" + sdf.format(date) + ".txt";

						// cr??er dossier resultat
						File dir = new File(directoryResultat);
						if (!dir.exists()) {
							boolean isDirectoryCreate = dir.mkdir();
						} // Le fichier a ??t?? cr??e

						// si le fichier n'existe, le cr??er
						if (!new File(path).exists()) {
							boolean isFileCreate = new File(path).createNewFile();
						}

						FileWriter fw = new FileWriter(path, true);

						// ??crire les informations dans le fichier
						outResultat
								.append("======================================")
								.append("=============== LA CLASSE ").append(currentClass.getcName().toUpperCase())
								.append(" ================")
								.append("======================================\n")
								.append(sbModifier.get())
								.append(sbVisibility.get())
								.append(sbInheritance.get())
								.append(sbCoupling.get())
								.append(sbCall.get());
						// supprime les valeurs de couleur dans outResultat
						fw.write(outResultat.toString()
								.replace(RED, "")
								.replace(GREEN_BOLD, "")
								.replace(YELLOW_BOLD, "")
								.replace(WHITE_BOLD, "")
								.replace(WHITE_BOLD_BRIGHT, "")
								.replace(RED, "")
								.replace(YELLOW_BOLD_BRIGHT, "")
								.replace(RESET, ""));
						fw.close();
					} catch (IOException e) {
						System.err.println("IOException: " + e.getMessage());
						e.printStackTrace();
					}
				} // end else

				outResultat = new StringBuilder();
				sbModifier.set(new StringBuilder());
				sbVisibility.set(new StringBuilder());
				sbInheritance.set(new StringBuilder());
				sbCoupling.set(new StringBuilder());
				sbCall.set(new StringBuilder());
			}); // end read currentClass
		}); // end read file
	} // end method

	private JavaClass getClassByName(String name) {
		for (Object obj : objectManager) {
			if(obj instanceof JavaClass || obj instanceof JavaMethod || obj instanceof JavaInterface){
				continue;
			}
			JavaFile file = (JavaFile) obj;
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

	public static String capitalizeString(String str){
		return str.substring(0,1).toUpperCase() + str.substring(1).toLowerCase();
	}
}