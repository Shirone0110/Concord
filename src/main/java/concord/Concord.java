package concord;

public class Concord
{
	private DirectConversationManager d;
	private ServerManager s;
	private UserManager u;
	
	public Concord()
	{
		d = new DirectConversationManager();
		s = new ServerManager();
		u = new UserManager();
	}
}
