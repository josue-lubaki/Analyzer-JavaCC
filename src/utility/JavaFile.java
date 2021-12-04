package utility;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import pkgClass.IClass;
import pkgClass.JavaClass;
import pkgInterface.IInterface;
import pkgInterface.JavaInterface;

public class JavaFile implements IClass, IInterface {

	private File file;
	private String pkg;
	private List<JavaClass> listClasses;
	private List<JavaInterface> listInterfaces;
	private List<JavaImport> listImports;

	public JavaFile(File file) {
		this.file = file;
		this.pkg = "";
		this.listClasses = new ArrayList<>();
		this.listInterfaces = new ArrayList<>();
		this.listImports = new ArrayList<>();
	}

	@Override
	public void addInterface(JavaInterface jInterface) {
		this.listInterfaces.add(jInterface);
	}

	@Override
	public void addClass(JavaClass jClass) {
		this.listClasses.add(jClass);
	}
	
	public void addImport(JavaImport imp) {
		this.listImports.add(imp);
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getPkg() {
		return pkg;
	}

	public void setPkg(String pkg) {
		this.pkg = pkg;
	}

	public List<JavaClass> getListClasses() {
		return listClasses;
	}

	public void setListClasses(List<JavaClass> listClasses) {
		this.listClasses = listClasses;
	}

	public List<JavaInterface> getListInterfaces() {
		return listInterfaces;
	}

	public void setListInterfaces(List<JavaInterface> listInterfaces) {
		this.listInterfaces = listInterfaces;
	}

	public List<JavaImport> getListImports() {
		return listImports;
	}

	public void setListImports(List<JavaImport> listImports) {
		this.listImports = listImports;
	}
}
