package Test;

public class User {
	private String fname;
	private String lname;
	private int age;
	private Adress adress;
	
	public User(String fname, String lname, int age, Adress adress) {
		this.fname = fname;
		this.lname = lname;
		this.age = age;
		this.adress = adress;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Adress getAdress() {
		return adress;
	}

	public void setAdress(Adress adress) {
		this.adress = adress;
	}	
	
	public int getNombreAdress() {
		return Adress.nbreAdress;
	}
	
	
}
