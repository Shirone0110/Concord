package concord;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DirectConversationManagerTest
{
	DirectConversationManager d;
	User a, b, c;
	ArrayList<DirectConversation> tmp;

	@BeforeEach
	void setUp() throws Exception
	{
		d = new DirectConversationManager();
		a = new User("Prince Bojji", "Chantakrak", "123", 0);
		b = new User("Shirone", "Lam", "456", 1);
		c = new User("Chaekyung", "Kiana", "789", 2);
		
		d.createDc(a, b);
		d.createDc(b, c);
		
		tmp = new ArrayList<DirectConversation>();
		
		DirectConversation dc = new DirectConversation(0);
		dc.addUser(a);
		dc.addUser(b);
		dc.addMessage(new Message(a, "Hi"));
		tmp.add(dc);
		
		DirectConversation dc2 = new DirectConversation(1);
		dc2.addUser(b);
		dc2.addUser(c);
		dc2.addMessage(new Message(b, "Hello"));
		tmp.add(dc2);
	}

	@Test
	void testCreateDelete()
	{	
		assertEquals(tmp, d.getDcList());
		
		tmp.remove(1);
		d.deleteDc(1);
		assertEquals(tmp, d.getDcList());
	}
	
	@Test
	void testGetPastConv()
	{
		System.out.println(d.getPastConversation(b));
		assertEquals(tmp, d.getPastConversation(b));
	}

}
