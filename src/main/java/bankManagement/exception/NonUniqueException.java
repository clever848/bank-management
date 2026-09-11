package bankManagement.exception;

public class NonUniqueException extends RuntimeException{
	public NonUniqueException(String msg) {
		super(msg);
	}
}
