package mule.graph;

public class NoSuchRouteException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public NoSuchRouteException (String message) {
		super(message);
	}
}
