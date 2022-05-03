package concord;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7763743107695651068L;
	private User user;
	private String content;
	//private String time;
	private Date time;
	private Message replyTo;
	
	public Message(User u, String text)
	{
		user = u;
		content = text;
		//time = LocalDateTime.now().toString();
		time = new Date();
	}
	
	public Message()
	{
		new Message(new User(), "");
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
	
	public void setUser(User u)
	{
		user = u;
	}
	
	public String getContent()
	{
		return content;
	}
	
	public void setContent(String text)
	{
		content = text;
	}
	
	public Date getTime()
	{
		//return LocalDateTime.parse(time);
		return time;
	}
	
	/**
	 * @param time the time to set
	 */
	public void setTime(Date time)
	{
		//this.time = time.toString();
		this.time = time;
	}
	/**
	 * @return the replyTo
	 */
	public Message getReplyTo()
	{
		return replyTo;
	}

	/**
	 * @param replyTo the replyTo to set
	 */
	public void setReplyTo(Message replyTo)
	{
		this.replyTo = replyTo;
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
	
	@Override
	public String toString()
	{
		return user.getUserName() + ": " + content;
	}
}
