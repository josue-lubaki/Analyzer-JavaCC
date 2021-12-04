package pkgClass;

import java.util.ArrayList;
import java.util.List;

import pkgAttribute.IAttribute;
import pkgAttribute.JavaAttribute;
import pkgInterface.IInterface;
import pkgInterface.JavaInterface;
import pkgMethod.IMethod;
import pkgMethod.JavaMethod;
import utility.JavaFile;

public class JavaClass implements IClass, IAttribute, IInterface, IMethod {

	private JavaClass cParent;
	private JavaFile cFile;
	private String cName;
	private String superClass;
	private List<JavaInterface> listInterfaces;
	private List<JavaAttribute> listAttributes;
	private List<JavaMethod> listMethods;
	private List<JavaClass> listClasses;
	private int cScope;

	public JavaClass(String cName) {
		this.setcName(cName);
		this.listClasses = new ArrayList<>();
		this.listAttributes = new ArrayList<>();
		this.listMethods = new ArrayList<>();
		this.listInterfaces = new ArrayList<>();
	}

	// methode qui vérifie si cParent a des superClass
	public boolean hasSuperClass() {
		return this.cParent != null;
	}

	@Override
	public void addClass(JavaClass jClass) {
		this.listClasses.add(jClass);
	}

	@Override
	public void addMethod(JavaMethod jMethod) {
		this.listMethods.add(jMethod);
	}

	@Override
	public void addInterface(JavaInterface jInterface) {
		this.listInterfaces.add(jInterface);
	}

	@Override
	public void addAttribute(JavaAttribute jAttribute) {
		this.listAttributes.add(jAttribute);
	}

	public JavaClass getcParent() {
		return cParent;
	}

	public void setcParent(JavaClass cParent) {
		this.cParent = cParent;
	}

	public JavaFile getcFile() {
		return cFile;
	}

	public void setcFile(JavaFile cFile) {
		this.cFile = cFile;
	}

	public String getcName() {
		return cName;
	}

	public void setcName(String cName) {
		this.cName = cName;
	}

	public String getSuperClass() {
		return superClass;
	}

	public void setSuperClass(String superClass) {
		this.superClass = superClass;
	}

	public List<JavaInterface> getListInterfaces() {
		return listInterfaces;
	}

	public void setListInterfaces(List<JavaInterface> listInterfaces) {
		this.listInterfaces = listInterfaces;
	}

	public List<JavaAttribute> getListAttributes() {
		return listAttributes;
	}

	public void setListAttributes(List<JavaAttribute> listAttributes) {
		this.listAttributes = listAttributes;
	}

	public List<JavaMethod> getListMethods() {
		return listMethods;
	}

	public void setListMethods(List<JavaMethod> listMethods) {
		this.listMethods = listMethods;
	}

	public List<JavaClass> getListClasses() {
		return listClasses;
	}

	public void setListClasses(List<JavaClass> listClasses) {
		this.listClasses = listClasses;
	}

	public int getcScope() {
		return cScope;
	}

	public void setcScope(int cScope) {
		this.cScope = cScope;
	}

}
