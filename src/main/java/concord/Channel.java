package concord;

import java.util.ArrayList;

public class Channel
{
	private String channelName;
	private ArrayList<Message> messages;
	private Server from;
	private int channelId;
	
	public Channel(String name, Server server, int id)
	{
		channelName = name;
		from = server;
		channelId = id;
		messages = new ArrayList<Message>();
	}
	
	public Channel()
	{
		new Channel("default", new Server(), 0);
	}
	
	public String getChannelName()
	{
		return channelName;
	}
	
	public void setChannelName(String name)
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
	
	public ArrayList<Message> getMessages()
	{
		return messages;
	}
	
	public void setMessages(ArrayList<Message> m)
	{
		messages = m;
	}
	
	public Server getFrom()
	{
		return from;
	}
	
	public void setFrom(Server s)
	{
		from = s;
	}
	
	public int getChannelId()
	{
		return channelId;
	}
	
	public void setChannelId(int id)
	{
		channelId = id;
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
		Channel other = (Channel) obj;
		return channelId == other.channelId;
	}
	
	
}
