package concord;

import java.util.ArrayList;

public class Channel
{
	String channelName;
	ArrayList<Message> messages;
	int channelId;
	
	public Channel(String name, int id)
	{
		channelName = name;
		channelId = id;
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
	
	public void deleteMessage(int id)
	{
		messages.remove(id);
	}
	
	public int getId()
	{
		return channelId;
	}
}
