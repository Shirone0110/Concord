package concord;

import java.util.ArrayList;

public class Server
{
	String serverName;
	int serverId;
	ArrayList<Channel> channels;
	ArrayList<Message> pins;
	boolean isPrivate;
	ArrayList<User> users;
	String serverLogo;
	String serverDescription;
	
	public Server(String name, int id, boolean priv)
	{
		serverName = name;
		serverId = id;
		isPrivate = priv;
		channels = new ArrayList<Channel>();
		pins = new ArrayList<Message>();
		users = new ArrayList<User>();
	}
	
	public String getName()
	{
		return serverName;
	}
	
	public void setName(String name)
	{
		serverName = name;
	}
	
	public int getId()
	{
		return serverId;
	}
	
	public void addChannel(User u, Channel channel)
	{
		
	}
}
