

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
	
	public class Adress{
		private String rue;
		private String ville;
		private String zip;
		
		public Adress(String rue, String ville, String zip) {
			this.rue = rue;
			this.ville = ville;
			this.zip = zip;
		}
	}
	
}
