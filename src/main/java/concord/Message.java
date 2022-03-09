package concord;

import java.time.LocalDateTime;

public class Message
{
	private User user;
	private String content;
	private LocalDateTime time;
	private Message replyTo;
	
	public Message(User u, String text)
	{
		user = u;
		content = text;
		time = LocalDateTime.now();
	}
	
	public Message(User u, String text, Message reply)
	{
		this(u, text);
		replyTo = reply;
	}
	
	public User getUser() 
	{
		return user;
	}
	
	public String getContent()
	{
		return content;
	}
	
	public void setContent(String text)
	{
		content = text;
	}
	
	public LocalDateTime getTime()
	{
		return time;
	}
	
	public Message getReply()
	{
		return replyTo;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (obj == this) return true;
		if (!(obj instanceof Message)) return false;
		Message m = (Message) obj;
		if (user == m.getUser() && content == m.getContent()
			&& time == m.getTime()) return true;
		return false;
	}
}
