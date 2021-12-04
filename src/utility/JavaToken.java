package utility;

import parser.JavaParserConstants;
import parser.Token;

public class JavaToken extends Token {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int realKind = JavaParserConstants.GT;

    public JavaToken(int kind, String image) {
        this.kind = kind;
        this.image = image;
    }
    
    public static Token newToken(int ofKind, String tokenImage) {
    	return new JavaToken(ofKind, tokenImage);
    }

}
