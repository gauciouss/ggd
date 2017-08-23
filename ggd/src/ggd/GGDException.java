package ggd;

public class GGDException extends Exception {

	private static final long serialVersionUID = 7161667104150048530L;

	public GGDException() {
	}
	
	
	public GGDException(String msg) {
		super(msg);
	}
	
	public GGDException(Throwable ex) {
		super(ex);
	}
	
	public GGDException(String msg, Throwable ex) {
		super(msg, ex);
	}
}
