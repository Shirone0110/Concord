package concord;

public class Concord
{
	DirectConversationManager d;
	ServerManager s;
	UserManager u;
	
	public Concord()
	{
		d = new DirectConversationManager();
		s = new ServerManager();
		u = new UserManager();
	}
}
