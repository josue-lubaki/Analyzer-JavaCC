package pkgMethodCall;

public class JavaMethodCall {

	private final String rawCall;
	private final String[] splittedCall;

	public JavaMethodCall(String rawCall) {
		this.rawCall = rawCall;
		this.splittedCall = rawCall.split("[.]");
	}

	public String getRawCall() {
		return rawCall;
	}

	public String[] getSplittedCall() {
		return splittedCall;
	}

	public String getCall(int index) {
		return index < splittedCall.length ? splittedCall[index] : "";
	}

	public int getLength() {
		return splittedCall.length;
	}

	public String getLastCall() {
		return getCall(getLength() - 1);
	}
	
	public String getObj() {
		return splittedCall.length > 0 ? splittedCall[0] : "";
	}

}
