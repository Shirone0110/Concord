package concord;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DirectConversationTest
{
	DirectConversation d;
	User a, b;
	Message m1, m2;
	
	@BeforeEach
	void setUp() throws Exception
	{
		d = new DirectConversation(0);
		a = new User("a", "a", "123", 0);
		b = new User("b", "b", "234", 1);
		m1 = new Message(a, "hi");
		m2 = new Message(b, "hello");
	}

	@Test
	void testId()
	{
		assertEquals(0, d.getId());
	}

	@Test
	void testUser()
	{
		d.addUser(a);
		d.addUser(b);
		ArrayList<User> tmp = new ArrayList<User>();
		tmp.add(a);
		tmp.add(b);
		
		assertEquals(tmp, d.getUsers());
		
		tmp.remove(a);
		d.removeUser(a);
		assertEquals(tmp, d.getUsers());
	}
	
	@Test
	void testMessage()
	{
		d.addMessage(a, "hi");
		d.addMessage(b, "hello");
		ArrayList<Message> tmp = new ArrayList<Message>();
		tmp.add(m1);
		tmp.add(m2);
		
		assertEquals(tmp, d.getMessages());
		
		tmp.remove(m2);
		d.deleteMessage(m2);
		assertEquals(tmp, d.getMessages());
	}
}
