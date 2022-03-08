package concord;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class DirectConversation
{
	ArrayList <User> users;
	ArrayList <Message> messages;
	int directConvoId;
	int messageCount;
	
	public DirectConversation(int id)
	{
		users = new ArrayList<User>();
		messages = new ArrayList<Message>();
		messageCount = 0;
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
	
	public void addMessage(User u, String text)
	{
		messages.add(new Message(u, text, messageCount));
		messageCount++;
	}
	
	public void deleteMessage(int id)
	{
		messages.remove(id);
	}
	
	public int getId()
	{
		return directConvoId;
	}
	
	public LocalDateTime getLastTimestamp()
	{
		return messages.get(messages.size() - 1).time;
	}
}
