package Test;

public class Adress {
		public static int nbreAdress;
		private String rue;
		private String ville;
		private String zip;
		
		public Adress(String rue, String ville, String zip) {
			this.rue = rue;
			this.ville = ville;
			this.zip = zip;
		}

		public String getRue() {
			return rue;
		}

		public void setRue(String rue) {
			this.rue = rue;
		}

		public String getVille() {
			return ville;
		}

		public void setVille(String ville) {
			this.ville = ville;
		}

		public String getZip() {
			return zip;
		}

		public void setZip(String zip) {
			this.zip = zip;
		}
		
	}