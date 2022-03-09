package concord;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MessageTest
{
	Message m;
	User u;
	
	@BeforeEach
	void setUp() throws Exception
	{
		u = new User("Prince Bojji", "Chantakrak", "123", 0);
		m = new Message(u, "Hi");
	}

	@Test
	void testUserContent()
	{
		assertEquals(u, m.getUser());
		assertEquals("Hi", m.getContent());
		
		m.setContent("Hello");
		assertEquals("Hello", m.getContent());
	}

	@Test
	void testReply()
	{
		Message rep = new Message(u, "Reply", m);
		assertEquals(m, rep.getReply());
		assertEquals(u, rep.getUser());
	}
	
}
