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
	
	public Server createServer(User creator, String name, boolean priv)
	{
		Server s = new Server(creator, name, numberOfServer, priv);
		servers.add(s);
		numberOfServer++;
		return s;
	}

	/**
	 * @return the numberOfServer
	 */
	public int getNumberOfServer()
	{
		return numberOfServer;
	}

	/**
	 * @param numberOfServer the numberOfServer to set
	 */
	public void setNumberOfServer(int numberOfServer)
	{
		this.numberOfServer = numberOfServer;
	}

	public ArrayList<Server> getServers()
	{
		return servers;
	}
	
	public void setServers(ArrayList<Server> s)
	{
		servers = s;
	}
	
	public Server findServerById(int id)
	{
		for (Server s: servers)
		{
			if (s.getServerId() == id) return s;
		}
		return null;
	}

	public void deleteServer(User admin, int serverId) throws InvalidActionException
	{
		for (Server s: servers)
		{
			if (s.getServerId() == serverId)
			{
				RoleComponent role = s.getRoleMap().get(admin);
				if (role.getBasicPermission("modify admin"))
					servers.remove(s);
				else throw new InvalidActionException();
				return;
			}
		}
	}
	
	public ArrayList<Server> getUserServer(User u)
	{
		Server tmp;
		ArrayList<Server> answer = new ArrayList<Server>();
		for (int i = 0; i < servers.size(); i++)
		{
			tmp = servers.get(i);
			if (tmp.getUsers().contains(u)) answer.add(tmp);
		}	
		return answer;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ServerManager other = (ServerManager) obj;
		if (numberOfServer != other.numberOfServer)
			return false;
		for (Server s: servers)
		{
			if (!other.contains(s))
				return false;
		}
		return true;
	}
	
	public boolean contains(Server server)
	{
		for (Server s: servers)
		{
			if (s.equals(server)) return true;
		}
		return false;
	}
	
}
