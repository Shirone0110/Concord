package concord;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ChannelTest
{
	Channel c;
	Server s;
	Message m1, m2;
	User a;

	@BeforeEach
	void setUp() throws Exception
	{
		a = new User("Prince Bojji", "Chantakrak", "123", 0);
		s = new Server(a, "test", 0, true);
		c = new Channel("channel", s);
		m1 = new Message(a, "Hi");
		m2 = new Message(a, "Hello");
	}

	@Test
	void testMessage()
	{
		c.addMessage(m1);
		c.addMessage(m2);
		
		ArrayList<Message> tmp = new ArrayList<Message>();
		tmp.add(m1);
		tmp.add(m2);
		
		assertEquals(tmp, c.getMessage());
		
		tmp.remove(m1);
		c.deleteMessage(m1);
		assertEquals(tmp, c.getMessage());
	}
	
	@Test
	void testName()
	{
		assertEquals("channel", c.getName());
		c.setName("change");
		assertEquals("change", c.getName());
	}
	
	@Test
	void testServer()
	{
		assertEquals(s, c.getServer());
	}

}
