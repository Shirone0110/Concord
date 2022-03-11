package concord;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class DirectConversation
{
	private ArrayList <User> users;
	private ArrayList <Message> messages;
	private int directConvoId;
	private final LocalDateTime defaultTime = LocalDateTime.MIN;
	
	public DirectConversation(int id)
	{
		users = new ArrayList<User>();
		messages = new ArrayList<Message>();
		directConvoId = id;
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
	
	public ArrayList<Message> getMessages()
	{
		return messages;
	}
	
	public int getId()
	{
		return directConvoId;
	}
	
	public LocalDateTime getLastTimestamp()
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
		if (directConvoId == dc.getId()) return true;
		return false;
	}
}
