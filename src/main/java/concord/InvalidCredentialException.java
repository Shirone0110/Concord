package concord;

public class InvalidCredentialException extends Exception
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1266163287703842140L;

	/**
	 * 
	 */
	public InvalidCredentialException()
	{
		super("Incorrect username or password");
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public InvalidCredentialException(String message)
	{
		super(message);
		// TODO Auto-generated constructor stub
	}
	
}
