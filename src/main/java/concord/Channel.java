package concord;

import java.util.ArrayList;

public class Channel
{
	private String channelName;
	private ArrayList<Message> messages;
	private Server from;
	
	public Channel(String name, Server server)
	{
		channelName = name;
		from = server;
		messages = new ArrayList<Message>();
	}
	
	public String getName()
	{
		return channelName;
	}
	
	public void setName(String name)
	{
		channelName = name;
	}
	
	public void addMessage(Message m)
	{
		messages.add(m);
	}
	
	public void deleteMessage(Message m)
	{
		messages.remove(m);
	}
	
	public ArrayList<Message> getMessage()
	{
		return messages;
	}
	
	public Server getServer()
	{
		return from;
	}
}
