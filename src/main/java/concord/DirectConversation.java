package concord;

import java.util.ArrayList;
import java.util.Date;

public class DirectConversation
{
	private ArrayList <User> users;
	private ArrayList <Message> messages;
	private int directConvoId;
	private Date defaultTime = new Date();
	
	public DirectConversation(int id)
	{
		users = new ArrayList<User>();
		messages = new ArrayList<Message>();
		directConvoId = id;
	}
	
	public DirectConversation()
	{
		new DirectConversation(0);
	}
	
	public void addUser(User u)
	{
		users.add(u);
	}
	
	public void removeUser(User u)
	{
		users.remove(u);
	}
	
	public void addMessage(Message m)
	{
		messages.add(m);
	}
	
	public void deleteMessage(Message m)
	{
		messages.remove(m);
	}
	
	public ArrayList<User> getUsers()
	{
		return users;
	}
	
	public void setUsers(ArrayList<User> u)
	{
		users = u;
	}
	
	public ArrayList<Message> getMessages()
	{
		return messages;
	}
	
	public void setMessages(ArrayList<Message> m)
	{
		messages = m;
	}
	
	public int getDirectConvoId()
	{
		return directConvoId;
	}
	
	public void setDirectConvoId(int id)
	{
		directConvoId = id;
	}
	
	public Date getDefaultTime()
	{
		return defaultTime;
	}
	
	/**
	 * @param defaultTime the defaultTime to set
	 */
	public void setDefaultTime(Date defaultTime)
	{
		this.defaultTime = defaultTime;
	}
	
	public Date getLastTimestamp()
	{
		if (messages.size() == 0) return defaultTime;
		return messages.get(messages.size() - 1).getTime();
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (obj == this) return true;
		if (!(obj instanceof DirectConversation)) return false;
		DirectConversation dc = (DirectConversation) obj;
		if (directConvoId == dc.getDirectConvoId()) return true;
		return false;
	}
}
