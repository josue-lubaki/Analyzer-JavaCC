package pkgMethod;

import java.util.ArrayList;
import java.util.List;

import pkgAttribute.IAttribute;
import pkgAttribute.JavaAttribute;
import pkgMethodCall.IMethodCall;
import pkgMethodCall.JavaMethodCall;

public class JavaMethod implements IAttribute, IMethodCall{
	
	private String mName;
	private String mTypeReturn;
	private int mScope;
	private List<JavaAttribute> listLocalVariables;
	private List<JavaAttribute> listParameters;
	private List<JavaMethodCall> listMethodCalls;
	
	// Constructor default
	public JavaMethod() {
		this.listParameters = new ArrayList<>();
		this.listLocalVariables = new ArrayList<>();
		this.listMethodCalls = new ArrayList<>();
	}
	
	// constructor with method's name
	public JavaMethod(String mName) {
		this.mName = mName;
		this.listParameters = new ArrayList<>();
		this.listLocalVariables = new ArrayList<>();
		this.listMethodCalls = new ArrayList<>();
	}
	
	// constructor with name and modifiers
	public JavaMethod(String mName, int mScope) {
		this.mName = mName;
		this.mScope = mScope;
		this.listParameters = new ArrayList<>();
		this.listLocalVariables = new ArrayList<>();
		this.listMethodCalls = new ArrayList<>();
	}
	
	// constructor with name, modifiers and TypeReturn of method
	public JavaMethod(String mName, int mScope, String mTypeReturn) {
		this.mName = mName;
		this.mScope = mScope;
		this.mTypeReturn = mTypeReturn;
		this.listParameters = new ArrayList<>();
		this.listLocalVariables = new ArrayList<>();
		this.listMethodCalls = new ArrayList<>();
	}

	@Override
	public void addMethodCall(JavaMethodCall jMethodCall) {
		this.listMethodCalls.add(jMethodCall);
	}

	@Override
	public void addAttribute(JavaAttribute jAttribute) {
		this.listLocalVariables.add(jAttribute);
	}
	
	public void addParameters(List<JavaAttribute> listOfAttribute) {
		this.listParameters.addAll(listOfAttribute);
	}
	
	// method add a parameter
	public void addParameter(JavaAttribute jAttribute) {
		this.listParameters.add(jAttribute);
	}

	public String getmName() {
		return mName;
	}

	public void setmName(String mName) {
		this.mName = mName;
	}

	public String getmTypeReturn() {
		return mTypeReturn;
	}

	public void setmTypeReturn(String mTypeReturn) {
		this.mTypeReturn = mTypeReturn;
	}

	public int getmScope() {
		return mScope;
	}

	public void setmScope(int mScope) {
		this.mScope = mScope;
	}

	public List<JavaAttribute> getListLocalVariables() {
		return listLocalVariables;
	}

	public void setListLocalVariables(List<JavaAttribute> listLocalVariables) {
		this.listLocalVariables = listLocalVariables;
	}

	public List<JavaAttribute> getListParameters() {
		return listParameters;
	}

	public void setListParameters(List<JavaAttribute> listParameters) {
		this.listParameters = listParameters;
	}

	public List<JavaMethodCall> getListMethodCalls() {
		return listMethodCalls;
	}

	public void setListMethodCalls(List<JavaMethodCall> listMethodCalls) {
		this.listMethodCalls = listMethodCalls;
	}
	
}
