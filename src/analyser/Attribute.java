package analyser;

import utility.IRange;
import utility.Range;

public class Attribute implements IRange {
	private String sName;
	private String sType;
	public Type type = new Type();
	public Range range = new Range();
	
	// constructor
	public Attribute() {
		this.sName = null;
		this.sType = null;
	}
	
	// Getters
	public String getName() {
		return sName;
	}
	
	public String getType() {
		return sType;
	}
	
	// setters
	public void setAttribute(String sName, String sType) {
		this.sName = sName;
		this.sType = sType;
	}
	
	public void setName(String sName) {
		this.sName = sName;
	}
	
	public void setType(String sType) {
		this.sType = sType;
	}
	
	// aussi appelé "isSimpleAttribute()"
	public boolean isPrimitive() {
		return this.type.contains(sType);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((sName == null) ? 0 : sName.hashCode());
		result = prime * result + ((sType == null) ? 0 : sType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Attribute) {
			Attribute other = (Attribute) obj;
			
			if(other.getName().equals(this.getName()) && other.getType().equals(this.getType()))
				return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "Attribute [sName=" + sName + ", sType=" + sType + "]";
	}
	
	
	
}
