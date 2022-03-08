package concord;

import java.time.LocalDateTime;

public class Message
{
	User user;
	String content;
	LocalDateTime time;
	Message replyTo;
	int messageId;
	
	public Message(User u, String text, int id)
	{
		user = u;
		content = text;
		messageId = id;
		time = LocalDateTime.now();
	}
	
	public Message(User u, String text, int id, Message reply)
	{
		this(u, text, id);
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
	
	public int getId()
	{
		return messageId;
	}
}
