package concord;

public class Concord
{
	private DirectConversationManager d;
	private ServerManager s;
	private UserManager u;
	
	public DirectConversationManager getDCM()
	{
		return d;
	}

	public ServerManager getSM()
	{
		return s;
	}

	public UserManager getUM()
	{
		return u;
	}

	public Concord()
	{
		d = new DirectConversationManager();
		s = new ServerManager();
		u = new UserManager();
	}
}
