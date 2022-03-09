package concord;

public class InvalidActionException extends Exception
{
	private static final long serialVersionUID = 374233024088545709L;

	public InvalidActionException()
	{
		super("You don't have the permission to perform this action.");
	}
	
	public InvalidActionException(String message)
	{
		super(message);
	}
}
