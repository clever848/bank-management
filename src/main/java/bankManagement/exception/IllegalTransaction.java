package bankManagement.exception;

public class IllegalTransaction extends RuntimeException{
	public IllegalTransaction(String msg) {
		super(msg);
	}
}
