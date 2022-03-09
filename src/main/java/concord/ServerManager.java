package concord;

import java.util.ArrayList;

public class ServerManager
{
	private ArrayList<Server> servers;
	private int numberOfServer;
	
	public ServerManager()
	{
		servers = new ArrayList<Server>();
		numberOfServer = 0;
	}
	
	public void createServer(User creator, String name, boolean priv)
	{
		servers.add(new Server(creator, name, numberOfServer, priv));
		numberOfServer++;
	}

	public ArrayList<Server> getServers()
	{
		return servers;
	}

	public void deleteServer(int id)
	{
		servers.remove(id);
	}
	
	public ArrayList<Server> getUserServer(User u)
	{
		Server tmp;
		ArrayList<Server> answer = new ArrayList<Server>();
		for (int i = 0; i < servers.size(); i++)
		{
			tmp = servers.get(i);
			if (tmp.users.contains(u)) answer.add(tmp);
		}	
		return answer;
	}
}
