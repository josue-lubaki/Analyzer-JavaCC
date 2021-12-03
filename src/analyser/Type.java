package analyser;

import java.util.ArrayList;

public class Type {
	public static final String STRING = "string";
	public static final String BOOLEAN = "boolean";
	public static final String CHAR = "char";
	public static final String BYTE = "byte";
	public static final String SHORT = "short";
	public static final String FLOAT = "float";
	public static final String DOUBLE = "double";
	public static final String INT = "int";
	public static final String LONG = "long";
	
	private ArrayList<String> listTypes;
	
	public Type() {
		this.listTypes = new ArrayList<String>();
		this.listTypes.add(Type.BOOLEAN);
		this.listTypes.add(Type.CHAR);
		this.listTypes.add(Type.BYTE);
		this.listTypes.add(Type.SHORT);
		this.listTypes.add(Type.INT);
		this.listTypes.add(Type.LONG);
		this.listTypes.add(Type.FLOAT);
		this.listTypes.add(Type.DOUBLE);
		this.listTypes.add(Type.STRING);
	}
	
	public boolean contains(String _type) {
		return listTypes.contains(_type.toLowerCase());
	}

}
