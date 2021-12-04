package pkgAttribute;

public class JavaAttribute {
    /*
     * Pour une variable, nous avons besoin de retenir essentiellent, son nom, type et sa portée
     * */
	
	private String aName;
	private String aType;
	private int aScope; // autrement appelé "modifiers"
	
	public JavaAttribute() {}
	
	public JavaAttribute(String aName, String aType, int aScope) {
		this.aName = aName;
		this.aType = aType;
		this.aScope = aScope;
	}
	
	public JavaAttribute(String aName) {
		this.aName = aName;
	}

	public String getaName() {
		return aName;
	}

	public void setaName(String aName) {
		this.aName = aName;
	}

	public String getaType() {
		return aType;
	}

	public void setaType(String aType) {
		this.aType = aType;
	}

	public int getaScope() {
		return aScope;
	}

	public void setaScope(int aScope) {
		this.aScope = aScope;
	}
	
	

}
