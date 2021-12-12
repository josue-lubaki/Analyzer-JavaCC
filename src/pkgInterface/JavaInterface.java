package pkgInterface;

import java.util.ArrayList;
import java.util.List;

import pkgAttribute.IAttribute;
import pkgAttribute.JavaAttribute;
import pkgMethod.IMethod;
import pkgMethod.JavaMethod;

public class JavaInterface implements IAttribute, IInterface, IMethod {

	private String iName;
	private int iScope;
	private List<JavaAttribute> listAttributes;
	private List<JavaMethod> listMethods;
	private List<JavaInterface> listInterfaces;

	public JavaInterface(String iName) {
		this.iName = iName;
		this.listAttributes = new ArrayList<>();
		this.listMethods = new ArrayList<>();
		this.listInterfaces = new ArrayList<>();
	}

	@Override
	public void addInterface(JavaInterface jInterface) {
		this.listInterfaces.add(jInterface);
	}

	@Override
	public void addMethod(JavaMethod jMethod) {
		this.listMethods.add(jMethod);
	}

	@Override
	public void addAttribute(JavaAttribute jAttribute) {
		this.listAttributes.add(jAttribute);
	}

	public String getiName() {
		return iName;
	}

	public void setiName(String iName) {
		this.iName = iName;
	}

	public int getiScope() {
		return iScope;
	}

	public void setiScope(int iScope) {
		this.iScope = iScope;
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

	public List<JavaInterface> getListInterfaces() {
		return listInterfaces;
	}

	public void setListInterfaces(List<JavaInterface> listInterfaces) {
		this.listInterfaces = listInterfaces;
	}

}
