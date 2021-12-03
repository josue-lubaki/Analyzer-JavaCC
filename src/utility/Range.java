package utility;

/**
 * 
 * <p>
 * Cette classe indique la port�e d'un objet : Public, Priv�e, Protected et ses
 * caract�ristiques: Static, Final et Abstract.
 * 
 */
public class Range {
	//Par defaut le range est PUBLIC.
    private int m_iRange = 0; // Variable contenant la port�e de la m�thode
                              // (private, public, protected)

    public boolean Static = false;

    public boolean Final = false;

    public boolean Abstract = false;

    static public int PUBLIC = 0;

    static public int PRIVATE = 1;

    static public int PROTECTED = 2;

    static public String ELEMENTNAME = "range"; //repr�sente le string de
                                                // l'�l�ment range dans un
                                                // Element
    public int get_Range() {
        return m_iRange;
    }
    
    public void set_Range(int Range) {
        this.m_iRange = Range;
    }
    
    /**
     * Retourne le range en string.
     * 
     * @return String : public, private ou protected.
     */
    public String toString() {
        String strRange = "";

        if (this.m_iRange == Range.PUBLIC) {
            strRange = "public";
        } else if (this.m_iRange == Range.PRIVATE) {
            strRange = "private";
        } else if (this.m_iRange == Range.PROTECTED) {
            strRange = "protected";
        }

        return strRange;
    }
}
